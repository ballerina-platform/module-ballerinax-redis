## Module Overview

This module provides the functionality required to access and manipulate key-value data stored in a Redis database.
Currently the provided functionality is limited to usage Redis as a database/cache. Broker functionality of Redis is
not yet exposed through this module.

### Client

To access a Redis datasource, you must first create a `client`object. Create a client of the redis client type
(i.e. `redis:Client`) and provide the necessary connection parameters. This will create a connection/pool of
connections to the given Redis database. A sample for creating a client with a Redis client can be found
below.

### Database operations

Once the client is created, redis commands can be executed through that client.

## Samples

### Creating a Client

```ballerina
redis:Client conn = new({
    host: "localhost",
    password: "redis",
    options: { connectionPooling: true, isClusterConnection: false, ssl: false,
        startTls: false, verifyPeer: false, database: 0, connectionTimeout: 
        500 }
});
```

This module provides API functions for manipulating Redis String, Hash, List, Set and Sorted Set data types and
basic Redis key/connection commands. For the full list of available configuration options refer the API docs of the
client.

## Example Usage
```ballerina
// Ping Server
string result = checkpanic conn->ping();
io:println(result);

// String Operations
var stringSetresult = conn->setVal("Project", "Ballerina");

if (stringSetresult is string) {
    io:println("Reply from the server: " + stringSetresult);
} else {
    io:println("Error occurred while calling `setVal`");
}

var value = conn->get("Project");

if (value is string) {
    io:println("Reply from the server: " + value);
} else if (value is ()) {
    io:println("Key does not exist");
} else {
    io:println("Error occurred while calling `get`");
}

// List Operations

var listPushresult = conn->lPush("NumberList", ["One", "Two"]);

if (listPushresult is int) {
    io:println("Number of elements pushed: " + listPushresult);
} else {
    io:println("Error occurred while calling `lPush`");
}

var lPopResult = conn->lPop("NumberList");

if (lPopResult is string) {
    io:println("Popped element: " + lPopResult);
} else if (lPopResult is ()) {
    io:println("Key does not exist");
} else {
    io:println("Error occurred while calling `lPop`");
}

// Set Oprerations

var setAddResult = conn->sAdd("NumberSet", ["1", "2", "3"]);

if (setAddResult is int) {
    io:println("Number of elements added: " + setAddResult);
} else {
    io:println("Error occurred while calling `sAdd`");
}

var setCardResult = conn->sCard("NumberSet");

if (setCardResult is int) {
    io:println("Number of memebers in the set: " + setCardResult);
} else {
    io:println("Error occurred while calling `sCard`");
}

// Hash Operations

var hashSetResult = conn->hSet("HashKey", "Name", "Manuri");

if (hashSetResult is boolean) {
    io:println("Hash set status: " + hashSetResult);
} else {
    io:println("Error occurred while calling `hSet`");
}

var hashGetResult = conn->hGet("HashKey", "Name");

if (hashGetResult is string) {
    io:println("Value of the hash field: " + hashGetResult);
} else {
    io:println("Error occurred while calling `hGet`");
}

// Closing the client

conn.stop();
```
