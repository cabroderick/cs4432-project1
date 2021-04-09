Name: Collin Broderick
Student id: 330551505

I. Running instructions
The code can be compiled using the following command line arguments in the \src folder:
javac *.java
java App [pool size]

II. Test results
Set 430 "F05-Rec450, Jane Do, 10 Hill Rd, age020."
working

Get 430
working

Get 20
working

Set 430 "F05-Rec450, John Do, 23 Lake Ln, age056."
working

Pin 5
working

Unpin 3
working

Get 430
working

Pin 5
working

Get 646
working

Pin 3
working

Set 10 "F01-Rec010, Tim Boe, 09 Deer Dr, age009."
working

Unpin 1
working

Get 355
working

Pin 2
working

Get 156
working

Set 10 "F01-Rec010, No Work, 31 Hill St, age100."
working

Pin 7
working

Get 10
working

Unpin 3
working

Unpin 2
working

Get 10
working

Pin 6
working

ALL WORKING

III. Design decisions
-getBlock and getFrameRecord were added to BufferPool in order to facilitate access of frames and buffers. The former takes an input k and gives a correpsonding physical block number, and the latter gets the record number within the frame being looked arguments
-loadFrame was added to facilitate loading into the first available frame, including eviction if needed
-The lastEvicted field was added to BufferPool to indicate which frame was last evicted