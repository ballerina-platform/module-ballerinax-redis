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
import ballerina.redis;

function main (string[] args) {
    endpoint redis:Client conn {
        host:"localhost",
        password:"",
        options:{}
    };
    
    println("Pinging Redis Server...");
    //Ping Server
    string result = conn -> ping();
    println(result);
    
    println("===Executing sample string oprerations===");
    //Sample String Operations
    println("Setting value of the key \"Project\" as \"Ballerina\"");
    string stringSetresult = conn -> set("Project", "Ballerina");
    println("Querying the server for the value of the key \"Project\"");
    string value = conn -> get("Project");
    println("Reply from the server: " + value);
    
    println("===Executing sample list oprerations===");
    //Sample List Operations
    println("Pushing 3 elements to NumberList");
    int listPushresult = conn -> lPush("NumberList", ["One", "Two"]);
    println("Reply from server: " + listPushresult);
    
    println("Poping an element from NumberList");
    string poppedElement = conn -> lPop("NumberList");
    println("Popped Element: " + poppedElement);
    
    println("===Executing sample set oprerations===");
    //Sample Set Operations
    println("Adding 3 elements to NumberSet");
    int setAddResult = conn -> sAdd("NumberSet", ["1", "2", "3"]);
    println("Reply from server: " + setAddResult);

    println("Querying number of elemenets in the Set");
    int numberOfMembers = conn -> sCard("NumberSet");
    println("Number of members: " + numberOfMembers);
        
    println("===Executing sample hash oprerations===");
    //Sample Hash operations
    println("Adding a key value pair to a hash");
    boolean hashSetResult = conn -> hSet("HashKey", "Name", "Manuri");
    println("Reply from server: " + hashSetResult);
    
    println("Querying the value of the hash field Name");
    string hashGetResult = conn -> hGet("HashKey", "Name");
    println("Value of the hash field \"Name\": " + hashGetResult);
    
    _ = conn -> quit();
    println("Redis connection closed!");
}

```
