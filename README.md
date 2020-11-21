# Simple application to check if word/words are in given directory

## Instructions
 
In order to check if files inside directory contain given words run following instruction

```sbt
sbt
runMain test.SimpleSearch directoryContainingTextFiles 
```

You will get then:
```sbt
4 files read in directory directoryContainingTextFiles
```

Then write list list of words separated by space, and you will get
```sbt
file1.txt : 100% file2.txt : 90% 
```

