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

Extract ballerina-redis-connector-<version>.zip and copy containing jars in to <BRE_HOME>/bre/lib/

Building from the source
==================================
If you want to build Ballerina Redis Connector from the source code:

1. Get a clone or download the source from this repository:
    https://github.com/wso2-ballerina/package-redis
2. Run the following Maven command from the ballerina directory: 
    mvn clean install
3. Copy and extract the distribution created at `/component/target/target/ballerina-redis-connector-<version>.zip`  
into <BRE_HOME>/bre/lib/.

Sample
==================================

```ballerina
import ballerina/redis;
import ballerina/io;

function main (string[] args) {
    endpoint redis:Client conn {
        host:"localhost",
        password:"",
        options:{}
    };
    
    io:println("Pinging Redis Server...");
    //Ping Server
    string result = check conn -> ping();
    io:println(result);
    
    io:println("===Executing sample string oprerations===");
    //Sample String Operations
    io:println("Setting value of the key \"Project\" as \"Ballerina\"");
    string stringSetresult = check conn -> setVal("Project", "Ballerina");
    io:println("Querying the server for the value of the key \"Project\"");
    string value = check conn -> get("Project");
    io:println("Reply from the server: " + value);
    
    io:println("===Executing sample list oprerations===");
    //Sample List Operations
    io:println("Pushing 3 elements to NumberList");
    int listPushresult = check conn -> lPush("NumberList", ["One", "Two"]);
    io:println("Reply from server: " + listPushresult);
    
    io:println("Poping an element from NumberList");
    string poppedElement = check conn -> lPop("NumberList");
    io:println("Popped Element: " + poppedElement);
    
    io:println("===Executing sample set oprerations===");
    //Sample Set Operations
    io:println("Adding 3 elements to NumberSet");
    int setAddResult = check conn -> sAdd("NumberSet", ["1", "2", "3"]);
    io:println("Reply from server: " + setAddResult);

    io:println("Querying number of elemenets in the Set");
    int numberOfMembers = check conn -> sCard("NumberSet");
    io:println("Number of members: " + numberOfMembers);
        
    io:println("===Executing sample hash oprerations===");
    //Sample Hash operations
    io:println("Adding a key value pair to a hash");
    boolean hashSetResult = check conn -> hSet("HashKey", "Name", "Manuri");
    io:println("Reply from server: " + hashSetResult);
    
    io:println("Querying the value of the hash field Name");
    string hashGetResult = check conn -> hGet("HashKey", "Name");
    io:println("Value of the hash field \"Name\": " + hashGetResult);
    
    _ = check conn -> quit();
    io:println("Redis connection closed!");
}
```
