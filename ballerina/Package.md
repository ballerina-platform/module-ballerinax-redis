## Overview

[Redis](https://redis.io/) is an open-source, in-memory data structure store that can be used as a database,
cache, and message broker. It supports various data structures such as strings, hashes, lists, sets, and more.

The `ballerinax/redis` package offers APIs to connect to Redis servers and to manipulate key-value data, providing
functionality to utilize Redis both as a database and cache. The current connector is compatible Redis server
versions up to 7.2.x.

## Setup guide

To use the Redis connector, you need to have a Redis server running and accessible. For that, you can either install
Redis locally or use a cloud-based Redis service.

### Setting up a Redis server locally

#### Step 1: Install Redis server
1. Download and install Redis from the [official website](https://redis.io/download/).

2. Follow the installation instructions based on your operating system.

#### Step 2: Start Redis server
After installation, start the Redis server by executing the appropriate command based on your operating system.
```bash
redis-server
```

#### Step 3: Verify Redis connectivity
Ensure that the Redis server is running and accessible.

```bash
redis-cli ping
```

If the server is running, you will receive a response of 'PONG'.

### Setting up a cloud-based Redis service

Several cloud providers offer managed Redis services. Some of the popular ones are:

1. **Redis Labs:** [Redis Labs](https://redis.com/) offers Redis Enterprise Cloud, a fully-managed Redis service in the cloud.
2. **Amazon ElastiCache:** Amazon Web Services provides [ElastiCache](https://aws.amazon.com/elasticache/), a managed Redis service for deploying, operating, and scaling Redis in the AWS Cloud.
3. **Google Cloud Memorystore:** Google Cloud offers [Memorystore](https://cloud.google.com/memorystore?hl=en) for Redis, a fully-managed Redis service on Google Cloud Platform.
4. **Microsoft Azure Cache for Redis:** [Azure Cache for Redis](https://azure.microsoft.com/en-us/products/cache) is a fully-managed Redis service provided by Microsoft Azure.

Choose the hosting solution that best fits your requirements and proceed with its setup instructions.

## Quickstart

To use the `redis` connector in your Ballerina application, follow the steps below:

### Step 1: Import the module

Import the `redis` module.

```ballerina
import ballerinax/redis;
```

### Step 2: Instantiate a new connector

Create a `redis:ConnectionConfig` instance by giving the Redis server configuration and
initialize the Ballerina Redis client using it.

```ballerina
redis:Client redis = new (
    connection = {
        host: "localhost",
        port: 6379
    }
);
```

### Step 3: Invoke the connector operation

Now, you can use the available connector operations to interact with Redis.

#### Set a key-value pair

```ballerina
check redis->set("key", "value");
```

#### Get value by key

```ballerina
string value = check redis->get("key");
```

### Step 4: Run the Ballerina application

Save the changes and run the Ballerina application using the following command.

```bash
bal run
```

## Examples

The Redis connector provides practical examples illustrating usage in various scenarios. Explore these examples covering common Redis operations.

1. [Cache Management](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/cache-management) - Implement caching using Redis to improve application performance.

2. [Session Management](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/session-management) - Use Redis to manage user sessions efficiently.

3. [Rate Limiting](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/rate-limiting) - Implement rate limiting for API endpoints using Redis.
