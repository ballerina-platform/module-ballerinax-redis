# Specification: Ballerina Redis Library

_Authors_: @NipunaRanasinghe
_Reviewers_: @AyeshLK @AzeemMuzammil
_Created_: 2022/02/19  
_Updated_: 2022/02/19  
_Edition_: Swan Lake

## Introduction

This specification outlines the functionality provided by the Redis connector for
the [Ballerina language](https://ballerina.io/).
The library facilitates interaction with Redis, enabling users to store, retrieve, and manipulate data using Ballerina.

The Redis library specification undergoes continuous refinement, and any updates are documented and released under
relevant GitHub tags.

If you have any feedback or suggestions about the library, start a discussion via
a [GitHub issue](https://github.com/ballerina-platform/ballerina-library/issues)
or in the [Discord server](https://discord.gg/ballerinalang).
Based on the outcome of the discussion, the specification and implementation can be updated. Community feedback is
always welcome.

Any accepted proposal, which affects the specification is stored under `/docs/proposals`. Proposals under discussion can
be found with the label `type/proposal` in GitHub.

The conforming implementation of the specification is released to Ballerina Central. Any deviation from the
specification is considered a bug.

## Contents

1. [Overview](#1-overview)
2. [Client](#2-client)  
   2.1. [Handle connection pools](#21-handle-connection-pools)  
   2.2. [Close the client](#22-close-the-client)
3. [Commands and responses](#3-commands-and-responses)
4. [Key operations](#4-key-operations)
5. [List operations](#5-list-operations)

# 1. Overview

This section describes the usage of the Redis `Client` object for interfacing with a Redis server.

The `Client` supports various Redis commands for key-value operations, list operations, and more.

# 2. Client

The Redis `Client` represents a connection to the Redis server. It maintains a pool of connections throughout its
lifetime.

**Initialization of the Client:**

  ```ballerina
  # Initialize the Redis client. 
  #
  # + config - configuration for the connector
  # + return - `redis:Error` in case of failures or `nil` if successful. 
  public isolated function init(*ConnectionConfig config) returns Error? {
      check self.initClient(self, config);
  }
  ```

**Configurations available for initializing the Redis client:**

* Connection options:
  ```ballerina
  # The client endpoint configuration for Redis.
  #
  # + connection - connection configuration for the Redis database
  # + connectionPooling - whether connection pooling is enabled
  # + isClusterConnection - whether the connection is a cluster connection
  public type ConnectionConfig record {|
      ConnectionUri|ConnectionParams connection?;
      boolean connectionPooling = false;
      boolean isClusterConnection = false;
  |};
    
  # The connection parameters based configurations.
  #
  # + host - host address of the Redis database
  # + port - port of the Redis database
  # + username - field description
  # + password - The password for the Redis database
  # + options - other connection options of the connection configuration
  type ConnectionParams record {|
      string host = "localhost";
      int port = 6379;
      string username?;
      string password?;
      Options options = {};
  |};
    
  # The redis Connection URI based configurations. This can become useful when working with
  # managed Redis databases, where the cloud provider usually provides a connection URI.
  #
  # + uri - The connection URI for the Redis database
  type ConnectionUri record {|
      string uri;
  |};
    
  # Connection options for Redis client endpoint.
  #
  # + secureSocket - configurations related to SSL/TLS encryption
  # + clientName - name of the client
  # + database - database index
  # + connectionTimeout - connection timeout in seconds
  public type Options record {|
      SecureSocket secureSocket?;
      string clientName?;
      int database = -1;
      int connectionTimeout = 60;
  |};
    
  # Configurations for secure communication with the Redis server.
  #
  # + cert - configurations associated with `crypto:TrustStore` or single certificate file that the client trusts
  # + key - configurations associated with `crypto:KeyStore` or combination of certificate and private key of the client
  # + protocols - list of protocols used for the connection established to Redis Server, such as TLSv1.2, TLSv1.1, TLSv1.
  # + ciphers - list of ciphers to be used for SSL connections
  # + verifyPeer - whether peer verification is enabled
  # + startTls - whether StartTLS is enabled
  public type SecureSocket record {|
      crypto:TrustStore|string cert;
      crypto:KeyStore|CertKey key?;
      string[] protocols?;
      string[] ciphers?;
      boolean verifyPeer?;
      boolean startTls?;
  |};
    
  # Represents a combination of certificate, private key, and private key password if encrypted.
  #
  # + certFile - file containing the certificate
  # + keyFile - file containing the private key in PKCS8 format
  # + keyPassword - Password of the private key if it is encrypted
  @display {label: "Certificate Key Configurations"}
  public type CertKey record {|
      string certFile;
      string keyFile;
      string keyPassword?;
  |};
  ``` 

## 2.1. Handle connection pools

Connection pooling is supported using the [Apache Commons Pool](https://commons.apache.org/proper/commons-pool/)
library.

The `ConnectionConfig` record has a `connectionPooling` field that can be set to `true` to enable connection pooling.

## 2.2. Close the client

The `close()` operation shuts down the Redis client and closes the associated connection pool.

```ballerina
# Closes the Redis client and shuts down the connection pool.
#
# + return - Possible error when closing the client
public isolated function close() returns Error?;
```

# 3. Commands and responses

The Redis library supports various commands and responses specified by Redis.

For more details on supported commands and responses, refer to
the [Redis Commands Reference](https://redis.io/commands).

## 3.1 Hash Commands

Hash commands allow manipulation of hash data structures in Redis.

Ballerina Redis connector supports the following hash commands:

- `hDel`: Delete one or more hash fields.
- `hExists`: Determine if a hash field exists.
- `hGet`: Get the value of a hash field.
- `hGetAll`: Get all values of a hash.
- `hIncrBy`: Increment the integer value of a hash field by the given number.
- `hIncrByFloat`: Increment the float value of a hash field by the given number.
- `hKeys`: Get all fields in a hash.
- `hLen`: Get the number of fields in a hash.
- `hMGet`: Get the values of all the given hash fields.
- `hMSet`: Set multiple hash fields to multiple values.
- `hSet`: Set the string value of a hash field.
- `hSetNx`: Set the string value of a hash field, only if the field does not exist.
- `hStrLen`: Get the string length of the field value in a hash.
- `hVals`: Get all the values in a hash.

## 3.2 Key Commands

Key operations involve setting, retrieving, and deleting keys in the Redis database.

Ballerina Redis connector supports the following key operations:

- `del`: Delete one or more keys.
- `exists`: Determine how many keys exist.
- `expire`: Set a key's time to live in seconds.
- `keys`: Find all keys matching the given pattern.
- `move`: Move a key to another database.
- `persist`: Remove the expiration from a key.
- `pExpire`: Set a key's time to live in milliseconds.
- `pTtl`: Get the time to live for a key in milliseconds.
- `randomKey`: Return a random key from the keyspace.
- `rename`: Rename a key.
- `renameNx`: Rename a key, only if the new key does not exist.
- `sort`: Sort the elements in a list, set, or sorted set.
- `ttl`: Get the time to live for a key.
- `RedisType`: Determine the type stored at key.

# 3.3 List Commands

List operations allow manipulation of lists stored in Redis.

Ballerina Redis connector supports the following list commands:

- `lPushX`: Prepend one or multiple values to a list, only if the list exists.
- `bLPop`: Remove and get the first element in a list, or block until one is available.
- `bRPop`: Remove and get the last element in a list, or block until one is available.
- `lPush`: Prepend one or multiple values to a list.
- `lPop`: Remove and get the first element in a list.
- `lIndex`: Get an element from a list by its index.
- `lInsert`: Insert an element before or after another element in a list.
- `lLen`: Get the length of a list.
- `lRange`: Get a range of elements from a list.
- `lRem`: Remove elements from a list.
- `lSet`: Set the value of an element in a list by its index.
- `lTrim`: Trim a list to the specified range.
- `rPop`: Remove and get the last element in a list.
- `rPopLPush`: Remove the last element in a list, append it to another list and return it.
- `rPush`: Append one or multiple values to a list.
- `rPushX`: Append one or multiple values to a list, only if the list exists.

## 3.4 Set Commands

Set Operations allow manipulation of sets in Redis.

Ballerina Redis connector supports the following set commands:

- `sAdd`: Add one or more members to a set.
- `sCard`: Get the number of members in a set.
- `sDiff`: Return the set resulting from the difference between the first set and all the successive sets.
- `sDiffStore`: Obtain the set resulting from the difference between the first set and all the successive sets and store
  it at the provided destination.
- `sInter`: Return the intersection of the provided sets.
- `sInterStore`: Obtain the intersection of the provided sets and store it at the provided destination.
- `sIsMember`: Determine if a given value is a member of a set.
- `sMembers`: Get all the members in a set.
- `sMove`: Move a member from one set to another.
- `sPop`: Remove and return a random member from a set.
- `sRandMember`: Get one or multiple random members from a set.
- `sRem`: Remove one or more members from a set.
- `sUnion`: Return the union of multiple sets.
- `sUnionStore`: Return the union of multiple sets and store it at the provided destination.

## 3.5 Sorted Set Commands

Sorted Set operations allow manipulation of sorted sets in Redis.

Ballerina Redis connector supports the following sorted set commands:

- `zAdd`: Add one or more members to a sorted set or update its score if it already exists.
- `zCard`: Get the number of members in a sorted set.
- `zCount`: Count the members in a sorted set with scores within the given range.
- `zIncrBy`: Increment the score of a member in a sorted set.
- `zInterStore`: Intersect multiple sorted sets and store the resulting sorted set in a new key.
- `zLexCount`: Count the members in a sorted set within the given lexicographical range.
- `zRange`: Return a range of members in a sorted set by index.
- `zRangeByLex`: Return a range of members in a sorted set by lexicographical range from lowest to highest.
- `zRevRangeByLex`: Return a range of members in a sorted set by lexicographical range ordered from highest to lowest.
- `zRangeByScore`: Return a range of members in a sorted set by score from lowest to highest.
- `zRank`: Determine the index of a member in a sorted set.
- `zRem`: Remove one or more members from a sorted set.
- `zRemRangeByLex`: Remove all members in a sorted set between the given lexicographical range.
- `zRemRangeByRank`: Remove all members in a sorted set within the given indices.
- `zRemRangeByScore`: Remove all members in a sorted set within the given scores.
- `zRevRange`: Return a range of members in a sorted set by index, ordered highest to lowest.
- `zRevRangeByScore`: Return a range of members in a sorted set by score from highest to lowest.
- `zRevRank`: Determine the index of a member in a sorted set, in descending order.
- `zScore`: Determine the score of a member in a sorted set.
- `zUnionStore`: Union multiple sorted sets and store the resulting sorted set in a new key.

## 3.6 String Commands

String operations allow manipulation of string values in Redis.

Ballerina Redis connector supports the following string commands:

- `append`: Appends a value to a key.
- `bitCount`: Counts set bits in a string.
- `bitOpAnd`: Performs a bitwise AND operation between strings.
- `bitOpOr`: Performs a bitwise OR operation between strings.
- `bitOpNot`: Performs a bitwise NOT operation on a string.
- `bitOpXor`: Performs a bitwise XOR operation between strings.
- `decr`: Decrements the integer value of a key by one.
- `decrBy`: Decrements the integer value of a key by a given number.
- `get`: Gets a string value from Redis.
- `getBit`: Returns the bit value at a specified offset in the string value stored at a key.
- `getRange`: Gets a substring of the string stored at a key.
- `getSet`: Sets the string value of a key and returns its old value.
- `incr`: Increments the integer value of a key by one.
- `incrBy`: Increments the integer value of a key by a given amount.
- `incrByFloat`: Increments the float value of a key by a given amount.
- `mGet`: Gets the values of multiple keys.
- `mSet`: Sets multiple keys to multiple values.
- `mSetNx`: Sets multiple keys to multiple values, only if none of the keys exist.
- `pSetEx`: Sets the value and expiration in milliseconds of a key.
- `set`: Sets a string value for a given key.
- `setBit`: Sets or clears the bit at an offset in the string value stored at a key.
- `setEx`: Sets the value and expiration of a key in seconds.
- `setNx`: Sets the value of a key, only if the key does not exist.
- `setRange`: Overwrites part of a string at a key starting at the specified offset.
- `strLen`: Gets the length of the value stored in a key.

## 3.7 Cluster Commands

The Redis library supports cluster specific commands for Redis cluster operations.

Ballerina Redis connector supports the following cluster commands:

- `clusterInfo`: Retrieve information and statistics about the Redis Cluster observed by the current node.

----
