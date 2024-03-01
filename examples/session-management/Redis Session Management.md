# Redis Session Management

This example demonstrates how to use Redis to manage user sessions efficiently.

## Prerequisites

### 1. Setup Redis Server

Ensure you have Redis server installed and running locally or on a server accessible by your application.
Refer to the [Setup Guide](https://central.ballerina.io/ballerinax/redis/latest#setup-guide) to set up the Redis server locally.

### 2. Configuration

Update Redis client related configurations in `Config.toml` in the example directory:

```toml
host = "<host>"
port = "<port>"
```

## Run the Example

Execute the following command to run the example:

```ballerina
bal run
```
