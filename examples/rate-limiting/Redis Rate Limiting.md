# Redis Rate Limiting

This example demonstrates how to implement rate limiting for API endpoints using Redis.

## Prerequisites

### 1. Setup Redis Server

Ensure you have Redis server installed and running locally or on a server accessible by your application. 
Refer to the [Setup Guide](https://central.ballerina.io/ballerinax/redis/latest#setup-guide) to set up the Redis server locally.

### 2. Configuration

Install a Redis client library for your programming language. Most languages have official or community-supported Redis client libraries. Make sure to install the appropriate library for your programming environment.

## Configuration

Update IBM MQ related configurations in `Config.toml` in the example directory:

```toml
host = "<host>"
port = "<port>"
```

## Run the Example

Execute the following command to run the example:

```ballerina
bal run
```
