# Task

Implement sorting of big text file which does not fit in RAM.

Required memory should not depend on file size.

One line in file is much smaller than memory size.

To test the program also implement big files generator which gets lines count and their MAX length as parameters.

---

# Solution

To sort a big file we split it into the smaller files, sort all of them in memory and then merge back into one file.

Class BigFileGenerator (a.k.a. BFG :) has main method to generate a big file.

Class FileSorter has main method to sort the big file.

---

# Performance tests

Tested on my Intel Core i7-7700 CPU and SSD drive.

JVM maximum heap size was limited to 1024 Mb.

Line MIN length: 9

Line MAX length: 15

| Lines in file | File size | Time to sort |
| :------------:|:---------:| :-----------:|
| 10 million    | 128 Mb    | 7 sec        |
| 100 million   | 1.4 Gb    | 78 sec       |
| 300 million   | 4 Gb      | 279 sec      |