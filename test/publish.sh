#!/bin/sh

set -e

ant doc
mv doc.md doc.text
pandoc doc.text -s -S --toc -H doc.css -o doc.html

echo uploading files to http://jastadd.org/testweb
rsync -rav --chmod=g+w \
	--exclude='.git/' \
	--include='doc.html' \
	--exclude='*' \
	. login.cs.lth.se:/cs/jastadd/testweb

