


This API overloads the Hadoop API, and presents new features to transfer and treat large files. 
These features are not available on the native library of Hadoop. Why ?

The Hadoop API proposes two methods to read and write files. These methods can't treat large files, because 
the content of the file is passed through a byte array and this array is charged in-memory. Large data can't be 
fed in-memory.

To achieve the transfert of large file from Client to Server, the API return a distributed iterator object to developer. 
This iterator treats the transfer of the large file. It splits the large file on several splits, which will be transeferd 
through an asynchronous manner. The same logic will be applied to download a file from Server to Client.

The different projects are:

-> API-DFS       : The API treats files whose the maximum size threshold is 10Mo.

-> API-DFS2      : The API treats files whose the size is greatter than 10Mo.

-> Common        : Common library for the two APIs.

-> DFSHelloWorld : The project simulate the use of the API-DFS2.

