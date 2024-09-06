/* Copyright (c) 2005-2015, The JastAdd Team
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

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

/**
 * Runs parameterized tests in parallel.
 * The number of parllel test threads is controlled by the system property
 * {@code org.jastadd.testThreads}.
 */
public class ParallelParameterized extends Parameterized {

  private static final int NUM_THREADS = 8;

  public ParallelParameterized(Class<?> klass) throws Throwable {
    super(klass);
    int threads = NUM_THREADS;
    try {
      Properties prop = System.getProperties();
      String threadcount = System.getProperty("org.jastadd.testThreads", "");
      if (!threadcount.isEmpty()) {
        threads = Integer.parseInt(threadcount);
      }
    } catch (Exception ignored) {
    }
    if (threads > 1) {
      setScheduler(new ThreadPoolScheduler(threads));
    }
  }

  private static class ThreadPoolScheduler implements RunnerScheduler {
    private final ExecutorService executor;

    public ThreadPoolScheduler(int threads) {
      executor = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void finished() {
      executor.shutdown();
      try {
        // NB: this time limit will limit total testing time.
        executor.awaitTermination(1000, TimeUnit.MINUTES);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void schedule(Runnable test) {
      executor.submit(test);
    }
  }
}
