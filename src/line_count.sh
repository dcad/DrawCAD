$files = find . -name '*.java' 

$fileCount = $files| wc -l

$lineCount = 0

echo "Bash version ${BASH_VERSION}..."
for i in {0..fileCount..1}
  do
     echo "file name $files[i] "
	echo 'wc -l $files[i]'
 done
