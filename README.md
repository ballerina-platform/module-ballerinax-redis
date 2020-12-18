[![Build Status](https://travis-ci.org/wso2-ballerina/module-redis.svg?branch=master)](https://travis-ci.org/wso2-ballerina/module-redis)

# module-ballerinax-redis

Ballerina Redis Module is used to connect Ballerina with a Redis data source. With the Ballerina Redis Module 
following operations are supported.

**Basic Commands**

* AUTH
* ECHO
* PING
* QUIT

**String commands**

* APPEND
* BITCOUNT
* BITOP
* DECR
* DECRBY
* GETBIT
* GETRANGE
* GETSET
* GET
* INCR
* INCRBY
* INCRBYFLOAT
* MGET
* MSET
* MSETNX
* PSETEX
* SET
* SETBIT
* SETEX
* SETNX
* SETRANGE
* STRLN

**List Commands**
 * BLPOP
 * BRPOP
 * LINDEX
 * LINSERT
 * LLEN
 * LPOP
 * LPUSH
 * LPUSHX
 * LRANGE
 * LREM
 * LSET
 * LTRIM
 * RPOP
 * RPOPLPUSH
 * RPUSH
 * RPUSHX
 
**Set Commands**
 
 * SADD
 * SCARD
 * SDIFF
 * SDIFFSTORE
 * SINTER
 * SINTERSTORE
 * SISMEMBER
 * SMEMBERS
 * SMOVE
 * SPOP
 * SRANDMEMBER
 * SREM
 * SUNION
 * SUNIONSTORE
 
**Sorted Set Commands**
 
 * ZADD
 * ZCARD
 * ZCOUNT
 * ZINCRBY
 * ZINTERSTORE
 * ZLEXCOUNT
 * ZRANGE
 * ZRANGEBYLEX
 * ZRANK
 * ZREM
 * ZREMRANGEBYLEX
 * ZREMRANGEBYRANK
 * ZREMRANGEBYSCORE
 * ZREVRANGE
 * ZREVRANGEBYLEX
 * ZREVRANGEBYSCORE
 * ZREVRANK
 * ZSCORE
 * ZUNIONSTORE
 
**Hash Commands**
 
 * HDEL
 * HEXISTS
 * HGET
 * HGETALL
 * HINCRBY
 * HINCRBYFLOAT
 * HKEYS
 * HMGET
 * HMSET
 * HSET
 * HSETNX
 * HSTRLN
 * HVALS
 
**Key Commands**

* DEL
* EXISTS
* EXPIRE
* KEYS
* MOVE
* PERSIST
* PEXPIRE
* PTTL
* RANDOMKEY
* RENAME
* SORT
* TTL
* TYPE

Building from the source
==================================
If you want to build Ballerina Redis Connector from the source code:

1. Get a clone or download the source from this repository:
    https://github.com/ballerina-platform/module-ballerinax-redis
2. Run the following Maven command from the module-ballerinax-redis directory:

        mvn clean install
3. Run the following ballerina command module-ballerinax-redis/redis directory:

    3.1 If running on a Unix OS:
         
        ballerina build
    3.2 The unit tests will not run in Windows OS. Therefore, if running on a Windows OS skip the tests using following command:
         
        ballerina build --skip-tests -c
         
## Compatibility

|                             |           Version           |
|:---------------------------:|:---------------------------:|
| Ballerina Language          |      Swan Lake Preview7     |

```ballerina
import ballerinax/redis;
import ballerina/io;

redis:ClientEndpointConfiguration redisConfig = {
        host: "localhost:6379",
        password: "",
        options: { connectionPooling: true, isClusterConnection: false, ssl: false,
            startTls: false, verifyPeer: false, connectionTimeout: 500 }
};

public function main() returns error? {
    redis:Client conn =  check new (redisConfig);

    io:println("Pinging Redis Server...");
    //Ping Server
    var result = conn->ping();
    if (result is string) {
        io:println(result);
    } else {
        io:println(result.message());
    }

    io:println("\n===Executing sample string oprerations===");
    //Sample String Operations
    io:println("Setting value of the key \"Project\" as \"Ballerina\"");
    var stringSetresult = conn->set("Project", "Ballerina");

    if (stringSetresult is string) {
        io:println("Reply from the server: " + stringSetresult);
    } else {
        io:println("Error occurred while calling `set`");
    }

    io:println("\nQuerying the server for the value of the key \"Project\"");
    var value = conn->get("Project");

    if (value is string) {
        io:println("Reply from the server: " + value);
    } else {
        io:println("Error occurred while calling `get`");
    }

    io:println("\n===Executing sample list oprerations===");
    //Sample List Operations
    io:println("Pushing 2 elements to NumberList");
    var listPushresult = conn->lPush("NumberList", ["One", "Two"]);

    if (listPushresult is int) {
        io:println(io:sprintf("Number of elements pushed: %s", listPushresult));
    } else {
        io:println("Error occurred while calling `lPush`");
    }

    io:println("\nPoping an element from NumberList");
    var lPopResult = conn->lPop("NumberList");

    if (lPopResult is string) {
        io:println("Popped element: " + lPopResult);
    } else {
        io:println("Error occurred while calling `lPop`");
    }
    io:println("\n===Executing sample set oprerations===");
    //Sample Set Operations
    io:println("Adding 3 elements to NumberSet");
    var setAddResult = conn->sAdd("NumberSet", ["1", "2", "3"]);

    if (setAddResult is int) {
        io:println(io:sprintf("Number of elements added: : %s", setAddResult));
    } else {
        io:println("Error occurred while calling `sAdd`");
    }

    io:println("\nQuerying number of elemenets in the Set");
    var setCardResult = conn->sCard("NumberSet");

    if (setCardResult is int) {
        io:println(io:sprintf("Number of memebers in the set: %s", setCardResult));
    } else {
        io:println("Error occurred while calling `sCard`");
    }

    io:println("\n===Executing sample hash oprerations===");
    //Sample Hash operations
    io:println("Adding a key value pair to a hash");
    var hashSetResult = conn->hSet("HashKey", "Name", "Manuri");

    if (hashSetResult is boolean) {
        io:println(io:sprintf("Hash set status: %s", hashSetResult));
    } else {
        io:println("Error occurred while calling `hSet`");
    }

    io:println("\nQuerying the value of the hash field Name");
    var hashGetResult = conn->hGet("HashKey", "Name");

    if (hashGetResult is string) {
        io:println("Value of the hash field: " + hashGetResult);
    } else {
        io:println("Error occurred while calling `hGet`");
    }

    conn.stop();
    io:println("\nRedis connection closed!");
}

```
