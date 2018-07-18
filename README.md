[![Build Status](https://travis-ci.org/wso2-ballerina/package-redis.svg?branch=master)](https://travis-ci.org/wso2-ballerina/package-redis)

# package-redis

Ballerina Redis Connector is used to connect Ballerina with a Redis data source. With the Ballerina Redis Connector 
following actions are supported.

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

Steps to Configure
==================================

Extract wso2-redis-<version>.zip and copy containing jars in to <BRE_HOME>/bre/lib/

Building from the source
==================================
If you want to build Ballerina Redis Connector from the source code:

1. Get a clone or download the source from this repository:
    https://github.com/wso2-ballerina/package-redis
2. Run the following Maven command from the ballerina directory: 
    mvn clean install
3. Extract the distribution created at `/component/target/wso2-redis-<version>.zip`. Run the install.{sh/bat} script to install the package.
You can uninstall the package by running uninstall.{sh/bat}.

Sample
==================================

```ballerina
import wso2/redis;
import ballerina/io;

function main(string... args) {
     endpoint redis:Client conn {
        host: "localhost",
        password: "",
        options: { connectionPooling: true, isClusterConnection: false, ssl: false,
            startTls: false, verifyPeer: false, connectionTimeout: 500 }
     };

    io:println("Pinging Redis Server...");
    //Ping Server
    string result = check conn->ping();
    io:println(result);

    io:println("\n===Executing sample string oprerations===");
    //Sample String Operations
    io:println("Setting value of the key \"Project\" as \"Ballerina\"");
    var stringSetresult = conn->setVal("Project", "Ballerina");

    match stringSetresult {
        string s => io:println("Reply from the server: " + s);
        error e => io:println("Error occurred while calling `setVal`");
    }

    io:println("\nQuerying the server for the value of the key \"Project\"");
    var value = conn->get("Project");

    match value {
        string s => io:println("Reply from the server: " + s);
        () => io:println("Key does not exist");
        error e => io:println("Error occurred while calling `get`");
    }

    io:println("\n===Executing sample list oprerations===");
    //Sample List Operations
    io:println("Pushing 3 elements to NumberList");
    var listPushresult = conn->lPush("NumberList", ["One", "Two"]);

    match listPushresult {
        int count => io:println("Number of elements pushed: " + count);
        error e => io:println("Error occurred while calling `lPush`");
    }

    io:println("\nPoping an element from NumberList");
    var lPopResult = conn->lPop("NumberList");

    match lPopResult {
        string poppedElement => io:println("Popped element: " + poppedElement);
        () => io:println("Key does not exist");
        error e => io:println("Error occurred while calling `lPop`");
    }

    io:println("\n===Executing sample set oprerations===");
    //Sample Set Operations
    io:println("Adding 3 elements to NumberSet");
    var setAddResult = conn->sAdd("NumberSet", ["1", "2", "3"]);

    match setAddResult {
        int count => io:println("Number of elements added: " + count);
        error e => io:println("Error occurred while calling `sAdd`");
    }

    io:println("\nQuerying number of elemenets in the Set");
    var setCardResult = conn->sCard("NumberSet");

    match setCardResult {
        int numberOfMembers => io:println("Number of memebers in the set: " + numberOfMembers);
        error e => io:println("Error occurred while calling `sCard`");
    }
    io:println("\n===Executing sample hash oprerations===");
    //Sample Hash operations
    io:println("Adding a key value pair to a hash");
    var hashSetResult = conn->hSet("HashKey", "Name", "Manuri");

    match hashSetResult {
        boolean hashSetStatus => io:println("Hash set status: " + hashSetStatus);
        error e => io:println("Error occurred while calling `hSet`");
    }

    io:println("\nQuerying the value of the hash field Name");
    var hashGetResult = conn->hGet("HashKey", "Name");

    match hashGetResult {
        string returnedValue => io:println("Value of the hash field: " + returnedValue);
        error e => io:println("Error occurred while calling `hGet`");
    }

    conn.stop();
    io:println("\nRedis connection closed!");
}
```
