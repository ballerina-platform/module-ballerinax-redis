# Examples

The `ballerinax/redis` connector provides practical examples illustrating usage in various scenarios. Explore these [examples](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples), covering use cases like cache management, session management, and rate limiting.

1. [Cache Management](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/cache-management) - Implement caching using Redis to improve application performance.

2. [Session Management](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/session-management) - Use Redis to manage user sessions efficiently.

3. [Rate Limiting](https://github.com/ballerina-platform/module-ballerinax-redis/tree/master/examples/rate-limiting) - Implement rate limiting for API endpoints using Redis.

## Prerequisites

1. Ensure you have Redis server installed and running locally or on a server accessible by your application.
   Refer to the [Setup Guide](https://central.ballerina.io/ballerinax/redis/latest#setup-guide) to set up the Redis server locally.

2. For each example, create a `Config.toml` file with your Redis server configuration. Here's an example of how your `Config.toml` file should look:

3. . Here's an example of how your `config.toml` file should look:

    ```toml
    host="<host>"
    port="<port>"
    ```

## Running an Example

Execute the following commands to build an example from the source:

* To build an example:

    ```bash
    bal build
    ```

* To run an example:

    ```bash
    bal run
    ```

## Building the Examples with the Local Module

**Warning**: Due to the absence of support for reading local repositories for single Ballerina files, the Bala of the module is manually written to the central repository as a workaround. Consequently, the bash script may modify your local Ballerina repositories.

Execute the following commands to build all the examples against the changes you have made to the module locally:

* To build all the examples:

    ```bash
    ./build.sh build
    ```

* To run all the examples:

    ```bash
    ./build.sh run
    ```
