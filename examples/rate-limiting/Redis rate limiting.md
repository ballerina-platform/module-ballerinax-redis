# Redis rate limiting

This example demonstrates how to implement rate limiting for API endpoints using Redis.

## Prerequisites

### 1. Setup Redis server

Use the following command to start a Redis server using Docker. It will start the Redis server and the 
[`RedisInsight`](https://redis.com/redis-enterprise/redis-insight/), which is a web-based Redis GUI that allows you to 
interact with Redis. 

```bash
docker compose up
```

Once you run the following command, you can access the RedisInsight web interface by navigating to
[http://localhost:5540](http://localhost:5540) in your web browser.

### 2. Configuration

Update Redis client related configurations in `Config.toml` in the example directory:

```toml
host = "<host>"
port = "<port>"
```

## Run the example

Execute the following command to run the example:

```ballerina
bal run
```
