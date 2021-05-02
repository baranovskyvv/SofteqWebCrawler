# SofteqWebCrawler
This multithreaded program follows the links and searches for keywords in each page, while counting those words. Records the counting results in 2 files. In the first top 10 pages, in the second all verified links. Files are stored in csv format.
There are 2 modes of operation, with default parameters and with your own data. (Default parameters: 10,000 pages, page depth 8, start site: https://en.wikipedia.org/wiki/Elon_Musk, search words: "Gigafactory", " Musk "," Tesla "," Elon Mask "

Technologies:
Java 11
Maven
JSOUP
JUNIT
LOG4J

Enter for running: mvn clean install exec:java –Dexec.mainClass=Main
