## Overview

The Ballerina connector for Redis enables you to connect to Redis and manipulate key-value data stored in a Redis
database. This connector supports manipulation of various data types such as string, hash, list, set, and sorted set. It
provides functionality to utilize Redis both as a database and cache.

## Prerequisites

Before using this connector in your Ballerina application, complete the following:

* Make sure Redis cache is running in the local environment or use Redis cache running on the cloud (for
  example, [Redis Enterprise Cloud](https://redislabs.com/redis-enterprise-cloud/overview/)).
* Configure the connector with details related to redis cache such as hostname, port number and password.

## Quickstart

To use the Redis connector in your Ballerina application, update the .bal file as follows:

### Step 1: Import connector

Import the `ballerinax/redis` module into the Ballerina project as follows.

```ballerina
import ballerinax/redis;
```

### Step 2: Create a new connector instance

To access a Redis server, you need to create a client object of the `redis:Client` type, providing the required
connection parameters. This establishes a connection or pool of connections to the specified Redis server.

Below is a code sample which creates a client object using `redis:ConnectionConfig`.

 ```ballerina
redis:ConnectionConfig redisConfig = {
    host: "localhost:6379",
    options: {
        connectionPooling: true,
        isClusterConnection: false,
        ssl: false,
        startTls: false,
        verifyPeer: false,
        connectionTimeout: 500
    }
};
 
redis:Client redis = check new (redisConfig);
```

### Step 3: Invoke connector operation

1. You can insert a value for a specific key as follows.

 ```ballerina
 string setResult = check redis->set("Project", "Ballerina");
 ```

2. Use `bal run` command to compile and run the Ballerina program.

**[You can find more samples here](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples)**
