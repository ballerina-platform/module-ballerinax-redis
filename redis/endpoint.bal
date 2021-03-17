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

# Represents Redis client endpoint.
@display {label: "Redis Client", iconPath: "RedisLogo.png"}
public client class Client {
    private ClientEndpointConfiguration clientEndpointConfig = {};

    handle datasource;

    # Gets called when the endpoint is being initialized during the module initialization.
    public function init(ClientEndpointConfiguration config) returns Error? {
        self.datasource = initClient(config);
    }

    // String operations
    # Append a value to a key.
    #
    # + key - The key
    # + value - The string value to be appended
    # + return - Length of the string after the operation
    @display {label: "Append value to key"}
    remote function append(@display {label: "Key"} string key, @display {label: "Value to be appended"} string value) 
                           returns @display {label: "Length of string after append"} int | Error {
        return append(self.datasource, java:fromString(key), java:fromString(value));
    }

    # Count set bits in a string.
    #
    # + key - The key
    # + return - The number of bits of the value
    @display {label: "Bits of a string value"}
    remote function bitCount(@display {label: "Key"} string key) 
                             returns @display {label: "Bits of string value"} int | Error {
        return bitCount(self.datasource, java:fromString(key));
    }

    # Perform bitwise AND between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform AND between
    # + return - The size of the string stored in the destination key, that is equal to the size of the longest input
    #            string
    @display {label: "Perform Bitwise AND"}
    remote function bitOpAnd(@display {label: "Result key"} string destination, 
                             @display {label: "Key to perform AND"} string[] keys) 
                             returns @display {label: "Size of string"} int | Error {
        return bitOpAnd(self.datasource, java:fromString(destination), keys);
    }

    # Perform bitwise OR between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform AND between
    # + return - The size of the string stored in the destination key, that is equal to the size of the longest input
    #            string or `Error` if an Error occurs
    @display {label: "Perform Bitwise OR"}
    remote function bitOpOr(@display {label: "Result key"} string destination, 
                            @display {label: "Key to perform OR"} string[] keys) 
                            returns @display {label: "Size of string"} int | Error {
        return bitOpOr(self.datasource, java:fromString(destination), keys);
    }

    # Perform bitwise NOT on a string.
    #
    # + destination - Result key of the operation
    # + key - Input keys to perform AND between
    # + return - The size of the string stored in the destination key or `Error` if an Error occurs
    @display {label: "Perform Bitwise Not"}
    remote function bitOpNot(@display {label: "Result key"} string destination, 
                             @display {label: "Key to perform NOT"} string key) 
                             returns @display {label: "Size of string"} int | Error {
        return bitOpNot(self.datasource, java:fromString(destination), java:fromString(key));
    }

    # Perform bitwise XOR between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform AND between
    # + return - The size of the string stored in the destination key, that is equal to the size of the longest input
    #            string or `Error` if an Error occurs
    @display {label: "Perform Bitwise XOR"}
    remote function bitOpXor(@display {label: "Result key"} string destination, 
                             @display {label: "Array of keys to perform XOR"} string[] keys) 
                             returns @display {label: "Size of string"} int | Error {
        return bitOpXor(self.datasource, java:fromString(destination), keys);
    }

    # Decrement the integer value of a key by one.
    #
    # + key - The key
    # + return - The value of the key after the decrement
    @display {label: "Decrement value by one"}
    remote function decr(@display {label: "Key"} string key) returns @display {label: "Value of key"} int | Error {
        return decr(self.datasource, java:fromString(key));
    }

    # Decrement the integer value of a key by the given number.
    #
    # + key - The key
    # + value - The value to be decremented
    # + return - The value of the key after the decrement or `Error` if an Error occurs
    @display {label: "Decrement value by a number"}
    remote function decrBy(@display {label: "Key"} string key, @display {label: "Value to decrement"} int value) 
                           returns @display {label: "Value of key"} int | Error {
        return decrBy(self.datasource, java:fromString(key), value);
    }

    # Returns the bit value at offset in the string value stored at key.
    #
    # + key - The key
    # + offset - The offset
    # + return - The bit value stored at offset or `Error` if an Error occurs
    @display {label: "Get bit value at an offset"}
    remote function getBit(@display {label: "Key"} string key, @display {label: "Offset"} int offset) 
                           returns @display {label: "Bit value"} int | Error {
        return getBit(self.datasource, java:fromString(key), offset);
    }

    # Get a substring of the string stored at a key.
    #
    # + key - The key
    # + startPos - The starting point of the substring
    # + end - The end point of the substring
    # + return - The substring or `Error` if an Error occurs
    @display {label: "Get a substring of string"}
    remote function getRange(@display {label: "Key"} string key, @display {label: "Start position"} int startPos, 
                             @display {label: "End position"} int end) 
                             returns @display {label: "Substring"} string | Error {
        return <string>java:toString(check getRange(self.datasource, java:fromString(key), startPos, end));
    }

    # Set the string value of a key and return its old value.
    #
    # + key - The key
    # + value - The value to be set
    # + return - The old value stored at key, or nil when key does not exist or `Error` if an Error occurs
    @display {label: "Set a new value to a key and return old value"}
    remote function getSet(@display {label: "Key"} string key, @display {label: "New value"} string value) 
                           returns @display {label: "Old value"} string | Error {
        return <string>java:toString(check getSet(self.datasource, java:fromString(key), java:fromString(value)));
    }

    # Get the value of a key.
    #
    # + key - The key
    # + return - The value of the key, or nil when key does not exist or `Error` if an Error occurs
    @display {label: "Get value of a key"}
    remote function get(@display {label: "Key"} string key) returns @display {label: "Value of key"} string | Error {
        return <string>java:toString(check get(self.datasource, java:fromString(key)));
    }

    # Increment the integer value of a key by one.
    #
    # + key - The key
    # + return - The value of the key after increment
    @display {label: "Increment value by one"}
    remote function incr(@display {label: "Key"} string key) returns @display {label: "Value of key"} int | Error {
        return incr(self.datasource, java:fromString(key));
    }

    # Increment the integer value of a key by the given amount.
    #
    # + key - The key
    # + value - The amount to increment
    # + return - The value of the key after increment
    @display {label: "Increment value by a number"}
    remote function incrBy(@display {label: "Key"} string key, @display {label: "Value to increment"} int value) 
                           returns @display {label: "Value of key"} int | Error {
        return incrBy(self.datasource, java:fromString(key), value);
    }
    # Increment the integer value of a key by the given amount.
    #
    # + key - The key
    # + value - The amount to increment
    # + return - The value of the key after increment
    @display {label: "Increment value by a float number"}
    remote function incrByFloat(@display {label: "Key"} string key, 
                                @display {label: "Float value to increment"} float value) 
                                returns @display {label: "Float value of key"} float | Error {
        return incrByFloat(self.datasource, java:fromString(key), value);
    }

    # Get the values of all the given keys.
    #
    # + keys - The keys of which the values need to be retrieved
    # + return - Array of values at the specified keys
    @display {label: "Get list of values of keys"}
    remote function mGet(@display {label: "Array of Keys"} string[] keys) 
                         returns @display {label: "Array of values"} string[] | Error {
        return mGet(self.datasource, keys);
    }

    # Set multiple keys to multiple values.
    #
    # + keyValueMap - A map of key-value pairs to be set
    # + return - A string with the value `OK` if the operation was successful
    @display {label: "Set multiple values for keys"}
    remote function mSet(@display {label: "Map of key value pairs"} map<any> keyValueMap) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check mSet(self.datasource, keyValueMap));
    }

    # Set multiple keys to multiple values, only if none of the keys exist.
    #
    # + keyValueMap - A map of key-value pairs to be set
    # + return - True if the operation was successful, false if it failed
    @display {label: "Set multiple values if keys not exists only"}
    remote function mSetNx(@display{label: "Map of key value pairs"} map<any> keyValueMap) 
                           returns @display{label: "Result"} boolean | Error {
        return mSetNx(self.datasource, keyValueMap);
    }

    # Set the value and expiration in milliseconds of a key.
    #
    # + key - The key
    # + value - The value to be set
    # + expirationTime - Expiration time in milli seconds
    # + return - `OK` if successful or `Error` if an Error occurs
    remote function pSetEx(@display {label: "Key"} string key, @display {label: "Value"} string value,
                           @display {label: "Time to expire in milli seconds"} int expirationTime) 
                           returns @display {label: "Result"} string | Error {
        return <string>java:toString(check pSetEx(self.datasource, java:fromString(key), java:fromString(value),
        expirationTime));
    }

    # Get the value of a key.
    #
    # + key - The key
    # + value - The values
    # + return - `OK` if successful
    @display {label: "Set value to a key"}
    remote function set(@display {label: "Key"} string key, @display {label: "Value of key"} string value) 
                        returns @display {label: "Result"} string | Error {
        return <string>java:toString(check set(self.datasource, java:fromString(key), java:fromString(value)));
    }

    # Sets or clears the bit at offset in the string value stored at key.
    #
    # + key - The key
    # + value - The value to be set
    # + offset - The offset at which the value should be set
    # + return - The original bit value stored at offset or `Error` if an Error occurs
    @display {label: "Set or clear bit value stored from offset"} 
    remote function setBit(@display {label: "Key"} string key, @display {label: "Value"} int value, 
                           @display {label: "Offset"} int offset) 
                           returns @display {label: "Old bit value at offset"} int | Error {
        return setBit(self.datasource, java:fromString(key), value, offset);
    }

    # Set the value and expiration of a key.
    #
    # + key - The key
    # + value - The value to be set
    # + expirationPeriodSeconds - Expiration time to be set, in seconds
    # + return - On success `OK` or `Error` if an Error occurs
    @display {label: "Set a string for key and expiration time"} 
    remote function setEx(@display {label: "Key"} string key, @display {label: "Value"} string value, 
                          @display {label: "Time to expire in seconds"} int expirationPeriodSeconds) 
                          returns @display {label: "Result"} string | Error {
        return <string>java:toString(check setEx(self.datasource, java:fromString(key), java:fromString(value),
        expirationPeriodSeconds));

    }

    # Set the value of a key, only if the key does not exist.
    #
    # + key - The key
    # + value - The value to be set
    # + return - `True` if exist `False` if not or `Error` if an Error occurs
    @display {label: "Set a string for key not existed"} 
    remote function setNx(@display {label: "Key"} string key, @display {label: "Value"} string value) 
                          returns @display {label: "Result"} boolean | Error {
        return setNx(self.datasource, java:fromString(key), java:fromString(value));
    }

    # Overwrite part of a string at key starting at the specified offset.
    #
    # + key - The key
    # + offset - The offset at which the value should be set
    # + value - The value to be set
    # + return - The length of the string after it was modified or `Error` if an Error occurs
    @display {label: "Overwrite a string from specific place"} 
    remote function setRange(@display {label: "Key"} string key, 
                             @display {label: "Start position of overwrite"} int offset, 
                             @display {label: "Value"} string value) 
                             returns @display {label: "Length of string resulted"} int | Error {
        return setRange(self.datasource, java:fromString(key), offset, java:fromString(value));
    }

    # Get the length of the value stored in a key.
    #
    # + key - The key
    # + return - The length of the string at key, or 0 when key does not exist or `Error` if an Error occurs
    @display {label: "Get length of string stored"} 
    remote function strln(@display {label: "Key"} string key) 
                          returns @display {label: "Length of string at key"} int | Error {
        return strln(self.datasource, java:fromString(key));
    }

    // list operations

    # Prepend one or multiple values to a list.
    #
    # + key - The key
    # + values - The values to be prepended
    # + return - The length of the list after the push operation(s) or `Error` if an Error occurs
    @display {label: "Push value to a list"}
    remote function lPush(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                          returns @display {label: "Length of the list"} int | Error {
        return lPush(self.datasource, java:fromString(key), values);
    }

    # Remove and get the first element in a list.
    #
    # + key - The key
    # + return - The value of the first element, or nil when key does not exist or `Error` if an Error occurs
    @display {label: "Pop value from list"}
    remote function lPop(@display {label: "Key"} string key) 
                         returns @display {label: "Value of first element popped"} string | Error {
        return <string>java:toString(check lPop(self.datasource, java:fromString(key)));
    }

    # Prepend one or multiple values to a list, only if the list exists.
    #
    # + key - The key
    # + values - The values to be prepended
    # + return - The length of the list after the push operation(s)
    @display {label: "Push value to a list if exists"}
    remote function lPushX(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                           returns @display {label: "Length of the list"} int | Error {
        return lPushX(self.datasource, java:fromString(key), values);
    }

    # Remove and get the first element in a list, or block until one is available.
    #
    # + timeOut - The timeout in seconds
    # + keys - The keys
    # + return - `nil` when no element could be popped and the timeout expired. A map containing one item, with the
    #         key being  the name of the key where an element was popped and the second element  being the value of the
    #         popped element, or `Error` if an Error occurs
    @display {label: "Pop from first value from list and block on element not exists"}
    remote function bLPop(@display {label: "Timeout"} int timeOut, @display {label: "Array of keys"} string[] keys) 
                          returns @display {label: "Key-value pair"} map<any> | Error {
        return bLPop(self.datasource, timeOut, keys);
    }

    # Remove and get the last element in a list, or block until one is available.
    #
    # + timeOut - The timeout in seconds
    # + keys - The keys
    # + return - `nil` when no element could be popped and the timeout expired. A map containing one item, with the
    #         key being  the name of the key where an element was popped and the second element being the value of the
    #         popped element, or `Error` if an Error occurs
    @display {label: "Pop from last value from list and block on element not exists"}
    remote function bRPop(@display {label: "Timeout"} int timeOut, @display {label: "Array of keys"} string[] keys) 
                          returns @display {label: "Key-value pair"} map<any> | Error {
        return bRPop(self.datasource, timeOut, keys);
    }

    # Get an element from a list by its index.
    #
    # + key - The key
    # + index - The index from which the element should be retrieved
    # + return - The value at the given index
    @display {label: "Get a value in a list by index"}
    remote function lIndex(@display {label: "Key"} string key, @display {label: "Index"} int index) 
                           returns @display {label: "Value at index"} string | Error {
        return <string>java:toString(check lIndex(self.datasource, java:fromString(key), index));
    }

    # Insert an element before or after another element in a list.
    #
    # + key - The key
    # + before - boolean value representing Whether element should be inserted before or after the pivot
    # + pivot - The pivot
    # + value - The value
    # + return - The length of the list after the insert operation, or -1 when the value pivot not found, or `Error` if
    #           an Error occurs
    @display {label: "Insert a value in a list at specific position"}
    remote function lInsert(@display {label: "Key"} string key, 
                            @display {label: "Insert before or not"} boolean before, 
                            @display {label: "Place to insert"} string pivot, @display {label: "Value"} string value) 
                            returns @display {label: "Length of list after insertion"} int | Error {
        return lInsert(self.datasource, java:fromString(key), before, java:fromString(pivot), java:fromString(value));
    }

    # Get the length of a list.
    #
    # + key - The key
    # + return - The length of the list at key or `Error` if an Error occurs
    @display {label: "Get length of list at a key"}
    remote function lLen(@display {label: "Key"} string key) returns @display {label: "Length of list"} int | Error {
        return lLen(self.datasource, java:fromString(key));
    }

    # Get a range of elements from a list.
    #
    # + key - The key
    # + startPos - The begining index of the range
    # + stopPos - The last index of the range
    # + return - Array of elements in the specified range or `Error` if an Error occurs
    @display {label: "Get range of elements of list at a key"}
    remote function lRange(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                           @display {label: "End Position"} int stopPos) 
                           returns @display {label: "Array of elements in range"} string[] | Error {
        return lRange(self.datasource, java:fromString(key), startPos, stopPos);
    }

    # Remove elements from a list.
    #
    # + key - The key
    # + count - The number of elements to be removed
    # + value - The value which the elements to be removed should be equal to
    # + return - Number of elements removed or `Error` if an Error occurs
    @display {label: "Remove elements from list at a key"}
    remote function lRem(@display {label: "Key"} string key, @display {label: "Number of members"} int count, 
                         @display {label: "Value"} string value) 
                         returns @display {label: "Number of members removed"} int | Error {
        return lRem(self.datasource, java:fromString(key), count, java:fromString(value));
    }

    # Set the value of an element in a list by its index.
    #
    # + key - The key of the list
    # + index - The index of the element of which the value needs to be set
    # + value - The value to be set
    # + return - A string with the value `OK` if the operation was successful or `Error` if an Error occurs
    @display {label: "Set value of an element in list at index"}
    remote function lSet(@display {label: "Key"} string key, @display {label: "Index"} int index, 
                         @display {label: "Value"} string value) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check lSet(self.datasource, java:fromString(key), index, java:fromString(value)));
    }

    # Trim a list to the specified range.
    #
    # + key - The key of the list
    # + startPos - The starting index of the range
    # + stopPos - The end index of the range
    # + return - A string with the value `OK` if the operation was successful
    @display {label: "Trim a list to a range"}
    remote function lTrim(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                          @display {label: "End Position"} int stopPos) 
                          returns @display {label: "Result"} string | Error {
        return <string>java:toString(check lTrim(self.datasource, java:fromString(key), startPos, stopPos));
    }

    # Remove and get the last element in a list.
    #
    # + key - The key of the list
    # + return - The value of the last element, or `nil` when key does not exist or `Error` if an Error occurs
    @display {label: "Pop last element in list"}
    remote function rPop(@display {label: "Key"} string key) 
                         returns @display {label: "Value of popped element"} string | Error {
        return <string>java:toString(check rPop(self.datasource, java:fromString(key)));
    }

    # Remove the last element in a list, append it to another list and return it.
    #
    # + src - The source key
    # + destination - The destination key
    # + return - The element being popped and pushed or `Error` if an Error occurs
    @display {label: "Move last element in a list to another list"}
    remote function rPopLPush(@display {label: "Current key of element"} string src, 
                              @display {label: "Destination key"} string destination) 
                              returns @display {label: "Value of element"} string | Error {
        return <string>java:toString(check rPopLPush(self.datasource, java:fromString(src),
        java:fromString(destination)));
    }

    # Append one or multiple values to a list.
    #
    # + key - The key of the list
    # + values - Array of values to be appended
    # + return - The length of the list after the push operation or `Error` if an Error occurs
    @display {label: "Append values to list"}
    remote function rPush(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                          returns @display {label: "Length of list"} int | Error {
        return rPush(self.datasource, java:fromString(key), values);
    }

    # Append one or multiple values to a list, only if the list exists.
    #
    # + key - The key of the list
    # + values - Array of values to be appended
    # + return - The length of the list after the push operation or `Error` if an Error occurs
    @display {label: "Append values to list if exists"}
    remote function rPushX(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                           returns @display {label: "Length of list"} int | Error {
        return rPushX(self.datasource, java:fromString(key), values);
    }

    // Set Commands

    # Add one or more members to a set.
    #
    # + key - The key of the set
    # + values - Array of values to be added
    # + return - The number of elements that were added to the set, not including all the elements which were
    #            already present in the set, or `Error` if an Error occurs
    @display {label: "Add members to a set"}
    remote function sAdd(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                         returns @display {label: "Number of elements added"} int | Error {
        return sAdd(self.datasource, java:fromString(key), values);
    }

    # Get the number of members in a set
    #
    # + key - The key of the set
    # + return - The cardinality (number of elements) of the set or `Error` if an Error occurs
    @display {label: "Get number of members in a set"}
    remote function sCard(@display {label: "Key"} string key) 
                          returns @display {label: "Number of elements in a set"} int | Error {
        return sCard(self.datasource, java:fromString(key));
    }

    # Return set resulting from the difference between the first set and all the successive sets
    #
    # + keys - The keys of the sets
    # + return - An array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Difference of a set"}
    remote function sDiff(@display {label: "Array of keys"} string[] keys) 
                          returns @display {label: "Array of members in result set"} string[] | Error {
        return sDiff(self.datasource, keys);
    }

    # Obtain the set resulting from the difference between the first set and all the successive.
    # sets and store at the provided destination.
    #
    # + destination - The destination key of the resulting set
    # + keys - The keys of the sets to find the difference of
    # + return - The number of members in the resulting set or `Error` if an Error occurs
    @display {label: "Set difference of a set to specific destination"}
    remote function sDiffStore(@display {label: "Destination key"} string destination, 
                               @display {label: "Array of keys"} string[] keys) 
                               returns @display {label: "Number of members in result set"} int | Error {
        return sDiffStore(self.datasource, java:fromString(destination), keys);
    }

    # Return the intersection of the provided sets.
    #
    # + keys - The keys of the sets to be intersected
    # + return - An array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Intersections of sets"}
    remote function sInter(@display {label: "Array of keys"} string[] keys) 
                           returns @display {label: "Array of members in result set"} string[] | Error {
        return sInter(self.datasource, keys);
    }

    # Obtain the intersection of the provided sets and store at the provided destination.
    #
    # + destination - The destination key of the resulting set
    # + keys - The keys of the sets to be intersected
    # + return - An array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Get intersections of sets to specific destination"}
    remote function sInterStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Array of members"} int | Error {
        return sInterStore(self.datasource, java:fromString(destination), keys);
    }

    # Determine if a given value is a member of a set.
    #
    # + key - The key of the set
    # + value - The value
    # + return - boolean true/false depending on whether the value is a member of the set or not, or `Error` if an Error
    #            occurs
    @display {label: "Check whether value in a set"}
    remote function sIsMember(@display {label: "Key"} string key, @display {label: "Value"} string value) 
                              returns @display {label: "Result"} boolean | Error {
        return sIsMember(self.datasource, java:fromString(key), java:fromString(value));
    }

    # Get all the members in a set.
    #
    # + key - The key of the set
    # + return - Array of all members in the set or `Error` if an Error occurs
    @display {label: "Get all members in a set"}
    remote function sMembers(@display {label: "Key"} string key) 
                             returns @display {label: "Array of members in a set"} string[] | Error {
        return sMembers(self.datasource, java:fromString(key));
    }

    # Move a member from one set to another.
    #
    # + src - The source key
    # + destination - The destination key
    # + member - The member to be moved
    # + return - `true` if the element is moved. `false` if the element is not a member of source and no
    #             operation was performed or `Error` if an Error occurs
    @display {label: "Move a member in a set to another"}
    remote function sMove(@display {label: "Source key"} string src, 
                          @display {label: "Destination key"} string destination, 
                          @display {label: "Member in a set"} string member) 
                          returns @display {label: "Result"} boolean | Error {
        return sMove(self.datasource, java:fromString(src), java:fromString(destination), java:fromString(member));
    }


    # Remove and return a random member from a set.
    #
    # + key - The source key
    # + count - Number of members to pop
    # + return - Array of removed elements or `nil` if key does not exist or `Error` if an Error occurs
    @display {label: "Pop a random member in a set"}
    remote function sPop(@display {label: "Key"} string key, @display {label: "Number of members"} int count) 
                         returns @display {label: "Array of removed elements"} string[] | Error {
        return sPop(self.datasource, java:fromString(key), count);
    }

    # Get one or multiple random members from a set.
    #
    # + key - The key of the set
    # + count - Number of members to obtain
    # + return - Array of the randomly selected elements, or `nil` when key does not exist or `Error` if an Error occurs
    @display {label: "Get a random member in a set"}
    remote function sRandMember(@display {label: "Key"} string key, @display {label: "Number of members"} int count) 
                                returns @display {label: "Array of elements"} string[] | Error {
        return sMembers(self.datasource, java:fromString(key));
    }

    # Remove one or more members from a set.
    #
    # + key - The key of the set
    # + members - Array of members to remove
    # + return - The number of members that were removed from the set, not including non existing members or `Error` if
    #            an Error occurs
    @display {label: "Remove multiple members in a set"}
    remote function sRem(@display {label: "Key"} string key, @display {label: "Array of members"} string[] members) 
                         returns @display {label: "Number of members removed"} int | Error {
        return sRem(self.datasource, java:fromString(key), members);
    }

    # Return the union of multiple sets.
    #
    # + keys - Array of keys of sets
    # + return - Array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Get union of multiple sets"}
    remote function sUnion(@display {label: "Array of keys"} string[] keys) 
                           returns @display {label: "Array of members"} string[] | Error {
        return sUnion(self.datasource, keys);
    }

    # Return the union of multiple sets.
    #
    # + destination - The destination key of the resulting set
    # + keys - Array of keys of sets
    # + return - Number of members of the resulting set or `Error` if an Error occurs
    @display {label: "Set union of multiple sets to specific destination"}
    remote function sUnionStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Number of members in result set"} int | Error {
        return sUnionStore(self.datasource, java:fromString(destination), keys);
    }

    // Sorted set Commands

    # Add one or more members to a sorted set, or update its score if it already exist.
    #
    # + key - The key of the sorted set
    # + memberScoreMap - A map of members and corresponding scores
    # + return - The number of elements that were added to the sorted set, not including all the elements which were
    #            already present in the set for which the score was updated, or `Error` if an Error occurs
    @display {label: "Add members to a sorted set"} 
    remote function zAdd(@display {label: "Key"} string key, 
                         @display {label: "Member-value pairs"} map<any> memberScoreMap) 
                         returns @display {label: "Number of members added"} int | Error {
        return zAdd(self.datasource, java:fromString(key), memberScoreMap);
    }

    # Get the number of members in a sorted set.
    #
    # + key - The key of the sorted set
    # + return - The cardinality (number of elements) of the sorted set or `Error` if an Error occurs
    @display {label: "Get number of members in a sorted set"}
    remote function zCard(@display {label: "Key"} string key) 
                          returns @display {label: "Number of members in sorted set"} int | Error {
        return zCard(self.datasource, java:fromString(key));
    }

    # Count the members in a sorted set with scores within the given range.
    #
    # + key - The key of the sorted set
    # + min - The minimum score of the range
    # + max - The maximum score of the range
    # + return - The number of elements in the specified score range or `Error` if an Error occurs
    @display {label: "Get members in a range of sorted set"}
    remote function zCount(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                           @display {label: "Maximum value"} float max) 
                           returns @display {label: "Number of members between range in sorted set"} int | Error {
        return zCount(self.datasource, java:fromString(key), min, max);
    }

    # Increment the score of a member in a sorted set.
    #
    # + key - The key of the sorted set
    # + amount - The amount to increment
    # + member - The member whose score to be incremented
    # + return - The new score of the member or `Error` if an Error occurs
    @display {label: "Increment a member in a sorted set"}
    remote function zIncrBy(@display {label: "Key"} string key, @display {label: "Amount to increment"} float amount, 
                            @display {label: "Member to increment"} string member) 
                            returns @display {label: "Incremented score of the member"} float | Error {
        return zIncrBy(self.datasource, java:fromString(key), amount, java:fromString(member));
    }

    # Intersect multiple sorted sets and store the resulting sorted set in a new key.
    #
    # + destination - The destination key of the resulting sorted set
    # + keys - The keys of the sorted sets to be intersected
    # + return - The number of elements in the resulting sorted set or `Error` if an Error occurs
    @display {label: "Get number of members in intersection of sorted sets"}
    remote function zInterStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Number of members"} int | Error {
        return zInterStore(self.datasource, java:fromString(destination), keys);
    }

    # Count the members in a sorted set within the given lexicographical range.
    #
    # + key - The key of the sorted set
    # + min - The minimum lexicographical value of the range
    # + max - The maximum lexicographical value of the range
    # + return - The number of elements in the specified lexicographical value range or `Error` if an Error occurs
    @display {label: "Get number of members in lexicographical range"}
    remote function zLexCount(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                              @display {label: "Maximum value"} string max) 
                              returns @display {label: "Number of members"} int | Error {
        return zLexCount(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Return a range of members in a sorted set, by index.
    #
    # + key - The key of the sorted set
    # + min - The minimum index of the range
    # + max - The maximum index of the range
    # + return - The range of members in a sorted set, by index, or `Error` if an Error occurs
    @display {label: "Get members by index range in a sorted set"}
    remote function zRange(@display {label: "Key"} string key, @display {label: "Minimum index"} int min, 
                           @display {label: "Maximum index"} int max) 
                           returns @display {label: "Array of members"} string[] | Error {
        return zRange(self.datasource, java:fromString(key), min, max);
    }

    # Return a range of members in a sorted set, by lexicographical range from lowest to highest.
    #
    # + key - The key of the sorted set
    # + min - The minimum lexicographical value of the range
    # + max - The maximum lexicographical value of the range
    # + return - Array of members in the specified lexicographical value range ordered from lowest to highest or `Error`
    #            if an Error occurs
    @display {label: "Get members by lexicographical range from a sorted set"}
    remote function zRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                                @display {label: "Maximum value"} string max) 
                                returns @display {label: "Array of members"} string[] | Error {
        return zRangeByLex(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Return a range of members in a sorted set, by lexicographical range ordered from highest to
    # lowest.
    #
    # + key - The key of the sorted set
    # + min - The lexicographical value of the range
    # + max - The maximum lexicographical value of the range
    # + return - Array of members in the specified lexicographical value range ordered from highest to lowest or `Error`
    #            if an Error occurs
    @display {label: "Get ordered list of members by lexicographical range from a sorted set"}
    remote function zRevRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                                   @display {label: "Maximum value"} string max) 
                                   returns @display {label: "Array of members"} string[] | Error {
        return zRevRangeByLex(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Return a range of members in a sorted set, by score from lowest to highest.
    #
    # + key - The key of the sorted set
    # + min - The minimum score of the range
    # + max - The maximum score of the range
    # + return - Array of members in the specified score range ordered from lowest to highest or `Error` if an Error
    #            occurs
    @display {label: "Get members by score range in a sorted set"}
    remote function zRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                                  @display {label: "Maximum value"} float max) 
                                  returns @display {label: "Array of members"} string[] | Error {
        return zRangeByScore(self.datasource, java:fromString(key), min, max);
    }

    # Determine the index of a member in a sorted set.
    #
    # + key - The key of the sorted set
    # + member - The member of which the index needs to be obtained
    # + return - The index of the member or `Error` if an Error occurs
    @display {label: "Get index of a member in sorted set"}
    remote function zRank(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                          returns @display {label: "Index"} int | Error {
        return zRank(self.datasource, java:fromString(key), java:fromString(member));
    }

    # Remove one or more members from a sorted set
    #
    # + key - The key of the sorted set
    # + members - The members to be removed
    # + return - The number of members removed from the sorted set, not including non existing members or `Error` if an
    #            Error occurs
    @display {label: "Remove members from sorted set"}
    remote function zRem(@display {label: "Key"} string key, @display {label: "Array of members"} string[] members) 
                         returns @display {label: "Number of members removed"} int | Error {
        return zRem(self.datasource, java:fromString(key), members);
    }


    # Remove all members in a sorted set between the given lexicographical range.
    #
    # + key - The key of the sorted set
    # + min - The minimum lexicographical value of the range
    # + max - The maximum lexicographical value of the range
    # + return - The number of members removed from the sorted set or `Error` if an Error occurs
    @display {label: "Remove members between range in lexicographical range"}
    remote function zRemRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                                   @display {label: "Maximum value"} string max) 
                                   returns @display {label: "Number of members removed"} int | Error {
        return zRemRangeByLex(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Remove all members in a sorted set within the given indices.
    #
    # + key - The key of the sorted set
    # + min - The minimum index of the range
    # + max - The maximum index of the range
    # + return - The number of members removed from the sorted set or `Error` if an Error occurs
    @display {label: "Number of members removed between indexes"}
    remote function zRemRangeByRank(@display {label: "Key"} string key, @display {label: "Minimum index"} int min, 
                                    @display {label: "Maximum index"} int max) 
                                    returns @display {label: "Number of members removed"} int | Error {
        return zRemRangeByRank(self.datasource, java:fromString(key), min, max);
    }

    # Remove all members in a sorted set within the given scores.
    #
    # + key - The key of the sorted set
    # + min - The minimum score of the range
    # + max - The maximum score of the range
    # + return - The number of members removed from the sorted set or `Error` if an Error occurs
    @display {label: "Number of members removed between rang of scores"}
    remote function zRemRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                                     @display {label: "Maximum value"} float max) 
                                     returns @display {label: "Remove members between range from sorted set"} int | Error {
        return zRemRangeByScore(self.datasource, java:fromString(key), min, max);
    }

    # Return a range of members in a sorted set, by index, ordered highest to lowest.
    #
    # + key - The key of the sorted set
    # + min - The minimum index of the range
    # + max - The maximum index of the range
    # + return - The number of elements in the specified index range or `Error` if an Error occurs
    @display {label: "Get members between range of indexes"}
    remote function zRevRange(@display {label: "Key"} string key, @display {label: "Minimum index"} int min, 
                              @display {label: "Maximum index"} int max) 
                              returns @display {label: "Array of members"} string[] | Error {
        return zRevRange(self.datasource, java:fromString(key), min, max);
    }

    # Return a range of members in a sorted set, by score from highest to lowest.
    #
    # + key - The key of the sorted set
    # + min - The minimum score of the range
    # + max - The maximum score of the range
    # + return - Array of members in the specified score range ordered from highest to lowest or `Error` if an Error
    #            occurs
    @display {label: "Get members between range of scores"}
    remote function zRevRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                                     @display {label: "Maximum value"} float max) 
                                     returns @display {label: "Array of members"} string[] | Error {
        return zRevRangeByScore(self.datasource, java:fromString(key), min, max);
    }

    # Determine the index of a member in a sorted set
    #
    # + key - The key of the sorted set
    # + member - The member of which the index needs to be obtained
    # + return - The index of the member or `Error` if an Error occurs
    @display {label: "Get index of member in sorted set"}
    remote function zRevRank(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                             returns @display {label: "Index"} int | Error {
        return zRevRank(self.datasource, java:fromString(key), java:fromString(member));
    }

    # Determine the score of a member in a sorted set
    #
    # + key - The key of the sorted set
    # + member - The member of which the score needs to be obtained
    # + return - The score of the member or `Error` if an Error occurs
    @display {label: "Get score of member in sorted set"}
    remote function zScore(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                           returns @display {label: "Score"} float | Error {
        return zScore(self.datasource, java:fromString(key), java:fromString(member));
    }

    # Return the union of multiple sorted sets
    #
    # + destination - The destination key of the resulting set
    # + keys - Array of keys of sorted sets
    # + return - Number of members of the resulting sorted set or `Error` if an Error occurs
    @display {label: "Get union of sorted sets"}
    remote function zUnionStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Number of members"} int | Error {
        return zUnionStore(self.datasource, java:fromString(destination), keys);
    }

    // Hash Commands

    # Delete one or more hash fields.
    #
    # + key - The key of the hash
    # + fields - Array of fields to be deleted
    # + return - Number of fields that were removed from the hash, not including specified but non existing fields or
    #            `Error` if an Error occurs
    @display {label: "Delete hash fields"}
    remote function hDel(@display {label: "Key"} string key, @display {label: "Array of fields"} string[] fields) 
                         returns @display {label: "Number of fields removed"} int | Error {
        return hDel(self.datasource, java:fromString(key), fields);
    }

    # Determine if a hash field exists.
    #
    # + key - The key of the hash
    # + field - Array of fields to be deleted
    # + return - boolean `true` if the hash contains the field. boolean false if the hash does not contain
    #            field or key does not exist or `Error` if an Error occurs
    @display {label: "Check existance of hash fields"}
    remote function hExists(@display {label: "Key"} string key, @display {label: "Field"} string 'field) 
                            returns @display {label: "Result"} boolean | Error {
        return hExists(self.datasource, java:fromString(key), java:fromString('field));
    }

    # Get the value of a hash field.
    #
    # + key - The key of the hash
    # + field - The field
    # + return - The value of the field or `Error` if an Error occurs
    @display {label: "Get value of the hash field"} 
    remote function hGet(@display {label: "Key"} string key, @display {label: "Field"} string 'field) 
                         returns @display {label: "Value of field"} string | Error {
        return <string>java:toString(check hGet(self.datasource, java:fromString(key), java:fromString('field)));
    }

    # Get the all values of a hash.
    #
    # + key - The key of the hash
    # + return - Map of field-value pairs or `Error` if an Error occurs
    @display {label: "Get all values of hash"} 
    remote function hGetAll(@display {label: "Key"} string key) 
                            returns @display {label: "Field-value pairs"} map<any> | Error {
        return hGetAll(self.datasource, java:fromString(key));
    }

    # Increment the integer value of a hash field by the given number.
    #
    # + key - The key of the hash
    # + field - The field
    # + amount - The amount to increment
    # + return - The value of the field or `Error` if an Error occurs
    @display {label: "Increment integer value of field in hash"} 
    remote function hIncrBy(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                            @display {label: "Value to increment"} int amount) 
                            returns @display {label: "Incremented value"} int | Error {
        return hIncrBy(self.datasource, java:fromString(key), java:fromString('field), amount);
    }

    # Increment the float value of a hash field by the given number.
    #
    # + key - The key of the hash
    # + field - The field
    # + amount - The amount to increment
    # + return - The value of the field or `Error` if an Error occurs
    @display {label: "Increment integer value of field in hash"} 
    remote function hIncrByFloat(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                                 @display {label: "Value to increment"} float amount) 
                                 returns @display {label: "Incremented value"} float | Error {
        return hIncrByFloat(self.datasource, java:fromString(key), java:fromString('field), amount);
    }

    # Get all the fields in a hash.
    #
    # + key - The key of the hash
    # + return - Array of hash fields or `Error` if an Error occurs
    @display {label: "Get all fields in a hash"} 
    remote function hKeys(@display {label: "Key"} string key) 
                          returns @display {label: "Array of hash fields"} string[] | Error {
        return hKeys(self.datasource, java:fromString(key));
    }

    # Get the number of fields in a hash.
    #
    # + key - The key of the hash
    # + return - Number of fields or `Error` if an Error occurs
    @display {label: "Get number of fields in a hash"} 
    remote function hLen(@display {label: "Key"} string key) 
                         returns @display {label: "Number of hash fields"} int | Error {
        return hLen(self.datasource, java:fromString(key));
    }

    # Get the values of all the given hash fields.
    #
    # + key - The key of the hash
    # + fields - Array of hash fields
    # + return - Map of field-value pairs or `Error` if an Error occurs
    @display {label: "Get values of selected fields in a hash"} 
    remote function hMGet(@display {label: "Key"} string key, @display {label: "Array of fields"} string[] fields) 
                          returns @display {label: "Field-value pairs"} map<any> | Error {
        return hMGet(self.datasource, java:fromString(key), fields);
    }

    # Set multiple hash fields to multiple values.
    #
    # + key - The key of the hash
    # + fieldValueMap - Map of field-value pairs
    # + return - A string with the value `OK` if the operation was successful, or `Error` if an Error occurs
    @display {label: "Add values to hash fields"} 
    remote function hMSet(@display {label: "Key"} string key, 
                          @display {label: "Field-value pairs"} map<any> fieldValueMap) 
                          returns @display {label: "Result"} string | Error {
        return <string>java:toString(check hMSet(self.datasource, java:fromString(key), fieldValueMap));
    }

    # Set the string value of a hash field.
    #
    # + key - The key of the hash
    # + field - The field
    # + value - The value to be set to the field
    # + return - boolean `true` if field is a new field in the hash and value was set. boolean false if
    #         field already exists in the hash and the value was updated, or `Error` if an Error occurs
    @display {label: "Add value to a hash field"} 
    remote function hSet(string key, string 'field, string value) returns boolean | Error {
        return hSet(self.datasource, java:fromString(key), java:fromString('field), java:fromString(value));
    }

    # Set the string value of a hash field, only if the field does not exist.
    #
    # + key - The key of the hash
    # + field - The field
    # + value - The value to be set to the field
    # + return - boolean `true` if field is a new field in the hash and value was set. boolean false if
    #            field already exists in the hash and no operation was performed, or `Error` if an Error occurs
    @display {label: "Add value to a hash for non-existance field"} 
    remote function hSetNx(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                           @display {label: "Value"} string value) returns @display {label: "Result"} boolean | Error {
        return hSetNx(self.datasource, java:fromString(key), java:fromString('field), java:fromString(value));
    }

    # Get the string length of the field value in a hash.
    #
    # + key - The key of the hash
    # + field - The field
    # + return - The length of the field value, or 0 when field is not present in the hash or key does
    #            not exist at all, or `Error` if an Error occurs
    @display {label: "Get the string length for value of the field"}
    remote function hStrln(@display {label: "Key"} string key, @display {label: "Field in the hash"} string 'field) 
                           returns @display {label: "String length of value in field"} int | Error {
        return hStrln(self.datasource, java:fromString(key), java:fromString('field));
    }

    # Get all the values in a hash.
    #
    # + key - The key of the hash
    # + return - Array of values in the hash, or an empty array when key does not exist or `Error` if an Error occurs
    @display {label: "Get values in the hash stored at key"}
    remote function hVals(@display {label: "Key"} string key) 
                          returns @display {label: "Array of values"} string[] | Error {
        return hVals(self.datasource, java:fromString(key));
    }

    // Key Commands

    # Delete one or more keys.
    #
    # + keys - The key to be deleted
    # + return - The number of keys that were removed
    @display {label: "Delete a bulk of keys"}
    remote function del(@display {label: "Array of keys"} string[] keys) 
                        returns @display {label: "Number of keys deleted"} int | Error {
        return del(self.datasource, keys);
    }

    # Determine how many keys exist.
    #
    # + keys - The keys of which existence to be found out
    # + return - The number of existing keys or `Error` if an Error occurs
    @display {label: "Check whether keys exists"}
    remote function exists(@display {label: "Array of keys"} string[] keys) 
                           returns @display {label: "Number of keys exists"} int | Error {
        return exists(self.datasource, keys);
    }

    # Set a key's time to live in seconds.
    #
    # + key - The keys of which expiry time to be set
    # + seconds - Expiry in seconds
    # + return - boolean `true` if the timeout was set. false if key does not exist or the timeout could not be set or
    # `Error` if an Error occurs
    @display {label: "Set time to live in seconds"}
    remote function expire(@display {label: "Key"} string key, @display {label: "Time in seconds"} int seconds) 
                           returns @display {label: "Result"} boolean | Error {
        return expire(self.datasource, java:fromString(key), seconds);
    }

    # Find all keys matching the given pattern.
    #
    # + pattern - The pattern to match
    # + return - Array of keys matching the given pattern or `Error` if an Error occurs
    @display {label: "Get keys matching a pattern"}
    remote function keys(@display {label: "Pattern string"} string pattern) 
                         returns @display {label: "Array of keys"} string[] | Error {
        return keys(self.datasource, java:fromString(pattern));
    }

    # Move a key to another database.
    #
    # + key - The key to be moved
    # + database - The database to which the key needs to be moved
    # + return - boolean true if key was succesfully moved, boolean false otherwise or `Error` if an Error occurs
    @display {label: "Move a key"}
    remote function move(@display {label: "Key"} string key, 
                         @display {label: "Database to which key should be moved"} int database) 
                         returns @display {label: "Result"} boolean | Error {
        return move(self.datasource, java:fromString(key), database);
    }

    # Remove the expiration from a key.
    #
    # + key - The key of which expiry time should be removed
    # + return - boolean `true` if the timeout was removed. boolean `false` if key does not exist or does not have
    #            an associated timeout, or `Error` if an Error occurs
    @display {label: "Remove key timeout"}
    remote function persist(@display {label: "Key"} string key) returns @display {label: "Result"} boolean | Error {
        return persist(self.datasource, java:fromString(key));
    }

    # Set a key's time to live in milliseconds..
    #
    # + key - The key of which expiry time should be removed
    # + timeMilliSeconds - The expiry time in milli seconds
    # + return - boolean `true` if the timeout was set. boolean false if key does not exist or the timeout could not
    #         be set, or `Error` if an Error occurs
    @display {label: "Set time to live in milli seconds"}
    remote function pExpire(@display {label: "Key"} string key, 
                            @display {label: "Time in milli seconds"} int timeMilliSeconds) 
                            returns @display {label: "Result"} boolean | Error {
        return pExpire(self.datasource, java:fromString(key), timeMilliSeconds);
    }

    # Get the time to live for a key in milliseconds
    #
    # + key - The key of which time-to-live should be obtained
    # + return - time-to-live of the key, in milli seconds or `Error` if an Error occurs
    @display {label: "Get time to live in milli seconds"}
    remote function pTtl(@display {label: "Key"} string key) 
                         returns @display {label: "Time to live in milli seconds"} int | Error {
        return pTtl(self.datasource, java:fromString(key));
    }

    # Return a random key from the keyspace.
    #
    # + return - The random key, or `nil` when the database is empty or `Error` if an Error occurs
    @display {label: "Get a random key"}
    remote function randomKey() returns @display {label: "Key"} string | Error {
        return <string>java:toString(check randomKey(self.datasource));
    }

    # Rename a key.
    #
    # + key - The key to be renamed
    # + newName - The new name of the key
    # + return - A string with the value `OK` if the operation was successful or `Error` if an Error occurs
    @display {label: "Rename a key"}
    remote function rename(@display {label: "Key"} string key, @display {label: "New name to rename"} string newName)
                           returns @display {label: "Result"} string | Error {
        return <string>java:toString(check rename(self.datasource, java:fromString(key), java:fromString(newName)));
    }

    # Rename a key, only if the new key does not exist.
    #
    # + key - The key to be renamed
    # + newName - The new name of the key
    # + return - boolean `true` if key was renamed to newkey. boolean `false` if newkey already exists. Or `Error` if an
    #            Error occurs
    @display {label: "Rename a for non-existance key"}
    remote function renameNx(@display {label: "Key"} string key, @display {label: "New name to rename"} string newName)
                             returns @display {label: "Result"} boolean | Error {
        return renameNx(self.datasource, java:fromString(key), java:fromString(newName));
    }

    # Sort the elements in a list, set or sorted set.
    #
    # + key - The key of the data typeure to be sorted
    # + return - Sorted array containing the members of the sorted data type or `Error` if an Error occurs
    @display {label: "Sort the elements"}
    remote function sort(@display {label: "Key"} string key) 
                         returns @display {label: "Sorted array of members"} string[] | Error {
        return sort(self.datasource, java:fromString(key));
    }

    # Get the time to live for a key.
    #
    # + key - The key of which the time to live needs to be obtained
    # + return - Time to live in seconds or a negative value/`Error` in order to signal an Error in evaluating ttl.
    #         Whether it is a negative value of an `Error` would differ depending on whether the Error occurs at DB
    #         level or the driver level
    @display {label: "Get time to live"}
    remote function ttl(@display {label: "Key"} string key) 
                        returns @display {label: "Time to live in seconds"} int | Error {
        return ttl(self.datasource, java:fromString(key));
    }

    # Determine the type stored at key.
    #
    # + key - The key of which the type needs to be obtained
    # + return - Type stored at key
    @display {label: "Type of a key"}
    remote function redisType(@display {label: "Key"} string key) 
                              returns @display {label: "Type of value of a key"} string | Error {
        return <string>java:toString(check redisType(self.datasource, java:fromString(key)));
    }

    // Connection commands

    # Ping the server.
    # 
    # + return - A string with the value `PONG` if the operation was successful
    @display {label: "Ping the server"}
    remote function ping() returns @display {label: "Result"} string | Error {
        return <string>java:toString(check ping(self.datasource));
    }

    # Authenticate to the server.
    #
    # + password - The password
    # + return - A string with the value `OK` if the operation was successful or `Error` if an Error occurs
    @display {label: "Authenticate to server"}
    remote function auth(@display {label: "Password"} string password) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check auth(self.datasource, java:fromString(password)));
    }

    # Echo the given string.
    #
    # + message - The message to be echo-ed
    # + return - The message itself if the operation was successful or `Error` if an Error occurs
    @display {label: "Echo a string"}
    remote function echo(@display {label: "String to echo"} string message) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check echo(self.datasource, java:fromString(message)));
    }

    # Stops the registered service.
    public function stop() {
        close(self.datasource);
    }
}

# Initialize redis client.
#
# + clientEndpointConfig - Client end point configuration
# + return - `Error` if Error occurs
function initClient(ClientEndpointConfiguration clientEndpointConfig) returns handle = @java:Method {
    'class: "org.ballerinalang.redis.endpoint.InitRedisClient"
} external;

# An internal function used by clients to shutdown the connection/connection pool(if pooling enabled).
function close(handle datasource) = @java:Method {
    'class: "org.ballerinalang.redis.endpoint.Close"
} external;

function append(handle datasource, handle key, handle value) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function bitCount(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function bitOpAnd(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function bitOpOr(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function bitOpNot(handle datasource, handle destination, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function bitOpXor(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function decr(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function decrBy(handle datasource, handle key, int value) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function get(handle datasource, handle key) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function getBit(handle datasource, handle key, int offset) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function getRange(handle datasource, handle key, int startPos, int end) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function getSet(handle datasource, handle key, handle value) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function incr(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function incrBy(handle datasource, handle key, int value) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function incrByFloat(handle datasource, handle key, float value) returns float | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

public function mGet(handle datasource, string[] keys) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

public function mSet(handle datasource, map<any> keyValueMap) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

public function mSetNx(handle datasource, map<any> keyValueMap) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function pSetEx(handle datasource, handle key, handle value, int expirationTime) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function set(handle datasource, handle key, handle value) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function setBit(handle datasource, handle key, int value, int offset) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function setEx(handle datasource, handle key, handle value, int expirationPeriodSeconds) returns handle | Error =
@java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function setNx(handle datasource, handle key, handle value) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function setRange(handle datasource, handle key, int offset, handle value) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function strln(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.StringActions"
} external;

function lPush(handle datasource, handle key, string[] values) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lPop(handle datasource, handle key) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lPushX(handle datasource, handle key, string[] values) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function bLPop(handle datasource, int timeOut, string[] keys) returns map<any> | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function bRPop(handle datasource, int timeOut, string[] keys) returns map<any> | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lIndex(handle datasource, handle key, int index) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lInsert(handle datasource, handle key, boolean before, handle pivot, handle value) returns int | Error =
@java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lLen(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lRange(handle datasource, handle key, int startPos, int stopPos) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lRem(handle datasource, handle key, int count, handle value) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lSet(handle datasource, handle key, int index, handle value) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function lTrim(handle datasource, handle key, int startPos, int stopPos) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function rPop(handle datasource, handle key) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function rPopLPush(handle datasource, handle src, handle destination) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function rPush(handle datasource, handle key, string[] values) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function rPushX(handle datasource, handle key, string[] values) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ListActions"
} external;

function sAdd(handle datasource, handle key, string[] values) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sCard(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sDiff(handle datasource, string[] keys) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sDiffStore(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sInter(handle datasource, string[] keys) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sInterStore(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sIsMember(handle datasource, handle key, handle value) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sMembers(handle datasource, handle key) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sMove(handle datasource, handle src, handle destination, handle member) returns boolean | Error =
@java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sPop(handle datasource, handle key, int count) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sRandMember(handle datasource, handle key, int count) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sRem(handle datasource, handle key, string[] members) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sUnion(handle datasource, string[] keys) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function sUnionStore(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SetActions"
} external;

function zAdd(handle datasource, handle key, map<any> memberScoreMap) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zCard(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zCount(handle datasource, handle key, float min, float max) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zIncrBy(handle datasource, handle key, float amount, handle member) returns float | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zInterStore(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zLexCount(handle datasource, handle destination, handle min, handle max) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRange(handle datasource, handle key, int min, int max) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRangeByLex(handle datasource, handle key, handle min, handle max) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRevRangeByLex(handle datasource, handle key, handle min, handle max) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRangeByScore(handle datasource, handle key, float min, float max) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRank(handle datasource, handle key, handle member) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRem(handle datasource, handle key, string[] members) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRemRangeByLex(handle datasource, handle key, handle min, handle max) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRemRangeByRank(handle datasource, handle key, int min, int max) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRemRangeByScore(handle datasource, handle key, float min, float max) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRevRange(handle datasource, handle key, int min, int max) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRevRangeByScore(handle datasource, handle key, float min, float max) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zRevRank(handle datasource, handle key, handle member) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zScore(handle datasource, handle key, handle member) returns float | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function zUnionStore(handle datasource, handle destination, string[] keys) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.SortedSetActions"
} external;

function del(handle datasource, string[] key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function exists(handle datasource, string[] key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function expire(handle datasource, handle key, int seconds) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function keys(handle datasource, handle pattern) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function move(handle datasource, handle key, int database) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function persist(handle datasource, handle key) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function pExpire(handle datasource, handle key, int timeMilliSeconds) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function pTtl(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function randomKey(handle datasource) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function rename(handle datasource, handle key, handle newName) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function renameNx(handle datasource, handle key, handle newName) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function sort(handle datasource, handle key) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function ttl(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function redisType(handle datasource, handle key) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.KeyActions"
} external;

function ping(handle datasource) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ConnectionActions"
} external;

function auth(handle datasource, handle password) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ConnectionActions"
} external;

function echo(handle datasource, handle message) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.ConnectionActions"
} external;

function hDel(handle datasource, handle key, string[] fields) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hExists(handle datasource, handle key, handle 'field) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hGet(handle datasource, handle key, handle 'field) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hGetAll(handle datasource, handle key) returns map<any> | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hIncrBy(handle datasource, handle key, handle 'field, int amount) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hIncrByFloat(handle datasource, handle key, handle 'field, float amount) returns float | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hLen(handle datasource, handle key) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hMGet(handle datasource, handle key, string[] fields) returns map<any> | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hMSet(handle datasource, handle key, map<any> fieldValueMap) returns handle | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hKeys(handle datasource, handle key) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hSet(handle datasource, handle key, handle 'field, handle value) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hSetNx(handle datasource, handle key, handle 'field, handle value) returns boolean | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hStrln(handle datasource, handle key, handle 'field) returns int | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

function hVals(handle datasource, handle key) returns string[] | Error = @java:Method {
    'class: "org.ballerinalang.redis.actions.HashActions"
} external;

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
    string clientName = "";
    boolean connectionPooling = false;
    boolean isClusterConnection = false;
    boolean ssl = false;
    boolean startTls = false;
    boolean verifyPeer = false;
    int database = -1;
    int connectionTimeout = -1;
|};

# The Client endpoint configuration for Redis databases.
#
# + host - The host of the Redis database
# + password - Password for the database connection
# + options - Properties for the connection configuration
public type ClientEndpointConfiguration record {|
    string host = "localhost";
    string password = "";
    Options options = {};
|};
