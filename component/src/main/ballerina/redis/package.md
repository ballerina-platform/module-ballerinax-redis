## Package Overview

This package provides the functionality required to access and manipulate key-value data stored in a Redis database.
Currently the provided functionality is limited to usage Redis as a database/cache. Broker functionality of Redis is
not yet exposed through this package.

### Endpoint

To access a Redis datasource, you must first create an `endpoint`, which is a virtual representation of the physical
endpoint of the Redis database that you are trying to connect to. Create an endpoint of the redis client type
(i.e. `redis:Client`) and provide the necessary connection parameters. This will create a connection/pool of
connections to the given Redis database. A sample for creating an endpoint with a Redis client can be found
below.

### Database operations

Once the endpoint is created, redis commands can be executed through that endpoint.

## Samples

### Creating an endpoint

```ballerina
endpoint redis:Client conn {
    host: "localhost",
    password: "redis",
    options: { connectionPooling: true, isClusterConnection: false, ssl: false,
        startTls: false, verifyPeer: false, database: 0, connectionTimeout: 
        500 }
};
```

This package provides API functions for manipulating Redis String, Hash, List, Set and Sorted Set data types and
basic Redis key/connection commands. For the full list of available configuration options refer the API docs of the
endpoint.

## Example Usage
```ballerina
// Ping Server
string result = check conn->ping();
io:println(result);

// String Operations
var stringSetresult = conn->setVal("Project", "Ballerina");

match stringSetresult {
    string s => io:println("Reply from the server: " + s);
    error e => io:println("Error occurred while calling `setVal`");
}

var value = conn->get("Project");

match value {
	string s => io:println("Reply from the server: " + s);
	() => io:println("Key does not exist");
	error e => io:println("Error occurred while calling `get`");
}

// List Operations

var listPushresult = conn->lPush("NumberList", ["One", "Two"]);

match listPushresult {
	int count => io:println("Number of elements pushed: " + count);
	error e => io:println("Error occurred while calling `lPush`");
}

var lPopResult = conn->lPop("NumberList");

match lPopResult {
	string poppedElement => io:println("Popped element: " + poppedElement);
	() => io:println("Key does not exist");
	error e => io:println("Error occurred while calling `lPop`");
}

// Set Oprerations

var setAddResult = conn->sAdd("NumberSet", ["1", "2", "3"]);

match setAddResult {
	int count => io:println("Number of elements added: " + count);
	error e => io:println("Error occurred while calling `sAdd`");
}

var setCardResult = conn->sCard("NumberSet");

match setCardResult {
	int numberOfMembers => io:println("Number of memebers in the set: " + numberOfMembers);
	error e => io:println("Error occurred while calling `sCard`");
}

// Hash Operations

var hashSetResult = conn->hSet("HashKey", "Name", "Manuri");

match hashSetResult {
	boolean hashSetStatus => io:println("Hash set status: " + hashSetStatus);
	error e => io:println("Error occurred while calling `hSet`");
}

var hashGetResult = conn->hGet("HashKey", "Name");

match hashGetResult {
	string value => io:println("Value of the hash field: " + value);
	error e => io:println("Error occurred while calling `hGet`");
}

// Closing the client

conn.stop();
```
