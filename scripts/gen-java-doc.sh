#!/usr/bin/env zsh

set -e # exit if any step has a nonzero exit code
set -o pipefail # including in the middle of a pipe

mkdir -p doc/javadoc

javadoc -d doc/javadoc \                                                                                           -sourcepath src/java:src/gen:src/javacc:tinytemplate/src/java \                                                  -subpackages org \                                                                                               -encoding UTF-8 \
     -Xdoclint:none \
     --ignore-source-errors \
     -tag "apilevel:a:API Level:" \
     -tag "declaredat:a:Declared at:" \
     -tag "aspect:a:Aspect:" \
     -tag "attribute:a:Attribute:" \
     -tag "ast:t:AST Node:" \
     -tag "production:a:Production:" \
     -tag "astdecl:a:AST Declaration:" \
     -tag "bgen:a:Generated:" \
     -quiet
