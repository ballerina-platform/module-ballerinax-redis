# Redis session management

This example demonstrates how to use Redis to manage user sessions efficiently.

## Prerequisites

### 1. Setup Redis server

Ensure you have Redis server installed and running locally or on a server accessible by your application.
Refer to the [Setup Guide](https://central.ballerina.io/ballerinax/redis/latest#setup-guide) to set up the Redis server locally.

### 2. Configuration

Update Redis client related configurations in `Config.toml` in the example directory:

```toml
# redis server configurations
host = "<host>"
port = "<port>"

# user-store based basic-auth configurations
[[ballerina.auth.users]]
username="alice"
password="alice@123"
scopes=["developer"]

[[ballerina.auth.users]]
username="nipunaranasinghe"
password="nipuna@123"
scopes=["developer", "admin"]

[[ballerina.auth.users]]
username="eve"
password="eve@123"
```

## Run the example

Execute the following command to run the example:

```ballerina
bal run
```
