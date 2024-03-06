# Ballerina Redis Connector

[![Build](https://github.com/ballerina-platform/module-ballerinax-redis/actions/workflows/ci.yml/badge.svg)](https://github.com/ballerina-platform/module-ballerinax-redis/actions/workflows/ci.yml)
[![Trivy](https://github.com/ballerina-platform/module-ballerinax-redis/actions/workflows/trivy-scan.yml/badge.svg)](https://github.com/ballerina-platform/module-ballerinax-redis/actions/workflows/trivy-scan.yml)
[![codecov](https://codecov.io/gh/ballerina-platform/module-ballerinax-redis/branch/master/graph/badge.svg)](https://codecov.io/gh/ballerina-platform/module-ballerinax-redis)
[![GraalVM Check](https://github.com/ballerina-platform/module-ballerinax-redis/actions/workflows/build-with-bal-test-graalvm.yml/badge.svg)](https://github.com/ballerina-platform/module-ballerinax-redis/actions/workflows/build-with-bal-test-graalvm.yml)
[![GitHub Last Commit](https://img.shields.io/github/last-commit/ballerina-platform/module-ballerinax-redis.svg)](https://github.com/ballerina-platform/module-ballerinax-redis/commits/master)
[![GitHub Issues](https://img.shields.io/github/issues/ballerina-platform/ballerina-library/module/redis.svg?label=Open%20Issues)](https://github.com/ballerina-platform/ballerina-library/labels/module%2Fredis)

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
To start the Redis server, open a terminal and execute the following command.

```bash
redis-server
```

#### Step 3: Verify Redis connectivity
After starting the Redis server, you can verify the connectivity using the Redis CLI.
Open a new terminal and execute the following command.

```bash
redis-cli ping
```

If the server is running, you will receive a response of `"PONG"`.

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

The Redis connector provides practical examples illustrating usage in various scenarios.
Explore these [examples](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/) covering common Redis operations.

1. [Cache management](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/cache-management) - Implement caching using Redis to improve application performance.

2. [Session management](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/session-management) - Use Redis to manage user sessions efficiently.

3. [Rate limiting](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/rate-limiting) - Implement rate limiting for API endpoints using Redis.

## Issues and projects

The **Issues** and **Projects** tabs are disabled for this repository as this is part of the Ballerina library. 
To report bugs, request new features, start new discussions, view project boards, etc., 
visit the Ballerina library [parent repository](https://github.com/ballerina-platform/ballerina-library).

This repository only contains the source code for the package.

## Building from the source

### Prerequisites

1. Download and install Java SE Development Kit (JDK) version 17. You can download it from either of the following sources:

    * [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
    * [OpenJDK](https://adoptium.net/)

   > **Note:** After installation, remember to set the `JAVA_HOME` environment variable to the directory where JDK was installed.

2. Download and install [Ballerina Swan Lake](https://ballerina.io/).

3. Download and install [Docker](https://www.docker.com/get-started).
   
   > **Note**: Ensure that the Docker daemon is running before executing any tests.

### Build options

Execute the commands below to build from the source.

1. To build the package:

   ```bash
   ./gradlew clean build
   ```

2. To run the tests:

   ```bash
   ./gradlew clean test
   ```

3. To build the without the tests:

   ```bash
   ./gradlew clean build -x test
   ```

4. To run tests against different environment:

   ```bash
   ./gradlew clean test -Pgroups=<Comma separated groups/test cases>
   ```

5. To debug package with a remote debugger:

   ```bash
   ./gradlew clean build -Pdebug=<port>
   ```

6. To debug with the Ballerina language:

   ```bash
   ./gradlew clean build -PbalJavaDebug=<port>
   ```

7. Publish the generated artifacts to the local Ballerina Central repository:

    ```bash
    ./gradlew clean build -PpublishToLocalCentral=true
    ```

8. Publish the generated artifacts to the Ballerina Central repository:

   ```bash
   ./gradlew clean build -PpublishToCentral=true
   ```

## Contribute to Ballerina

As an open-source project, Ballerina welcomes contributions from the community.

For more information, go to the [contribution guidelines](https://github.com/ballerina-platform/ballerina-lang/blob/master/CONTRIBUTING.md).

## Code of conduct

All the contributors are encouraged to read the [Ballerina Code of Conduct](https://ballerina.io/code-of-conduct).

## Useful links

* For more information go to the [`redis` package](https://lib.ballerina.io/ballerinax/redis/latest).
* For example demonstrations of the usage, go to [Ballerina By Examples](https://ballerina.io/learn/by-example/).
* Chat live with us via our [Discord server](https://discord.gg/ballerinalang).
* Post all technical questions on Stack Overflow with the [#ballerina](https://stackoverflow.com/questions/tagged/ballerina) tag.
