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


## Configuration

Currently we are using same config for all read files. 
In order to tweak configuration check application.conf.
There are two properties: 
```
bits = 400000000
hashes = 5
```
At this page you can play with different values and find the best config for you files: [Bloom filter calculator](https://hur.st/bloomfilter/?n=492&p=&m=10000&k=8).