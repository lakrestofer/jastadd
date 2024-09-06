#!/bin/bash

# generates some nice documentation to index.html

function listtests {
	dir=$1
	dirname=${dir##*/}
	echo "<li><b>$dirname</b><p>" >> index.html
	if [ -e "$dir/description" ]; then
		perl md/Markdown.pl < "$dir/description" >> index.html
	fi
	echo "<p><ul>" >> index.html
	for file in $dir/*; do
		if [ -f $file -a ! "${file##*/}" = "description" ]; then
			echo "<li><a href=\"$file\">${file##*/}</a></li>" >> index.html
		fi
	done
	echo "</ul><p><ul>" >> index.html
	for file in $dir/*; do
		if [ -d $file ]; then
			listtests $file
		fi
	done
	echo "</ul>" >> index.html
}

for desc in `find tests -name description`; do
	echo "<li><b>${desc%/*}</b><p>" >> index.html
	cat $desc >> index.html
done

echo "<html>" > index.html
echo "<head><title>JastAddTest</title></head>" >> index.html
echo "<style type="text/css">"\
	"p code {"\
	"  background-color: #F8F8F8;"\
	"  border: 1px solid #CCC;"\
	"  border-radius: 3px;"\
	"  padding: 0px 5px;"\
	"  white-space: nowrap;"\
	"}"\
	"pre {"\
	"  background-color: #F8F8F8;"\
	"  border: 1px solid #CCC;"\
	"  border-radius: 3px;"\
	"  padding: 6px 10px;"\
	"  overflow: auto;"\
	"  width: 800px;"\
	"}</style>" >> index.html
echo "<body>" >> index.html

perl md/Markdown.pl < README.md >> index.html

echo "<h2>Tests</h2>" >> index.html
echo "<p>All available tests are listed below:" >> index.html
echo "<ul>" >> index.html
listtests "tests"
echo "</ul>" >> index.html

echo "<body>" >> index.html
echo "</body>" >> index.html
