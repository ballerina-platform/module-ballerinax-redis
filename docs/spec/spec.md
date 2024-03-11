# Specification: Ballerina Redis Library

_Authors_: @NipunaRanasinghe \
_Reviewers_: @AyeshLK @AzeemMuzammil \
_Created_: 2024/02/19 \
_Updated_: 2024/02/28 \
_Edition_: Swan Lake

## Introduction

This is the specification for the Redis connector library of the [Ballerina language](https://ballerina.io),
which provides a comprehensive set of operations to interact with Redis servers using Ballerina.

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
    * 2.1. [Configurations](#21-configurations)  
      * 2.1.1. [Connection configurations](#211-connection-configurations)  
      * 2.1.2. [Connection URI and Connection parameters](#212-connection-uri-and-connection-parameters)  
      * 2.1.3. [Secure Socket configurations](#213-secure-socket-configurations)  
    * 2.2. [Initialization](#22-initialization)  
    * 2.3. [Secure communication](#23-secure-communication)  
    * 2.4. [Cluster connections](#24-cluster-connections)  
    * 2.5. [Connection pooling](#25-connection-pooling)  
    * 2.6. [Closing the connection](#26-closing-the-connection)
    * 2.7. [Supported Operations](#27-supported-operations)  
      * 2.7.1. [Hash Operations](#271-hash-operations)  
      * 2.7.2. [Key Operations](#272-key-operations)  
      * 2.7.3. [List Operations](#273-list-operations)  
      * 2.7.4. [Set Operations](#274-set-operations)  
      * 2.7.5. [Sorted Set Operations](#275-sorted-set-operations)  
      * 2.7.6. [String Operations](#276-string-operations)  
      * 2.7.7. [Cluster Operations](#277-cluster-operations)

# 1. Overview

Redis (REmote DIctionary Server) is an open-source, in-memory data structure store that can be used as a database,
cache, and message broker.
It supports various data structures such as strings, hashes, lists, sets, and more. Redis has its own protocol for
communication between clients and the server.

The Ballerina Redis client library supports a multitude of Redis operations related to different data structures, such
as key-value operations, list operations, and more. The client also supports connecting to Redis clusters, which allows
working with multiple Redis nodes using a single client. It also comes with the support for various connection features
such as connection pooling, which allows reusing connections to the Redis server, and SSL/TLS encryption for secure
communication with the Redis server.

# 2. Client

The Redis `Client` represents a connection to the Redis server. It maintains a pool of connections throughout its
lifetime.

## 2.1. Configurations

### 2.1.1. Connection configurations

The `ConnectionConfig` record represents the client endpoint configuration for Redis. It contains the following fields:
  
  ```ballerina
  # The client endpoint configuration for Redis.
  #
  # + connection - Connection configurations of the Redis server. This can be either a single URI or a set of parameters.
  #                If none is provided, the default Redis server configurations (localhost:6379) will be used.
  # + connectionPooling - Flag to indicate whether connection pooling is enabled
  # + isClusterConnection - Flag to indicate whether the connection is a cluster connection
  # + secureSocket - Configurations related to SSL/TLS encryption
  public type ConnectionConfig record {|
      ConnectionUri|ConnectionParams connection?;
      boolean connectionPooling = false;
      boolean isClusterConnection = false;
      SecureSocket secureSocket?;
  |};
  ```

### 2.1.2. Connection URI and Connection parameters

The connection parameters can be provided either as a single Redis URI or as individual parameters. The `ConnectionUri`
and `ConnectionParams` records are defined as follows:

  1. The `ConnectionUri` record represents the connection URI string for the Redis database.
  ```ballerina
  # The redis Connection URI based configurations. This can become useful when working with 
  # managed Redis databases, where the cloud provider usually provides a connection URI.
  #
  # + uri - The connection URI for the Redis database
  public type ConnectionUri string;
  ```

  2. The `ConnectionParams` record represents the configurations related to Redis client connection.
  ```ballerina
  # The connection parameters based configurations.
  #
  # + host - Host address of the Redis database
  # + port - Port of the Redis database
  # + username - The username for the Redis database
  # + password - The password for the Redis database
  # + options - Other connection options of the connection configuration
  public type ConnectionParams record {|
      string host = "localhost";
      int port = 6379;
      string username?;
      string password?;
      Options options = {};
  |};
   
  # Connection options for Redis client endpoint.
  #
  # + clientName - Name of the client
  # + database - Database index which the client should interact with. Not applicable for cluster connections
  # + connectionTimeout - Connection timeout in seconds
  public type Options record {|
      string clientName?;
      int database = 0;
      int connectionTimeout = 60;
  |};
  ```

### 2.1.3. Secure Socket configurations

The `SecureSocket` record represents the configurations related to secure communication with the Redis server. It consists
of the below fields and sub records.

  ```ballerina
  # Configurations for secure communication with the Redis server.
  #
  # + cert - Configurations associated with `crypto:TrustStore` or single certificate file that the client trusts
  # + key - Configurations associated with `crypto:KeyStore` or combination of certificate and private key of the client
  # + protocols - List of protocols used for the connection established to Redis Server, such as TLSv1.2, TLSv1.1, TLSv1.
  # + ciphers - List of ciphers to be used for SSL connections
  # + verifyMode - The SSL/TLS verification mode. This can be either NONE, CA, or FULL.
  # + startTls - Whether StartTLS is enabled
  public type SecureSocket record {|
      crypto:TrustStore|string cert?;
      crypto:KeyStore|CertKey key?;
      string[] protocols?;
      string[] ciphers?;
      SslVerifyMode verifyMode = FULL;
      boolean startTls = false;
  |};

  # Represents a combination of certificate, private key, and private key password if encrypted.
  #
  # + certFile - File containing the certificate
  # + keyFile - File containing the private key in PKCS8 format
  # + keyPassword - Password of the private key if it is encrypted
  public type CertKey record {|
      string certFile;
      string keyFile;
      string keyPassword?;
  |};
   
  # Represents the SSL/TLS verification mode.
  #
  # + NONE - No verification
  # + CA - Verify the server's certificate against the provided CA certificates
  # + FULL - Verify the server's certificate against the provided CA certificates and also verify the server's hostname
  public enum SslVerifyMode {
      NONE,
      CA,
      FULL
  }
  ```

## 2.2. Initialization

Redis client can be initialized using the `init` function. Client initialization requires the `ConnectionConfig` record 
which is mentioned in the previous section.

  ```ballerina
  # Initialize the Redis client. 
  #
  # + config - configuration for the connector
  # + return - `redis:Error` in case of failures or `nil` if successful. 
  public isolated function init(*ConnectionConfig config) returns redis:Error?;
  ```

## 2.3. Secure communication

The `ConnectionConfig` record contains a `secureSocket` field, which can be used to configure secure communication with
the Redis server. The `secureSocket` field is of type `SecureSocket`, which contains the following fields:

- `cert`: Configurations associated with `crypto:TrustStore` or a single certificate file that the client trusts.
- `key`: Configurations associated with `crypto:KeyStore` or a combination of certificate and private key of the client.
- `protocols`: List of protocols used for the connection established to the Redis server, such
  as `TLSv1.2`, `TLSv1.1`, `TLSv1`.
- `ciphers`: List of ciphers to be used for SSL connections.
- `verifyMode`: The SSL/TLS verification mode. This can be either `NONE`, `CA`, or `FULL`.
- `startTls`: Whether `StartTLS` is enabled.

## 2.4. Cluster connections

The `ConnectionConfig` has an `isClusterConnection` field that can be set to `true` to enable cluster connections.
When this field is set to `true`, the client can connect to any given node in the Redis cluster and resolve the rest of
the cluster topology.

## 2.5. Connection pooling

The existing connection pooling implementation for Redis is based on
the [Apache Commons Pool](https://commons.apache.org/proper/commons-pool/)
library.

The `ConnectionConfig` has a `connectionPooling` field that can be set to `true` to enable connection pooling.
Currently, the connection pooling has limited configuration options, and the default pool size and other configurations
of the underlying connection pool are used by the client.

## 2.6. Closing the connection

The `close()` operation shuts down the Redis client and closes the associated connection pool.

```ballerina
# Closes the Redis client and shuts down the connection pool.
#
# + return - Possible error when closing the client
public isolated function close() returns redis:Error?;
```

## 2.7. Supported operations

The Ballerina Redis connector supports various operations for interacting with Redis data structures.

For complete details on all available operations in Redis, refer to
the [Redis Command Reference](https://redis.io/commands).

### 2.7.1. Hash Operations

Hash operations allow manipulation of hash data structures in Redis.

Ballerina Redis connector supports the following hash operations:

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

### 2.7.2. Key Operations

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

### 2.7.3. List Operations

List operations allow manipulation of lists stored in Redis.

Ballerina Redis connector supports the following list operations:

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

### 2.7.4. Set Operations

Set operations allow manipulation of sets in Redis.

Ballerina Redis connector supports the following set operations:

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

### 2.7.5. Sorted Set Operations

Sorted Set operations allow manipulation of sorted sets in Redis.

Ballerina Redis connector supports the following sorted set operations:

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

### 2.7.6. String Operations

String operations allow manipulation of string values in Redis.

Ballerina Redis connector supports the following string operations:

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

### 2.7.7. Cluster Operations

The Redis library supports cluster specific operations for Redis cluster operations.

Ballerina Redis connector supports the following cluster operations:

- `clusterInfo`: Retrieve information and statistics about the Redis Cluster observed by the current node.
