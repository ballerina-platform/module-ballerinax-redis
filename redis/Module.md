## Overview

The Ballerina connector for Redis allows you to connect to Redis and manipulate key-value data stored in a Redis database. You can use the connector to manipulate string, hash, list, set, and sorted set data types. The functionality of the Ballerina connector for Redis is limited to the use of Redis as a database and cache. The message broker functionality of Redis is not yet exposed through this module.

## Prerequisites
Before using this connector in your Ballerina application, complete the following:
* Make sure Redis cache is running in the local environment or use Redis cache running on the cloud (for example, [Redis Enterprise Cloud](https://redislabs.com/redis-enterprise-cloud/overview/)).
* Configure the connector with details related to redis cache such as hostname, port number and password.

## Quickstart

To use the Redis connector in your Ballerina application, update the .bal file as follows:

### Step 1: Import connector
Import the `ballerinax/redis` module into the Ballerina project as follows..
```ballerina
import ballerinax/redis;
```
### Step 2: Create a new connector instance
To access a Redis datasource, you must first create a `client` object. Create a client of the redis client type (i.e. `redis:Client`) and provide the necessary connection parameters. This creates a connection/pool of connections to the given Redis database. A method for creating a client with a Redis client can be found below.
```ballerina
redis:ConnectionConfig redisConfig = {
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
1. You can insert a value for a specific key as follows. 
    ```ballerina
    string stringSetresult = check connection->set("Project", "Ballerina");
    ```
2. Use `bal run` command to compile and run the Ballerina program. 

**[You can find more samples here](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/redis/samples)**
