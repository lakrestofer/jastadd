/* Copyright (c) 2005-2015, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package core;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Runs an external process in a thread. This eables running processes with a timeout
 * in a test case.
 */
class ProcessThread extends Thread {
  public volatile boolean processFinished = false;
  public volatile int exitCode = 0;
  private final String command;
  private final OutputStream out;
  private final OutputStream errOut;
  private Process process;
  private InputStream in;
  private InputStream errIn;

  public ProcessThread(String command, OutputStream out, OutputStream errOut) {
    this.command = command;
    this.out = out;
    this.errOut = errOut;
  }

  @Override
  public void run() {
    try {
      synchronized (this) {
        process = Runtime.getRuntime().exec(command);
        in = process.getInputStream();
        errIn = process.getErrorStream();
      }
      boolean interrupted = false;
      try {
        while (!isInterrupted()) {
          int data = in.read();
          if (data == -1) {
            break; // End of input.
          }
          out.write(data);
        }
        while (!isInterrupted()) {
          int data = errIn.read();
          if (data == -1) {
            break; // End of input.
          }
          errOut.write(data);
        }
        exitCode = process.waitFor();
        in.close();
        errIn.close();
      } catch (InterruptedException e) {
        interrupted = true;
      } finally {
        if (isInterrupted() || interrupted) {
          killProcess();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
      try {
        errOut.close();
      } catch (IOException e) {
      }
      processFinished = true;
    }
  }

  public synchronized void killProcess() {
    if (process != null) {
      System.err.println("Killing test process.");
      try {
        in.close();
      } catch (IOException ex) {
      }
      try {
        errIn.close();
      } catch (IOException ex) {
      }
      process.destroy();
    }
  }
}
