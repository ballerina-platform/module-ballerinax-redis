// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/jballerina.java;

# Ballerina Redis connector provides the capability to access Redis cache.
# This connector lets you to perform operations to access and manipulate key-value data stored in a Redis database. 
# 
@display {label: "Redis Client", iconPath: "icon.png"}
public isolated client class Client {
    private ConnectionConfig clientEndpointConfig = {};

    # Initializes the connector. During initialization you have to pass needed configurations such as host name, port number and password.
    #
    # + config - Configuration for the connector
    # + return - `http:Error` in case of failure to initialize or `null` if successfully initialized
    public isolated function init(ConnectionConfig config) returns error? {
        Error? initClientResult = self.initClient(self, config);
        if initClientResult is Error {
            return initClientResult;
        }
    }

    # Initialize redis client.
    #
    # + redisClient - client
    # + clientEndpointConfig - Client end point configuration
    # + return - `error` if error occurs
    isolated function initClient(Client redisClient, ConnectionConfig clientEndpointConfig) returns Error? = @java:Method {
        'class: "org.ballerinalang.redis.utils.RedisUtils"
    } external;

    // String operations
    
    # Append a value to a key.
    #
    # + key - Key referring to a value
    # + value - String value to be appended
    # + return - Length of the string after the operation
    @display {label: "Enrich Value"}
    isolated remote function append(@display {label: "Key"} string key, @display {label: "Value To Append"} string value) 
                           returns int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Count set bits in a string.
    #
    # + key - Key referring to a value
    # + return - Number of bits of the value
    @display {label: "Get Bits of String"}
    isolated remote function bitCount(@display {label: "Key"} string key) 
                             returns @display {label: "Bits of String"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Perform bitwise AND between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform AND between
    # + return - Size of the string stored in the destination key, that is equal to the size of the longest input
    #            string
    @display {label: "Perform Bitwise AND"}
    isolated remote function bitOpAnd(@display {label: "Result key"} string destination, 
                             @display {label: "Key Array To Perform AND"} string[] keys)
                             returns @display {label: "Size of string"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Perform bitwise OR between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform OR between
    # + return - Size of the string stored in the destination key, that is equal to the size of the longest input
    #            string or `error` if an error occurs
    @display {label: "Perform Bitwise OR"}
    isolated remote function bitOpOr(@display {label: "Result key"} string destination, 
                            @display {label: "Key Array To Perform OR"} string[] keys)
                            returns @display {label: "Size of string"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Perform bitwise NOT on a string.
    #
    # + destination - Result key of the operation
    # + key - Input key to perform NOT
    # + return - Size of the string stored in the destination key or `error` if an error occurs
    @display {label: "Perform Bitwise NOT"}
    isolated remote function bitOpNot(@display {label: "Result key"} string destination, 
                             @display {label: "Key To Perform NOT"} string key)
                             returns @display {label: "Size of String"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Perform bitwise XOR between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform XOR between
    # + return - Size of the string stored in the destination key, that is equal to the size of the longest input
    #            string or `error` if an error occurs
    @display {label: "Perform Bitwise XOR"}
    isolated remote function bitOpXor(@display {label: "Result key"} string destination, 
                             @display {label: "Key Array To Perform XOR"} string[] keys)
                             returns @display {label: "Size of String"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Decrement integer value of a key by one.
    #
    # + key - Key referring to a value
    # + return - Value of key after the decrement
    @display {label: "Decrement(By One)"}
    isolated remote function decr(@display {label: "Key"} string key) returns @display {label: "Value"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Decrement integer value of a key by the given number.
    #
    # + key - Key referring to a value
    # + value - Value to be decremented
    # + return - Value of key after decrement or `error` if an error occurs
    @display {label: "Decrement (By Number)"}
    isolated remote function decrBy(@display {label: "Key"} string key, @display {label: "Value To Decrement"} int value) 
                           returns @display {label: "Value"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Returns bit value at offset in the string value stored at key.
    #
    # + key - Key referring to a value
    # + offset - Offset in string value
    # + return - Bit value stored at offset or `error` if an error occurs
    @display {label: "Get Bit At Offset"}
    isolated remote function getBit(@display {label: "Key"} string key, @display {label: "Offset"} int offset) 
                           returns @display {label: "Bit Value"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Get substring of string stored at a key.
    #
    # + key - Key referring to a value
    # + startPos - Starting point of substring
    # + end - End point of substring
    # + return - Substring or `error` if an error occurs
    @display {label: "Get Substring"}
    isolated remote function getRange(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                             @display {label: "End Position"} int end) 
                             returns @display {label: "Substring"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set string value of key and return its existing value.
    #
    # + key - Key referring to a value
    # + value - Value to be set
    # + return - Existing value stored at key, or nil when key does not exist or `error` if an error occurs
    @display {label: "Get And Set Value"}
    isolated remote function getSet(@display {label: "Key"} string key, @display {label: "New Value"} string value) 
                           returns @display {label: "Old Value"} string?|error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Get value of key.
    #
    # + key - Key referring to a value
    # + return - Value of key, or nil when key does not exist or `error` if an error occurs
    @display {label: "Get Value"}
    isolated remote function get(@display {label: "Key"} string key) returns @display {label: "Value"} string?|error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Increment integer value of a key by one.
    #
    # + key - Key referring to a value
    # + return - Value of key after increment
    @display {label: "Increment (By One)"}
    isolated remote function incr(@display {label: "Key"} string key) returns @display {label: "Result Value"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Increment integer value of key by the given amount.
    #
    # + key - Key referring to a value
    # + value - Amount to increment
    # + return - Value of key after increment
    @display {label: "Increment (By Number)"}
    isolated remote function incrBy(@display {label: "Key"} string key, @display {label: "Increment Value"} int value) 
                           returns @display {label: "Result Value"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Increment integer value of key by the given float.
    #
    # + key - Key referring to a value
    # + value - Amount to increment
    # + return - Value of key after increment
    @display {label: "Increment (By Float)"}
    isolated remote function incrByFloat(@display {label: "Key"} string key, 
                                @display {label: "Increment Value"} float value) 
                                returns @display {label: "Float Result Value"} float | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Get values of all given keys.
    #
    # + keys - Keys of which values need to be retrieved
    # + return - Array of values at specified keys
    @display {label: "Get Values"}
    isolated remote function mGet(@display {label: "Keys"} string[] keys) 
                         returns @display {label: "Values"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set multiple keys to multiple values.
    #
    # + keyValueMap - Map of key-value pairs to be set
    # + return - String with value `OK` if the operation was successful
    @display {label: "Set Values"}
    isolated remote function mSet(@display {label: "Key-Value Pair Map"} map<any> keyValueMap) 
                         returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set multiple keys to multiple values, only if none of the keys exist.
    #
    # + keyValueMap - Map of key-value pairs to be set
    # + return - True if the operation was successful, false if it failed
    @display {label: "Set Values If Absent"}
    isolated remote function mSetNx(@display{label: "Map of Key Value Pairs"} map<any> keyValueMap)
                           returns @display{label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set value and expiration in milliseconds of a key.
    #
    # + key - Key referring to a value
    # + value - Value to be set
    # + expirationTime - Expiration time in milli seconds
    # + return - `OK` if successful or `error` if an error occurs
    @display {label: "Set Expirable Value (ms)"}
    isolated remote function pSetEx(@display {label: "Key"} string key, @display {label: "Value"} string value,
                           @display {label: "TTL (ms)"} int expirationTime)
                           returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set the value of a key.
    #
    # + key - Key referring to a value
    # + value - Values
    # + return - `OK` if successful
    @display {label: "Set Value"}
    isolated remote function set(@display {label: "Key"} string key, @display {label: "Value"} string value) 
                        returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Sets or clears the bit at offset in the string value stored at key.
    #
    # + key - Key referring to a value
    # + value - Value to be set
    # + offset - Offset at which the value should be set
    # + return - Original bit value stored at offset or `error` if an error occurs
    @display {label: "Set Bit From Offset"} 
    isolated remote function setBit(@display {label: "Key"} string key, @display {label: "Value"} int value, 
                           @display {label: "Offset"} int offset) 
                           returns @display {label: "Old Bit At Offset"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set the value and expiration of a key.
    #
    # + key - Key referring to a value
    # + value - Value to be set
    # + expirationPeriodSeconds - Expiration time to be set, in seconds
    # + return - On success `OK` or `error` if an error occurs
    @display {label: "Set Expirable Value (s)"}
    isolated remote function setEx(@display {label: "Key"} string key, @display {label: "Value"} string value, 
                          @display {label: "TTL (s)"} int expirationPeriodSeconds)
                          returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Set value of a key, only if key does not exist.
    #
    # + key - Key referring to a value
    # + value - Value to be set
    # + return - `True` if exist `False` if not or `error` if an error occurs
    @display {label: "Set Value If Absent"} 
    isolated remote function setNx(@display {label: "Key"} string key, @display {label: "Value"} string value) 
                          returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Overwrite part of string at key starting at the specified offset.
    #
    # + key - Key referring to a value
    # + offset - Offset at which the value should be set
    # + value - Value to be set
    # + return - Length of the string after it was modified or `error` if an error occurs
    @display {label: "Overwrite Value From Offset"} 
    isolated remote function setRange(@display {label: "Key"} string key, 
                             @display {label: "Start Position"} int offset,
                             @display {label: "Value"} string value) 
                             returns @display {label: "Result String Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    # Get length of value stored in a key.
    #
    # + key - Key referring to a value
    # + return - Length of string at key, or 0 when key does not exist or `error` if an error occurs
    @display {label: "Get String Length"} 
    isolated remote function strln(@display {label: "Key"} string key) 
                          returns @display {label: "String Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.StringCommands"
    } external;

    // list operations

    # Prepend one or multiple values to list.
    #
    # + key - Key referring to a value
    # + values - Values to be prepended
    # + return - Length of list after the push operation(s) or `error` if an error occurs
    @display {label: "Push Value To List"}
    isolated remote function lPush(@display {label: "Key"} string key, @display {label: "Values"} string[] values) 
                          returns @display {label: "List Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Remove and get the first element in a list.
    #
    # + key - Key referring to a value
    # + return - Value of the first element, or nil when key does not exist or `error` if an error occurs
    @display {label: "Pop Value From List"}
    isolated remote function lPop(@display {label: "Key"} string key) 
                         returns @display {label: "First Element Popped"} string?|error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Prepend one or multiple values to a list, only if the list exists.
    #
    # + key - Key referring to a value
    # + values - Values to be prepended
    # + return - Length of the list after the push operation(s)
    @display {label: "Push To Available List"}
    isolated remote function lPushX(@display {label: "Key"} string key, @display {label: "Values"} string[] values) 
                           returns @display {label: "List Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Remove and get the first element in a list, or block until one is available.
    #
    # + timeOut - Timeout in seconds
    # + keys - Keys referring to values
    # + return - `Nil` when no element could be popped and the timeout expired. A map containing one item, with the
    #         key being  the name of the key where an element was popped and the second element  being the value of the
    #         popped element, or `error` if an error occurs
    @display {label: "Pop List First Element And Block If Absent"}
    isolated remote function bLPop(@display {label: "Timeout (s)"} int timeOut, @display {label: "Keys"} string[] keys)
                          returns @display {label: "Key-Value Pair"} map<any> | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Remove and get the last element in a list, or block until one is available.
    #
    # + timeOut - Timeout in seconds
    # + keys - Keys referring to values
    # + return - `nil` when no element could be popped and the timeout expired. A map containing one item, with the
    #         key being  the name of the key where an element was popped and the second element being the value of the
    #         popped element, or `error` if an error occurs
    @display {label: "Pop List Last Element And Block If Absent"}
    isolated remote function bRPop(@display {label: "Timeout (s)"} int timeOut, @display {label: "Key Referring To a Values"} string[] keys)
                          returns @display {label: "Key-Value Pair"} map<any> | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Get an element from list by its index.
    #
    # + key - Key referring to a value
    # + index - Index from which the element should be retrieved
    # + return - Value at the given index
    @display {label: "Get List Element By Index"}
    isolated remote function lIndex(@display {label: "Key"} string key, @display {label: "Index"} int index) 
                           returns @display {label: "Value"} string?|error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Insert an element before or after another element in a list.
    #
    # + key - Key referring to a value
    # + before - Boolean value representing Whether element should be inserted before or after the pivot
    # + pivot - Pivot position
    # + value - Value to insert
    # + return - Length of the list after the insert operation, or -1 when the value pivot not found, or `error` if
    #           an error occurs
    @display {label: "Insert To List In Specific Position"}
    isolated remote function lInsert(@display {label: "Key"} string key, 
                            @display {label: "Insert Before or Not"} boolean before,
                            @display {label: "Place To Insert"} string pivot, @display {label: "Value"} string value) 
                            returns @display {label: "Result List Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Get length of a list.
    #
    # + key - Key referring to a value
    # + return - Length of list at key or `error` if an error occurs
    @display {label: "Get List Length"}
    isolated remote function lLen(@display {label: "Key"} string key) returns @display {label: "List Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Get a range of elements from a list.
    #
    # + key - Key referring to a value
    # + startPos - Begining index of the range
    # + stopPos - Last index of the range
    # + return - Array of elements in the specified range or `error` if an error occurs
    @display {label: "Get Range of List Elements"}
    isolated remote function lRange(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                           @display {label: "End Position"} int stopPos) 
                           returns @display {label: "Elements In Range"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Remove elements from list.
    #
    # + key - Key referring to a value
    # + count - Number of elements to be removed
    # + value - Value which the elements to be removed should be equal to
    # + return - Number of elements removed or `error` if an error occurs
    @display {label: "Remove List Elements"}
    isolated remote function lRem(@display {label: "Key"} string key, @display {label: "Member Count"} int count, 
                         @display {label: "Value"} string value) 
                         returns @display {label: "Removed Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Set the value of an element in a list by its index.
    #
    # + key - Key of the list
    # + index - Index of the element of which the value needs to be set
    # + value - Value to be set
    # + return - String with the value `OK` if the operation was successful or `error` if an error occurs
    @display {label: "Set List Element At Index"}
    isolated remote function lSet(@display {label: "Key"} string key, @display {label: "Index"} int index, 
                         @display {label: "Value"} string value) 
                         returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Trim list to the specified range.
    #
    # + key - Key of the list
    # + startPos - Starting index of the range
    # + stopPos - End index of the range
    # + return - String with the value `OK` if the operation was successful
    @display {label: "Trim List To Range"}
    isolated remote function lTrim(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                          @display {label: "End Position"} int stopPos) 
                          returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Remove and get the last element in a list.
    #
    # + key - Key of the list
    # + return - Value of the last element, or `nil` when key does not exist or `error` if an error occurs
    @display {label: "Pop List Last Element"}
    isolated remote function rPop(@display {label: "Key"} string key) 
                         returns @display {label: "Popped Value"} string?|error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Remove the last element in a list, append it to another list and return it.
    #
    # + src - Source key
    # + destination - Destination key
    # + return - Element being popped and pushed or `error` if an error occurs
    @display {label: "Move List Last Element To Another"}
    isolated remote function rPopLPush(@display {label: "Current Key"} string src, 
                              @display {label: "Destination Key"} string destination) 
                              returns @display {label: "Value"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Append one or multiple values to a list.
    #
    # + key - Key of the list
    # + values - Array of values to be appended
    # + return - Length of the list after the push operation or `error` if an error occurs
    @display {label: "Enrich Values To List"}
    isolated remote function rPush(@display {label: "Key"} string key, @display {label: "Values"} string[] values) 
                          returns @display {label: "List Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    # Append one or multiple values to a list, only if the list exists.
    #
    # + key - Key of the list
    # + values - Array of values to be appended
    # + return - Length of the list after the push operation or `error` if an error occurs
    @display {label: "Enrich Values To List If Exists"}
    isolated remote function rPushX(@display {label: "Key"} string key, @display {label: "Values"} string[] values) 
                           returns @display {label: "List Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.ListCommands"
    } external;

    // Set Commands

    # Add one or more members to a set.
    #
    # + key - Key of the set
    # + values - Array of values to be added
    # + return - Number of elements that were added to the set, not including all the elements which were
    #            already present in the set, or `error` if an error occurs
    @display {label: "Add Members To Set"}
    isolated remote function sAdd(@display {label: "Key"} string key, @display {label: "Values"} string[] values) 
                         returns @display {label: "Number of Elements Added"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Get the number of members in a set
    #
    # + key - Key of the set
    # + return - Cardinality (number of elements) of the set or `error` if an error occurs
    @display {label: "Get Member Count In Set"}
    isolated remote function sCard(@display {label: "Key"} string key) 
                          returns @display {label: "Elements In Set"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Return set resulting from the difference between the first set and all the successive sets
    #
    # + keys - The keys of the sets
    # + return - An array of members of the resulting set or `error` if an error occurs
    @display {label: "Get Difference of Set"}
    isolated remote function sDiff(@display {label: "Keys"} string[] keys) 
                          returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Obtain the set resulting from the difference between the first set and all the successive.
    # sets and store at the provided destination.
    #
    # + destination - Destination key of the resulting set
    # + keys - Keys of the sets to find the difference of
    # + return - Number of members in the resulting set or `error` if an error occurs
    @display {label: "Set Difference of Set"}
    isolated remote function sDiffStore(@display {label: "Destination Key"} string destination, 
                               @display {label: "Keys"} string[] keys) 
                               returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Return the intersection of the provided sets.
    #
    # + keys - Keys of the sets to be intersected
    # + return - Array of members of the resulting set or `error` if an error occurs
    @display {label: "Get Intersections of Sets"}
    isolated remote function sInter(@display {label: "Keys"} string[] keys) 
                           returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Obtain the intersection of the provided sets and store at the provided destination.
    #
    # + destination - Destination key of the resulting set
    # + keys - Keys of the sets to be intersected
    # + return - Number of members of the resulting set or `error` if an error occurs
    @display {label: "Set Intersections of Sets"}
    isolated remote function sInterStore(@display {label: "Destination Key"} string destination, 
                                @display {label: "Keys"} string[] keys) 
                                returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Determine if a given value is a member of a set.
    #
    # + key - Key of the set
    # + value - Value of a key
    # + return - boolean true/false depending on whether the value is a member of the set or not, or `error` if an error
    #            occurs
    @display {label: "Check Value In Set"}
    isolated remote function sIsMember(@display {label: "Key"} string key, @display {label: "Value"} string value) 
                              returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Get all members in a set.
    #
    # + key - Key of the set
    # + return - Array of all members in the set or `error` if an error occurs
    @display {label: "Get Members In Set"}
    isolated remote function sMembers(@display {label: "Key"} string key) 
                             returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Move a member from one set to another.
    #
    # + src - Source key
    # + destination - Destination key
    # + member - Member to be moved
    # + return - `True` if the element is moved. `false` if the element is not a member of source and no
    #             operation was performed or `error` if an error occurs
    @display {label: "Move Member Between Sets"}
    isolated remote function sMove(@display {label: "Source Key"} string src, 
                          @display {label: "Destination Key"} string destination, 
                          @display {label: "Member"} string member) 
                          returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;


    # Remove and return a random member from a set.
    #
    # + key - Source key
    # + count - Number of members to pop
    # + return - Array of removed elements or `nil` if key does not exist or `error` if an error occurs
    @display {label: "Pop Set Random Member"}
    isolated remote function sPop(@display {label: "Key"} string key, @display {label: "Member Count"} int count) 
                         returns @display {label: "Removed Elements"} string[]?|error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Get one or multiple random members from a set.
    #
    # + key - Key of the set
    # + count - Number of members to obtain
    # + return - Array of the randomly selected elements, or `nil` when key does not exist or `error` if an error occurs
    @display {label: "Get Random Members In Set"}
    isolated remote function sRandMember(@display {label: "Key"} string key, @display {label: "Member Count"} int count) 
                                returns @display {label: "Elements"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Remove one or more members from a set.
    #
    # + key - Key of the set
    # + members - Array of members to remove
    # + return - Number of members that were removed from the set, not including non existing members or `error` if
    #            an error occurs
    @display {label: "Remove Members In Set"}
    isolated remote function sRem(@display {label: "Key"} string key, @display {label: "Members"} string[] members) 
                         returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Return the union of multiple sets.
    #
    # + keys - Array of keys of sets
    # + return - Array of members of the resulting set or `error` if an error occurs
    @display {label: "Get Multiple Sets Union"}
    isolated remote function sUnion(@display {label: "Keys"} string[] keys) 
                           returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    # Return the union of multiple sets.
    #
    # + destination - Destination key of the resulting set
    # + keys - Array of keys of sets
    # + return - Number of members of the resulting set or `error` if an error occurs
    @display {label: "Set Multiple Sets Union"}
    isolated remote function sUnionStore(@display {label: "Destination Key"} string destination,
                                @display {label: "Keys"} string[] keys) 
                                returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SetCommands"
    } external;

    // Sorted set Commands

    # Add one or more members to a sorted set, or update its score if it already exist.
    #
    # + key - Key of the sorted set
    # + memberScoreMap - Map of members and corresponding scores
    # + return - Number of elements that were added to the sorted set, not including all the elements which were
    #            already present in the set for which the score was updated, or `error` if an error occurs
    @display {label: "Set Sorted Set Members"} 
    isolated remote function zAdd(@display {label: "Key"} string key, 
                         @display {label: "Member-Value Pairs"} map<any> memberScoreMap) 
                         returns @display {label: "Added Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Get the number of members in a sorted set.
    #
    # + key - Key of the sorted set
    # + return - Cardinality (number of elements) of the sorted set or `error` if an error occurs
    @display {label: "Get Sorted Set Member Count"}
    isolated remote function zCard(@display {label: "Key"} string key) 
                          returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Count the members in a sorted set with scores within the given range.
    #
    # + key - Key of the sorted set
    # + min - Minimum score of the range
    # + max - Maximum score of the range
    # + return - Number of elements in the specified score range or `error` if an error occurs
    @display {label: "Get Sorted Set Member Count (By Range)"}
    isolated remote function zCount(@display {label: "Key"} string key, @display {label: "Minimum Value"} float min, 
                           @display {label: "Maximum Value"} float max) 
                           returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Increment the score of a member in a sorted set.
    #
    # + key - Key of the sorted set
    # + amount - Amount to increment
    # + member - Member whose score to be incremented
    # + return - New score of the member or `error` if an error occurs
    @display {label: "Increment Sorted Set Member"}
    isolated remote function zIncrBy(@display {label: "Key"} string key, @display {label: "Value"} float amount, 
                            @display {label: "Member"} string member) 
                            returns @display {label: "Incremented Member Score"} float | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Intersect multiple sorted sets and store the resulting sorted set in a new key.
    #
    # + destination - Destination key of the resulting sorted set
    # + keys - Keys of the sorted sets to be intersected
    # + return - Number of elements in the resulting sorted set or `error` if an error occurs
    @display {label: "Get Member Count (Sorted Sets Intersection)"}
    isolated remote function zInterStore(@display {label: "Destination Key"} string destination, 
                                @display {label: "Keys"} string[] keys) 
                                returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Count the members in a sorted set within the given lexicographical range.
    #
    # + key - Key of the sorted set
    # + min - Minimum lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Number of elements in the specified lexicographical value range or `error` if an error occurs
    @display {label: "Get Member Count (Lexicographical Range)"}
    isolated remote function zLexCount(@display {label: "Key"} string key, @display {label: "Minimum Value"} string min, 
                              @display {label: "Maximum Value"} string max) 
                              returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return a range of members in a sorted set, by index.
    #
    # + key - Key of the sorted set
    # + min - Minimum index of the range
    # + max - Maximum index of the range
    # + return - Range of members in a sorted set, by index, or `error` if an error occurs
    @display {label: "Get Sorted Set Members (By Index Range)"}
    isolated remote function zRange(@display {label: "Key"} string key, @display {label: "Minimum Index"} int min, 
                           @display {label: "Maximum Index"} int max) 
                           returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return a range of members in a sorted set, by lexicographical range from lowest to highest.
    #
    # + key - Key of the sorted set
    # + min - Minimum lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Array of members in the specified lexicographical value range ordered from lowest to highest or `error`
    #            if an error occurs
    @display {label: "Get Sorted Set Members From Lowest (By Lexicographical Range)"}
    isolated remote function zRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum Value"} string min, 
                                @display {label: "Maximum Value"} string max) 
                                returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return a range of members in a sorted set, by lexicographical range ordered from highest to
    # lowest.
    #
    # + key - Key of the sorted set
    # + min - Lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Array of members in the specified lexicographical value range ordered from highest to lowest or `error`
    #            if an error occurs
    @display {label: "Get Sorted Set Members From Highest (By Lexicographical Range)"}
    isolated remote function zRevRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum Value"} string min, 
                                   @display {label: "Maximum Value"} string max) 
                                   returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return a range of members in a sorted set, by score from lowest to highest.
    #
    # + key - Key of sorted set
    # + min - Minimum score of range
    # + max - Maximum score of range
    # + return - Array of members in the specified score range ordered from lowest to highest or `error` if an error
    #            occurs
    @display {label: "Get Sorted Set Members (By Score Range)"}
    isolated remote function zRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum Value"} float min, 
                                  @display {label: "Maximum Value"} float max) 
                                  returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Determine index of a member in a sorted set.
    #
    # + key - Key of the sorted set
    # + member - Member of which the index needs to be obtained
    # + return - Index of the member or `error` if an error occurs
    @display {label: "Get Sorted Set Member Index"}
    isolated remote function zRank(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                          returns @display {label: "Index"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Remove one or more members from a sorted set
    #
    # + key - Key of the sorted set
    # + members - Members to be removed
    # + return - Number of members removed from the sorted set, not including non existing members or `error` if an
    #            error occurs
    @display {label: "Remove Sorted Set Members"}
    isolated remote function zRem(@display {label: "Key"} string key, @display {label: "Members"} string[] members) 
                         returns @display {label: "Removed Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;


    # Remove all members in a sorted set between the given lexicographical range.
    #
    # + key - Key of the sorted set
    # + min - Minimum lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Number of members removed from the sorted set or `error` if an error occurs
    @display {label: "Deleted Member Count Between Lexicographical Range"}
    isolated remote function zRemRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum Value"} string min, 
                                   @display {label: "Maximum Value"} string max) 
                                   returns @display {label: "Removed Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Remove all members in a sorted set within the given indices.
    #
    # + key - Key of the sorted set
    # + min - Minimum index of the range
    # + max - Maximum index of the range
    # + return - Number of members removed from the sorted set or `error` if an error occurs
    @display {label: "Deleted Member Count Between Indexes"}
    isolated remote function zRemRangeByRank(@display {label: "Key"} string key, @display {label: "Minimum Index"} int min, 
                                    @display {label: "Maximum Index"} int max) 
                                    returns @display {label: "Removed Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Remove all members in a sorted set within the given scores.
    #
    # + key - Key of the sorted set
    # + min - Minimum score of the range
    # + max - Maximum score of the range
    # + return - Number of members removed from the sorted set or `error` if an error occurs
    @display {label: "Deleted Member Count Between Scores"}
    isolated remote function zRemRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum Value"} float min, 
                                     @display {label: "Maximum Value"} float max) 
                                     returns @display {label: "Removed Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return a range of members in a sorted set, by index, ordered highest to lowest.
    #
    # + key - Key of the sorted set
    # + min - Minimum index of the range
    # + max - Maximum index of the range
    # + return - Number of elements in the specified index range or `error` if an error occurs
    @display {label: "Get Members (By Index Range)"}
    isolated remote function zRevRange(@display {label: "Key"} string key, @display {label: "Minimum Index"} int min, 
                              @display {label: "Maximum Index"} int max) 
                              returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return a range of members in a sorted set, by score from highest to lowest.
    #
    # + key - Key of the sorted set
    # + min - Minimum score of the range
    # + max - Maximum score of the range
    # + return - Array of members in the specified score range ordered from highest to lowest or `error` if an error
    #            occurs
    @display {label: "Get Members (By Score Range)"}
    isolated remote function zRevRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum Value"} float min, 
                                     @display {label: "Maximum Value"} float max) 
                                     returns @display {label: "Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Determine the index of a member in a sorted set
    #
    # + key - Key of the sorted set
    # + member - Member of which the index needs to be obtained
    # + return - Index of the member or `error` if an error occurs
    @display {label: "Get Sorted Set Member Index"}
    isolated remote function zRevRank(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                             returns @display {label: "Index"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Determine the score of a member in a sorted set
    #
    # + key - Key of the sorted set
    # + member - Member of which the score needs to be obtained
    # + return - Score of the member or `error` if an error occurs
    @display {label: "Get Sorted Set Member Score"}
    isolated remote function zScore(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                           returns @display {label: "Score"} float | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    # Return the union of multiple sorted sets
    #
    # + destination - Destination key of the resulting set
    # + keys - Array of keys of sorted sets
    # + return - Number of members of the resulting sorted set or `error` if an error occurs
    @display {label: "Get Sorted Sets Union"}
    isolated remote function zUnionStore(@display {label: "Destination Key"} string destination, 
                                @display {label: "Keys"} string[] keys) 
                                returns @display {label: "Member Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.SortedSetCommands"
    } external;

    // Hash Commands

    # Delete one or more hash fields.
    #
    # + key - Key of the hash
    # + fields - Array of fields to be deleted
    # + return - Number of fields that were removed from the hash, not including specified but non existing fields or
    #            `error` if an error occurs
    @display {label: "Delete Hash Fields"}
    isolated remote function hDel(@display {label: "Key"} string key, @display {label: "Fields"} string[] fields) 
                         returns @display {label: "Deleted Fields Count"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Determine if a hash field exists.
    #
    # + key - Key of the hash
    # + field - Array of fields to be deleted
    # + return - Boolean `true` if the hash contains the field. boolean false if the hash does not contain
    #            field or key does not exist or `error` if an error occurs
    @display {label: "Check Hash Field Availability"}
    isolated remote function hExists(@display {label: "Key"} string key, @display {label: "Field"} string 'field) 
                            returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get the value of a hash field.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + return - Value of the field or `error` if an error occurs
    @display {label: "Get Hash Field Value"} 
    isolated remote function hGet(@display {label: "Key"} string key, @display {label: "Field"} string 'field) 
                         returns @display {label: "Value of Field"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get the all values of a hash.
    #
    # + key - Key of the hash
    # + return - Map of field-value pairs or `error` if an error occurs
    @display {label: "Get Hash Values"} 
    isolated remote function hGetAll(@display {label: "Key"} string key) 
                            returns @display {label: "Field-value Pairs"} map<any> | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Increment the integer value of a hash field by the given number.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + amount - Amount to increment
    # + return - Value of the field or `error` if an error occurs
    @display {label: "Increment Hash Field (By Number)"}
    isolated remote function hIncrBy(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                            @display {label: "Increment Value"} int amount) 
                            returns @display {label: "Incremented Value"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Increment the float value of a hash field by the given number.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + amount - Amount to increment
    # + return - Value of the field or `error` if an error occurs
    @display {label: "Increment Hash Field (By Float)"}
    isolated remote function hIncrByFloat(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                                 @display {label: "Value To Increment"} float amount)
                                 returns @display {label: "Incremented Value"} float | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get all the fields in a hash.
    #
    # + key - Key of the hash
    # + return - Array of hash fields or `error` if an error occurs
    @display {label: "Get Hash Fields"} 
    isolated remote function hKeys(@display {label: "Key"} string key) 
                          returns @display {label: "Hash Fields"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get the number of fields in a hash.
    #
    # + key - Key of the hash
    # + return - Number of fields or `error` if an error occurs
    @display {label: "Get Hash Fields Count"} 
    isolated remote function hLen(@display {label: "Key"} string key) 
                         returns @display {label: "Number of hash fields"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get the values of all the given hash fields.
    #
    # + key - Key of the hash
    # + fields - Array of hash fields
    # + return - Map of field-value pairs or `error` if an error occurs
    @display {label: "Get Hash Fields Values"} 
    isolated remote function hMGet(@display {label: "Key"} string key, @display {label: "Fields"} string[] fields) 
                          returns @display {label: "Field-Value Pairs"} map<any> | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Set multiple hash fields to multiple values.
    #
    # + key - Key of the hash
    # + fieldValueMap - Map of field-value pairs
    # + return - String with the value `OK` if the operation was successful, or `error` if an error occurs
    @display {label: "Set Hash Fields"} 
    isolated remote function hMSet(@display {label: "Key"} string key, 
                          @display {label: "Field-Value Pairs"} map<any> fieldValueMap) 
                          returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Set the string value of a hash field.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + value - Value to be set to the field
    # + return - Boolean `true` if field is a new field in the hash and value was set. boolean false if
    #         field already exists in the hash and the value was updated, or `error` if an error occurs
    @display {label: "Set Hash Field"} 
    isolated remote function hSet(@display {label: "Key"} string key, @display {label: "Hash Field"} string 'field,
                         @display {label: "Value"} string value) returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Set the string value of a hash field, only if the field does not exist.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + value - Value to be set to the field
    # + return - Boolean `true` if field is a new field in the hash and value was set. boolean false if
    #            field already exists in the hash and no operation was performed, or `error` if an error occurs
    @display {label: "Set Hash Field If Absent"} 
    isolated remote function hSetNx(@display {label: "Key"} string key, @display {label: "Hash Field"} string 'field, 
                           @display {label: "Value"} string value) returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get the string length of the field value in a hash.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + return - Length of the field value, or 0 when field is not present in the hash or key does
    #            not exist at all, or `error` if an error occurs
    @display {label: "Get Value String Length"}
    isolated remote function hStrln(@display {label: "Key"} string key, @display {label: "Hash Field"} string 'field) 
                           returns @display {label: "String Length"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    # Get all the values in a hash.
    #
    # + key - Key of the hash
    # + return - Array of values in the hash, or an empty array when key does not exist or `error` if an error occurs
    @display {label: "Get Values In Hash"}
    isolated remote function hVals(@display {label: "Key"} string key) 
                          returns @display {label: "Values"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.HashCommands"
    } external;

    // Key Commands

    # Delete one or more keys.
    #
    # + keys - Key to be deleted
    # + return - Number of keys that were removed
    @display {label: "Delete Keys"}
    isolated remote function del(@display {label: "Keys"} string[] keys) 
                        returns @display {label: "Number of keys deleted"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Determine how many keys exist.
    #
    # + keys - Keys of which existence to be found out
    # + return - Number of existing keys or `error` if an error occurs
    @display {label: "Check Keys"}
    isolated remote function exists(@display {label: "Keys"} string[] keys) 
                           returns @display {label: "Number of Keys Exists"} int|error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Set a key's time to live in seconds.
    #
    # + key - Keys of which expiry time to be set
    # + seconds - Expiry in seconds
    # + return - Boolean `true` if the timeout was set. false if key does not exist or the timeout could not be set or
    # `error` if an error occurs
    @display {label: "Set TTL (s)"}
    isolated remote function expire(@display {label: "Key"} string key, @display {label: "TTL (s)"} int seconds)
                           returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Find all keys matching the given pattern.
    #
    # + pattern - Pattern to match
    # + return - Array of keys matching the given pattern or `error` if an error occurs
    @display {label: "Get Matching Keys"}
    isolated remote function keys(@display {label: "Pattern String"} string pattern) 
                         returns @display {label: "Keys"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Move a key to another database.
    #
    # + key - Key to be moved
    # + database - Database to which the key needs to be moved
    # + return - Boolean true if key was succesfully moved, boolean false otherwise or `error` if an error occurs
    @display {label: "Move Key"}
    isolated remote function move(@display {label: "Key"} string key, 
                         @display {label: "Destination Database"} int database) 
                         returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Remove the expiration from a key.
    #
    # + key - Key of which expiry time should be removed
    # + return - Boolean `true` if the timeout was removed. boolean `false` if key does not exist or does not have
    #            an associated timeout, or `error` if an error occurs
    @display {label: "Remove Key Timeout"}
    isolated remote function persist(@display {label: "Key"} string key) returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Set a key's time to live in milliseconds.
    #
    # + key - Key of which expiry time should be removed
    # + timeMilliSeconds - Expiry time in milli seconds
    # + return - Boolean `true` if the timeout was set. boolean false if key does not exist or the timeout could not
    #         be set, or `error` if an error occurs
    @display {label: "Set TTL (ms)"}
    isolated remote function pExpire(@display {label: "Key"} string key, 
                            @display {label: "TTL (ms)"} int timeMilliSeconds)
                            returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Get the time to live for a key in milliseconds.
    #
    # + key - Key of which time-to-live should be obtained
    # + return - TTL of the key, in milli seconds or `error` if an error occurs
    @display {label: "Get TTL (ms)"}
    isolated remote function pTtl(@display {label: "Key"} string key) 
                         returns @display {label: "TTL (ms)"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Return a random key from the keyspace.
    #
    # + return - Random key, or `nil` when the database is empty or `error` if an error occurs
    @display {label: "Get Random Key"}
    isolated remote function randomKey() returns @display {label: "Key"} string?|error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Rename a key.
    #
    # + key - Key to be renamed
    # + newName - New name of the key
    # + return - String with the value `OK` if the operation was successful or `error` if an error occurs
    @display {label: "Rename Key"}
    isolated remote function rename(@display {label: "Key"} string key, @display {label: "New Key Name"} string newName)
                           returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Rename a key, only if the new key does not exist.
    #
    # + key - Key to be renamed
    # + newName - New name of the key
    # + return - Boolean `true` if key was renamed to newkey. boolean `false` if newkey already exists. Or `error` if an
    #            error occurs
    @display {label: "Rename Key If Absent"}
    isolated remote function renameNx(@display {label: "Key"} string key, @display {label: "New Key Name"} string newName)
                             returns @display {label: "Result"} boolean | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Sort elements in a list, set or sorted set.
    #
    # + key - Key of the data typeure to be sorted
    # + return - Sorted array containing the members of the sorted data type or `error` if an error occurs
    @display {label: "Sort Elements"}
    isolated remote function sort(@display {label: "Key"} string key) 
                         returns @display {label: "Sorted Members"} string[] | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Get the time to live for a key.
    #
    # + key - Key of which the time to live needs to be obtained
    # + return - Time to live in seconds or a negative value/`error` in order to signal an error in evaluating ttl.
    #         Whether it is a negative value of an `error` would differ depending on whether the error occurs at DB
    #         level or the driver level
    @display {label: "Get Key TTL"}
    isolated remote function ttl(@display {label: "Key"} string key) 
                        returns @display {label: "TTL (s)"} int | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    # Determine the type stored at key.
    #
    # + key - Key of which the type needs to be obtained
    # + return - Type stored at key
    @display {label: "Get Key Type"}
    isolated remote function redisType(@display {label: "Key"} string key) 
                              returns @display {label: "Type Of Key"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.KeyCommands"
    } external;

    // Connection commands

    # Ping the server.
    # 
    # + return - String with the value `PONG` if the operation was successful
    @display {label: "Ping the server"}
    isolated remote function ping() returns @display {label: "Result"} string|error = @java:Method {
        'class: "org.ballerinalang.redis.ConnectionCommands"
    } external;

    # Authenticate to the server.
    #
    # + password - Password to authenticate
    # + return - String with the value `OK` if the operation was successful or `error` if an error occurs
    @display {label: "Authenticate Server"}
    isolated remote function auth(@display {label: "Password"} string password) 
                         returns @display {label: "Result"} string | error = @java:Method {
        'class: "org.ballerinalang.redis.ConnectionCommands"
    } external;

    # Echo the given string.
    #
    # + message - Message to be echo-ed
    # + return - Message itself if the operation was successful or `error` if an error occurs
    @display {label: "Echo Input String"}
    isolated remote function echo(@display {label: "String To Echo"} string message)
                         returns @display {label: "Result"} string|error = @java:Method {
        'class: "org.ballerinalang.redis.ConnectionCommands"
    } external;

    # Stops the registered service.
    public isolated function close() = @java:Method {
        'class: "org.ballerinalang.redis.ConnectionCommands"
    } external;
}

# Connection options for Redis Client Endpoint.
#
# + clientName - The clientName of the connection
# + connectionPooling - Boolean value depending on whether the connection
#   pooling is enabled or not
# + isClusterConnection - Whether to enable cluster connection or not
# + ssl - Boolean value depending on whether SSL is enabled or not
# + startTls - Boolean value depending on whether startTLS is enabled or not
# + verifyPeer - Boolean value depending on whether peer verification is
#   enabled or not
# + database - The database to be used with the connection
# + connectionTimeout - The timeout value for the connection
public type Options record {|
    @display{label: "Client Name"} 
    string clientName = "";
    @display{label: "Connection Pooling Enabled"} 
    boolean connectionPooling = false;
    @display{label: "Cluster Connection Enabled"} 
    boolean isClusterConnection = false;
    @display{label: "SSL Enabled"} 
    boolean ssl = false;
    @display{label: "STARTTLS Enabled"} 
    boolean startTls = false;
    @display{label: "Peer Verification Enabled"} 
    boolean verifyPeer = false;
    @display{label: "Database"} 
    int database = -1;
    @display{label: "Connection Timeout"} 
    int connectionTimeout = -1;
|};

# The Client endpoint configuration for Redis databases.
#
# + host - The host of the Redis database
# + password - Password for the database connection
# + options - Properties for the connection configuration
@display{label: "Connection Config"} 
public type ConnectionConfig record {|
    @display{label: "Host"} 
    string host = "localhost";
    @display{label: "Password"} 
    string password = "";
    @display{label: "Connection Options"} 
    Options options = {};
|};
