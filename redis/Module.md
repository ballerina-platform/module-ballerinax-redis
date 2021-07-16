## Overview

Ballerina connector for Redis is connecting Redis via Ballerina language easily. This module provides the functionality required to access and manipulate key-value data stored in a Redis database this module provides API functions for manipulating Redis String, Hash, List, Set and Sorted Set data types.
Currently the provided functionality is limited to usage Redis as a database/cache. Broker functionality of Redis is not yet exposed through this module.

## Prerequisites
Before using this connector in your Ballerina application, complete the following:
* Make sure redis cache is running in local environment or use redis cache running on cloud such as [Redis Enterprise Cloud](https://redislabs.com/redis-enterprise-cloud/overview/)
* Configure the connector with details related to redis cache like hostname, port number and password

## Quickstart

To use the Redis connector in your Ballerina application, update the .bal file as follows:

### Step 1: Import connector
First, import the ballerinax/redis module into the Ballerina project.
```ballerina
import ballerinax/redis;
```
### Step 2: Create a new connector instance
To access a Redis datasource, you must first create a `client` object. Create a client of the redis client type (i.e. `redis:Client`) and provide the necessary connection parameters. This will create a connection/pool of connections to the given Redis database. A method for creating a client with a Redis client can be found below.
```ballerina
redis:ClientEndpointConfiguration redisConfig = {
    host: "localhost",
    password: "",
    options: {
        connectionPooling: true,
        isClusterConnection: false,
        ssl: false,
        startTls: false,
        verifyPeer: false,
        connectionTimeout: 500
    }
};

redis:Client conn = check new (redisConfig);
```
### Step 3: Invoke connector operation
1. You can insert value for specific key. 
    ```ballerina
    string stringSetresult = check connection->set("Project", "Ballerina");
    ```
2. Use `bal run` command to compile and run the Ballerina program. 

## Quick reference
The following code snippets shows how the connector operations can be used in different scenarios after initializing the client.
* Set value to a key

    This section shows how to insert a string value to a cache. This can be done by specifying name of the key and value to be inserted as parameters. On successful insertion, function returns string 'OK'
    ``` ballerina
    string stringSetResult = check connection->set("Project", "Ballerina");
    ```

* Get value of a key

    This section shows how to fetch a inserted string value from a cache. This can be done by specifying name of the key as parameter. On successful operation, function returns string containing value corresponding for that key
    ``` ballerina
    string? stringGetResult = check connection->get("Project");
    if (stringGetResult is string) {
        log:printInfo("String value of key: " + stringGetResult);
    } else {
        log:printInfo("Key does not exist");
    }
    ```
    
* Push values to list

    This section shows how to insert list elements to a cache. This can be done by specifying name of the key and list of values to be inserted as parameters. On successful insertion, function returns number of list elements pushed
    ``` ballerina
    int listPushresult = check connection->lPush("NumberList", ["One", "Two"]);
    ```

* Pop values in the list

    This section shows how to get and pop from list element in a cache. This can be done by specifying name of the key as parameter. On successful operation, function returns last element from list of elements
    ``` ballerina
    string? lPopResult = check connection->lPop("NumberList");
    if (lPopResult is string) {
        log:printInfo("Popped element: " + lPopResult);
    } else if (lPopResult is ()) {
        log:printInfo("Key does not exist");
    }
    ```
* Usage of multiple logical databses

    Redis logical database having the specified zero-based numeric index. New connections always use the database 0. Redis Cluster only supports database 0. In this redis connector we can select logical database during client initialization by providing databse number in configuration, if not specified it uses database 0. For usage of multiple logical databases have to create clients with database specified in configuration
    ```ballerina
    redis:ClientEndpointConfiguration redisConfig = {
        host: "localhost:6379",
        password: "",
        options: { 
            connectionPooling: true, 
            isClusterConnection: false, 
            ssl: false,
            startTls: false, 
            verifyPeer: false, 
            database: 1 
        }
    };
    ```

***[You can find more samples here](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/redis/samples)***
