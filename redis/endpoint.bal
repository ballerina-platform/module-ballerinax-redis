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
    # + key - Key
    # + value - String value to be appended
    # + return - Length of the string after the operation
    @display {label: "Append value to key"}
    remote function append(@display {label: "Key"} string key, @display {label: "Value to be appended"} string value) 
                           returns @display {label: "Length of string after append"} int | Error {
        return append(self.datasource, java:fromString(key), java:fromString(value));
    }

    # Count set bits in a string.
    #
    # + key - Key
    # + return - Number of bits of the value
    @display {label: "Bits of a string value"}
    remote function bitCount(@display {label: "Key"} string key) 
                             returns @display {label: "Bits of string value"} int | Error {
        return bitCount(self.datasource, java:fromString(key));
    }

    # Perform bitwise AND between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform AND between
    # + return - Size of the string stored in the destination key, that is equal to the size of the longest input
    #            string
    @display {label: "Perform Bitwise AND"}
    remote function bitOpAnd(@display {label: "Result key"} string destination, 
                             @display {label: "Key array to perform AND"} string[] keys) 
                             returns @display {label: "Size of string"} int | Error {
        return bitOpAnd(self.datasource, java:fromString(destination), keys);
    }

    # Perform bitwise OR between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform OR between
    # + return - Size of the string stored in the destination key, that is equal to the size of the longest input
    #            string or `Error` if an Error occurs
    @display {label: "Perform Bitwise OR"}
    remote function bitOpOr(@display {label: "Result key"} string destination, 
                            @display {label: "Key array to perform OR"} string[] keys) 
                            returns @display {label: "Size of string"} int | Error {
        return bitOpOr(self.datasource, java:fromString(destination), keys);
    }

    # Perform bitwise NOT on a string.
    #
    # + destination - Result key of the operation
    # + key - Input key to perform NOT
    # + return - Size of the string stored in the destination key or `Error` if an Error occurs
    @display {label: "Perform Bitwise NOT"}
    remote function bitOpNot(@display {label: "Result key"} string destination, 
                             @display {label: "Key to perform NOT"} string key) 
                             returns @display {label: "Size of string"} int | Error {
        return bitOpNot(self.datasource, java:fromString(destination), java:fromString(key));
    }

    # Perform bitwise XOR between strings.
    #
    # + destination - Result key of the operation
    # + keys - Input keys to perform XOR between
    # + return - Size of the string stored in the destination key, that is equal to the size of the longest input
    #            string or `Error` if an Error occurs
    @display {label: "Perform Bitwise XOR"}
    remote function bitOpXor(@display {label: "Result key"} string destination, 
                             @display {label: "Key array to perform XOR"} string[] keys) 
                             returns @display {label: "Size of string"} int | Error {
        return bitOpXor(self.datasource, java:fromString(destination), keys);
    }

    # Decrement integer value of a key by one.
    #
    # + key - Key
    # + return - Value of key after the decrement
    @display {label: "Decrement value by one"}
    remote function decr(@display {label: "Key"} string key) returns @display {label: "Value of key"} int | Error {
        return decr(self.datasource, java:fromString(key));
    }

    # Decrement integer value of a key by the given number.
    #
    # + key - Key
    # + value - Value to be decremented
    # + return - Value of key after decrement or `Error` if an Error occurs
    @display {label: "Decrement value by number"}
    remote function decrBy(@display {label: "Key"} string key, @display {label: "Value to decrement"} int value) 
                           returns @display {label: "Value of key"} int | Error {
        return decrBy(self.datasource, java:fromString(key), value);
    }

    # Returns bit value at offset in the string value stored at key.
    #
    # + key - Key
    # + offset - Offset in string value
    # + return - Bit value stored at offset or `Error` if an Error occurs
    @display {label: "Get bit value at offset"}
    remote function getBit(@display {label: "Key"} string key, @display {label: "Offset"} int offset) 
                           returns @display {label: "Bit value"} int | Error {
        return getBit(self.datasource, java:fromString(key), offset);
    }

    # Get substring of string stored at a key.
    #
    # + key - Key
    # + startPos - Starting point of substring
    # + end - End point of substring
    # + return - Substring or `Error` if an Error occurs
    @display {label: "Get substring of string"}
    remote function getRange(@display {label: "Key"} string key, @display {label: "Start position"} int startPos, 
                             @display {label: "End position"} int end) 
                             returns @display {label: "Substring"} string | Error {
        return <string>java:toString(check getRange(self.datasource, java:fromString(key), startPos, end));
    }

    # Set string value of key and return its existing value.
    #
    # + key - Key
    # + value - Value to be set
    # + return - Existing value stored at key, or nil when key does not exist or `Error` if an Error occurs
    @display {label: "Set value to key and return old value"}
    remote function getSet(@display {label: "Key"} string key, @display {label: "New value"} string value) 
                           returns @display {label: "Old value"} string | Error {
        return <string>java:toString(check getSet(self.datasource, java:fromString(key), java:fromString(value)));
    }

    # Get value of key.
    #
    # + key - Key
    # + return - Value of key, or nil when key does not exist or `Error` if an Error occurs
    @display {label: "Get value of a key"}
    remote function get(@display {label: "Key"} string key) returns @display {label: "Value of key"} string | Error {
        return <string>java:toString(check get(self.datasource, java:fromString(key)));
    }

    # Increment integer value of a key by one.
    #
    # + key - Key
    # + return - Value of key after increment
    @display {label: "Increment value by one"}
    remote function incr(@display {label: "Key"} string key) returns @display {label: "Value of key"} int | Error {
        return incr(self.datasource, java:fromString(key));
    }

    # Increment integer value of key by the given amount.
    #
    # + key - Key
    # + value - Amount to increment
    # + return - Value of key after increment
    @display {label: "Increment value by number"}
    remote function incrBy(@display {label: "Key"} string key, @display {label: "Value to increment"} int value) 
                           returns @display {label: "Value of key"} int | Error {
        return incrBy(self.datasource, java:fromString(key), value);
    }

    # Increment integer value of key by the given float.
    #
    # + key - Key
    # + value - Amount to increment
    # + return - Value of key after increment
    @display {label: "Increment value by float"}
    remote function incrByFloat(@display {label: "Key"} string key, 
                                @display {label: "Float value to increment"} float value) 
                                returns @display {label: "Float value of key"} float | Error {
        return incrByFloat(self.datasource, java:fromString(key), value);
    }

    # Get values of all given keys.
    #
    # + keys - Keys of which values need to be retrieved
    # + return - Array of values at specified keys
    @display {label: "Get list of values for keys"}
    remote function mGet(@display {label: "Array of Keys"} string[] keys) 
                         returns @display {label: "Array of values"} string[] | Error {
        return mGet(self.datasource, keys);
    }

    # Set multiple keys to multiple values.
    #
    # + keyValueMap - Map of key-value pairs to be set
    # + return - String with value `OK` if the operation was successful
    @display {label: "Set multiple values for keys"}
    remote function mSet(@display {label: "Map of key value pairs"} map<any> keyValueMap) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check mSet(self.datasource, keyValueMap));
    }

    # Set multiple keys to multiple values, only if none of the keys exist.
    #
    # + keyValueMap - Map of key-value pairs to be set
    # + return - True if the operation was successful, false if it failed
    @display {label: "Set multiple values if keys not exists only"}
    remote function mSetNx(@display{label: "Map of key value pairs"} map<any> keyValueMap) 
                           returns @display{label: "Result"} boolean | Error {
        return mSetNx(self.datasource, keyValueMap);
    }

    # Set value and expiration in milliseconds of a key.
    #
    # + key - Key
    # + value - Value to be set
    # + expirationTime - Expiration time in milli seconds
    # + return - `OK` if successful or `Error` if an Error occurs
    remote function pSetEx(@display {label: "Key"} string key, @display {label: "Value"} string value,
                           @display {label: "Time to expire(ms)"} int expirationTime) 
                           returns @display {label: "Result"} string | Error {
        return <string>java:toString(check pSetEx(self.datasource, java:fromString(key), java:fromString(value),
        expirationTime));
    }

    # Set the value of a key.
    #
    # + key - Key
    # + value - Values
    # + return - `OK` if successful
    @display {label: "Set value to key"}
    remote function set(@display {label: "Key"} string key, @display {label: "Value of key"} string value) 
                        returns @display {label: "Result"} string | Error {
        return <string>java:toString(check set(self.datasource, java:fromString(key), java:fromString(value)));
    }

    # Sets or clears the bit at offset in the string value stored at key.
    #
    # + key - Key
    # + value - Value to be set
    # + offset - Offset at which the value should be set
    # + return - Original bit value stored at offset or `Error` if an Error occurs
    @display {label: "Set bit value from offset"} 
    remote function setBit(@display {label: "Key"} string key, @display {label: "Value"} int value, 
                           @display {label: "Offset"} int offset) 
                           returns @display {label: "Old bit value at offset"} int | Error {
        return setBit(self.datasource, java:fromString(key), value, offset);
    }

    # Set the value and expiration of a key.
    #
    # + key - Key
    # + value - Value to be set
    # + expirationPeriodSeconds - Expiration time to be set, in seconds
    # + return - On success `OK` or `Error` if an Error occurs
    @display {label: "Set value and expiration time"} 
    remote function setEx(@display {label: "Key"} string key, @display {label: "Value"} string value, 
                          @display {label: "Time to expire(s)"} int expirationPeriodSeconds) 
                          returns @display {label: "Result"} string | Error {
        return <string>java:toString(check setEx(self.datasource, java:fromString(key), java:fromString(value),
        expirationPeriodSeconds));

    }

    # Set value of a key, only if key does not exist.
    #
    # + key - Key
    # + value - Value to be set
    # + return - `True` if exist `False` if not or `Error` if an Error occurs
    @display {label: "Set value for key not existed"} 
    remote function setNx(@display {label: "Key"} string key, @display {label: "Value"} string value) 
                          returns @display {label: "Result"} boolean | Error {
        return setNx(self.datasource, java:fromString(key), java:fromString(value));
    }

    # Overwrite part of string at key starting at the specified offset.
    #
    # + key - Key
    # + offset - Offset at which the value should be set
    # + value - Value to be set
    # + return - Length of the string after it was modified or `Error` if an Error occurs
    @display {label: "Overwrite value from offset"} 
    remote function setRange(@display {label: "Key"} string key, 
                             @display {label: "Start position of overwrite"} int offset, 
                             @display {label: "Value"} string value) 
                             returns @display {label: "Length of string resulted"} int | Error {
        return setRange(self.datasource, java:fromString(key), offset, java:fromString(value));
    }

    # Get length of value stored in a key.
    #
    # + key - Key
    # + return - Length of string at key, or 0 when key does not exist or `Error` if an Error occurs
    @display {label: "Get string length"} 
    remote function strln(@display {label: "Key"} string key) 
                          returns @display {label: "Length of string at key"} int | Error {
        return strln(self.datasource, java:fromString(key));
    }

    // list operations

    # Prepend one or multiple values to list.
    #
    # + key - Key
    # + values - Values to be prepended
    # + return - Length of list after the push operation(s) or `Error` if an Error occurs
    @display {label: "Push value to list"}
    remote function lPush(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                          returns @display {label: "Length of list"} int | Error {
        return lPush(self.datasource, java:fromString(key), values);
    }

    # Remove and get the first element in a list.
    #
    # + key - Key
    # + return - Value of the first element, or nil when key does not exist or `Error` if an Error occurs
    @display {label: "Pop value from list"}
    remote function lPop(@display {label: "Key"} string key) 
                         returns @display {label: "Value of first element popped"} string | Error {
        return <string>java:toString(check lPop(self.datasource, java:fromString(key)));
    }

    # Prepend one or multiple values to a list, only if the list exists.
    #
    # + key - Key
    # + values - Values to be prepended
    # + return - Length of the list after the push operation(s)
    @display {label: "Push value to list if exists"}
    remote function lPushX(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                           returns @display {label: "Length of list"} int | Error {
        return lPushX(self.datasource, java:fromString(key), values);
    }

    # Remove and get the first element in a list, or block until one is available.
    #
    # + timeOut - Timeout in seconds
    # + keys - Keys
    # + return - `Nil` when no element could be popped and the timeout expired. A map containing one item, with the
    #         key being  the name of the key where an element was popped and the second element  being the value of the
    #         popped element, or `Error` if an Error occurs
    @display {label: "Pop first value from list and block on element not exists"}
    remote function bLPop(@display {label: "Timeout(s)"} int timeOut, @display {label: "Array of keys"} string[] keys) 
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
    @display {label: "Pop last value from list and block on element not exists"}
    remote function bRPop(@display {label: "Timeout(s)"} int timeOut, @display {label: "Array of keys"} string[] keys) 
                          returns @display {label: "Key-value pair"} map<any> | Error {
        return bRPop(self.datasource, timeOut, keys);
    }

    # Get an element from list by its index.
    #
    # + key - Key
    # + index - Index from which the element should be retrieved
    # + return - Value at the given index
    @display {label: "Get value in list by index"}
    remote function lIndex(@display {label: "Key"} string key, @display {label: "Index"} int index) 
                           returns @display {label: "Value at index"} string | Error {
        return <string>java:toString(check lIndex(self.datasource, java:fromString(key), index));
    }

    # Insert an element before or after another element in a list.
    #
    # + key - Key
    # + before - Boolean value representing Whether element should be inserted before or after the pivot
    # + pivot - Pivot position
    # + value - Value to insert
    # + return - Length of the list after the insert operation, or -1 when the value pivot not found, or `Error` if
    #           an Error occurs
    @display {label: "Insert value in list at specific position"}
    remote function lInsert(@display {label: "Key"} string key, 
                            @display {label: "Insert before or not"} boolean before, 
                            @display {label: "Place to insert"} string pivot, @display {label: "Value"} string value) 
                            returns @display {label: "Length of list after insertion"} int | Error {
        return lInsert(self.datasource, java:fromString(key), before, java:fromString(pivot), java:fromString(value));
    }

    # Get length of a list.
    #
    # + key - Key
    # + return - Length of list at key or `Error` if an Error occurs
    @display {label: "Get length of list at key"}
    remote function lLen(@display {label: "Key"} string key) returns @display {label: "Length of list"} int | Error {
        return lLen(self.datasource, java:fromString(key));
    }

    # Get a range of elements from a list.
    #
    # + key - Key
    # + startPos - Begining index of the range
    # + stopPos - Last index of the range
    # + return - Array of elements in the specified range or `Error` if an Error occurs
    @display {label: "Get range of elements of list at key"}
    remote function lRange(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                           @display {label: "End Position"} int stopPos) 
                           returns @display {label: "Array of elements in range"} string[] | Error {
        return lRange(self.datasource, java:fromString(key), startPos, stopPos);
    }

    # Remove elements from list.
    #
    # + key - Key
    # + count - Number of elements to be removed
    # + value - Value which the elements to be removed should be equal to
    # + return - Number of elements removed or `Error` if an Error occurs
    @display {label: "Remove elements from list at key"}
    remote function lRem(@display {label: "Key"} string key, @display {label: "Number of members"} int count, 
                         @display {label: "Value"} string value) 
                         returns @display {label: "Number of members removed"} int | Error {
        return lRem(self.datasource, java:fromString(key), count, java:fromString(value));
    }

    # Set the value of an element in a list by its index.
    #
    # + key - Key of the list
    # + index - Index of the element of which the value needs to be set
    # + value - Value to be set
    # + return - String with the value `OK` if the operation was successful or `Error` if an Error occurs
    @display {label: "Set value of element in list at index"}
    remote function lSet(@display {label: "Key"} string key, @display {label: "Index"} int index, 
                         @display {label: "Value"} string value) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check lSet(self.datasource, java:fromString(key), index, java:fromString(value)));
    }

    # Trim list to the specified range.
    #
    # + key - Key of the list
    # + startPos - Starting index of the range
    # + stopPos - End index of the range
    # + return - String with the value `OK` if the operation was successful
    @display {label: "Trim list to a range"}
    remote function lTrim(@display {label: "Key"} string key, @display {label: "Start Position"} int startPos, 
                          @display {label: "End Position"} int stopPos) 
                          returns @display {label: "Result"} string | Error {
        return <string>java:toString(check lTrim(self.datasource, java:fromString(key), startPos, stopPos));
    }

    # Remove and get the last element in a list.
    #
    # + key - Key of the list
    # + return - Value of the last element, or `nil` when key does not exist or `Error` if an Error occurs
    @display {label: "Pop last element in list"}
    remote function rPop(@display {label: "Key"} string key) 
                         returns @display {label: "Value of popped element"} string | Error {
        return <string>java:toString(check rPop(self.datasource, java:fromString(key)));
    }

    # Remove the last element in a list, append it to another list and return it.
    #
    # + src - Source key
    # + destination - Destination key
    # + return - Element being popped and pushed or `Error` if an Error occurs
    @display {label: "Move last element in list to another list"}
    remote function rPopLPush(@display {label: "Current key of element"} string src, 
                              @display {label: "Destination key"} string destination) 
                              returns @display {label: "Value of element"} string | Error {
        return <string>java:toString(check rPopLPush(self.datasource, java:fromString(src),
        java:fromString(destination)));
    }

    # Append one or multiple values to a list.
    #
    # + key - Key of the list
    # + values - Array of values to be appended
    # + return - Length of the list after the push operation or `Error` if an Error occurs
    @display {label: "Append values to list"}
    remote function rPush(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                          returns @display {label: "Length of list"} int | Error {
        return rPush(self.datasource, java:fromString(key), values);
    }

    # Append one or multiple values to a list, only if the list exists.
    #
    # + key - Key of the list
    # + values - Array of values to be appended
    # + return - Length of the list after the push operation or `Error` if an Error occurs
    @display {label: "Append values to list if exists"}
    remote function rPushX(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                           returns @display {label: "Length of list"} int | Error {
        return rPushX(self.datasource, java:fromString(key), values);
    }

    // Set Commands

    # Add one or more members to a set.
    #
    # + key - Key of the set
    # + values - Array of values to be added
    # + return - Number of elements that were added to the set, not including all the elements which were
    #            already present in the set, or `Error` if an Error occurs
    @display {label: "Add members to set"}
    remote function sAdd(@display {label: "Key"} string key, @display {label: "Array of values"} string[] values) 
                         returns @display {label: "Number of elements added"} int | Error {
        return sAdd(self.datasource, java:fromString(key), values);
    }

    # Get the number of members in a set
    #
    # + key - Key of the set
    # + return - Cardinality (number of elements) of the set or `Error` if an Error occurs
    @display {label: "Get number of members in set"}
    remote function sCard(@display {label: "Key"} string key) 
                          returns @display {label: "Number of elements in a set"} int | Error {
        return sCard(self.datasource, java:fromString(key));
    }

    # Return set resulting from the difference between the first set and all the successive sets
    #
    # + keys - The keys of the sets
    # + return - An array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Difference of set"}
    remote function sDiff(@display {label: "Array of keys"} string[] keys) 
                          returns @display {label: "Array of members in result set"} string[] | Error {
        return sDiff(self.datasource, keys);
    }

    # Obtain the set resulting from the difference between the first set and all the successive.
    # sets and store at the provided destination.
    #
    # + destination - Destination key of the resulting set
    # + keys - Keys of the sets to find the difference of
    # + return - Number of members in the resulting set or `Error` if an Error occurs
    @display {label: "Set difference of set to specific destination"}
    remote function sDiffStore(@display {label: "Destination key"} string destination, 
                               @display {label: "Array of keys"} string[] keys) 
                               returns @display {label: "Number of members in result set"} int | Error {
        return sDiffStore(self.datasource, java:fromString(destination), keys);
    }

    # Return the intersection of the provided sets.
    #
    # + keys - Keys of the sets to be intersected
    # + return - Array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Intersections of sets"}
    remote function sInter(@display {label: "Array of keys"} string[] keys) 
                           returns @display {label: "Array of members in result set"} string[] | Error {
        return sInter(self.datasource, keys);
    }

    # Obtain the intersection of the provided sets and store at the provided destination.
    #
    # + destination - Destination key of the resulting set
    # + keys - Keys of the sets to be intersected
    # + return - Array of members of the resulting set or `Error` if an Error occurs
    @display {label: "Get intersections of sets to key"}
    remote function sInterStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Array of members"} int | Error {
        return sInterStore(self.datasource, java:fromString(destination), keys);
    }

    # Determine if a given value is a member of a set.
    #
    # + key - Key of the set
    # + value - Value of a key
    # + return - boolean true/false depending on whether the value is a member of the set or not, or `Error` if an Error
    #            occurs
    @display {label: "Check value in a set"}
    remote function sIsMember(@display {label: "Key"} string key, @display {label: "Value of a key"} string value) 
                              returns @display {label: "Result"} boolean | Error {
        return sIsMember(self.datasource, java:fromString(key), java:fromString(value));
    }

    # Get all members in a set.
    #
    # + key - Key of the set
    # + return - Array of all members in the set or `Error` if an Error occurs
    @display {label: "Get all members in set"}
    remote function sMembers(@display {label: "Key"} string key) 
                             returns @display {label: "Array of members in a set"} string[] | Error {
        return sMembers(self.datasource, java:fromString(key));
    }

    # Move a member from one set to another.
    #
    # + src - Source key
    # + destination - Destination key
    # + member - Member to be moved
    # + return - `True` if the element is moved. `false` if the element is not a member of source and no
    #             operation was performed or `Error` if an Error occurs
    @display {label: "Move member in set to another"}
    remote function sMove(@display {label: "Source key"} string src, 
                          @display {label: "Destination key"} string destination, 
                          @display {label: "Member in a set"} string member) 
                          returns @display {label: "Result"} boolean | Error {
        return sMove(self.datasource, java:fromString(src), java:fromString(destination), java:fromString(member));
    }


    # Remove and return a random member from a set.
    #
    # + key - Source key
    # + count - Number of members to pop
    # + return - Array of removed elements or `nil` if key does not exist or `Error` if an Error occurs
    @display {label: "Pop random member in set"}
    remote function sPop(@display {label: "Key"} string key, @display {label: "Number of members"} int count) 
                         returns @display {label: "Array of removed elements"} string[] | Error {
        return sPop(self.datasource, java:fromString(key), count);
    }

    # Get one or multiple random members from a set.
    #
    # + key - Key of the set
    # + count - Number of members to obtain
    # + return - Array of the randomly selected elements, or `nil` when key does not exist or `Error` if an Error occurs
    @display {label: "Get random member in set"}
    remote function sRandMember(@display {label: "Key"} string key, @display {label: "Number of members"} int count) 
                                returns @display {label: "Array of elements"} string[] | Error {
        return sMembers(self.datasource, java:fromString(key));
    }

    # Remove one or more members from a set.
    #
    # + key - Key of the set
    # + members - Array of members to remove
    # + return - Number of members that were removed from the set, not including non existing members or `Error` if
    #            an Error occurs
    @display {label: "Remove multiple members in set"}
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
    # + destination - Destination key of the resulting set
    # + keys - Array of keys of sets
    # + return - Number of members of the resulting set or `Error` if an Error occurs
    @display {label: "Set union of multiple sets"}
    remote function sUnionStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Number of members in result set"} int | Error {
        return sUnionStore(self.datasource, java:fromString(destination), keys);
    }

    // Sorted set Commands

    # Add one or more members to a sorted set, or update its score if it already exist.
    #
    # + key - Key of the sorted set
    # + memberScoreMap - Map of members and corresponding scores
    # + return - Number of elements that were added to the sorted set, not including all the elements which were
    #            already present in the set for which the score was updated, or `Error` if an Error occurs
    @display {label: "Add members to sorted set"} 
    remote function zAdd(@display {label: "Key"} string key, 
                         @display {label: "Member-value pairs"} map<any> memberScoreMap) 
                         returns @display {label: "Number of members added"} int | Error {
        return zAdd(self.datasource, java:fromString(key), memberScoreMap);
    }

    # Get the number of members in a sorted set.
    #
    # + key - Key of the sorted set
    # + return - Cardinality (number of elements) of the sorted set or `Error` if an Error occurs
    @display {label: "Get number of members in sorted set"}
    remote function zCard(@display {label: "Key"} string key) 
                          returns @display {label: "Number of members"} int | Error {
        return zCard(self.datasource, java:fromString(key));
    }

    # Count the members in a sorted set with scores within the given range.
    #
    # + key - Key of the sorted set
    # + min - Minimum score of the range
    # + max - Maximum score of the range
    # + return - Number of elements in the specified score range or `Error` if an Error occurs
    @display {label: "Get members in range of sorted set"}
    remote function zCount(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                           @display {label: "Maximum value"} float max) 
                           returns @display {label: "Number of members between range"} int | Error {
        return zCount(self.datasource, java:fromString(key), min, max);
    }

    # Increment the score of a member in a sorted set.
    #
    # + key - Key of the sorted set
    # + amount - Amount to increment
    # + member - Member whose score to be incremented
    # + return - New score of the member or `Error` if an Error occurs
    @display {label: "Increment member in sorted set"}
    remote function zIncrBy(@display {label: "Key"} string key, @display {label: "Amount to increment"} float amount, 
                            @display {label: "Member to increment"} string member) 
                            returns @display {label: "Incremented score of member"} float | Error {
        return zIncrBy(self.datasource, java:fromString(key), amount, java:fromString(member));
    }

    # Intersect multiple sorted sets and store the resulting sorted set in a new key.
    #
    # + destination - Destination key of the resulting sorted set
    # + keys - Keys of the sorted sets to be intersected
    # + return - Number of elements in the resulting sorted set or `Error` if an Error occurs
    @display {label: "Get number of members in intersection of sorted sets"}
    remote function zInterStore(@display {label: "Destination key"} string destination, 
                                @display {label: "Array of keys"} string[] keys) 
                                returns @display {label: "Number of members"} int | Error {
        return zInterStore(self.datasource, java:fromString(destination), keys);
    }

    # Count the members in a sorted set within the given lexicographical range.
    #
    # + key - Key of the sorted set
    # + min - Minimum lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Number of elements in the specified lexicographical value range or `Error` if an Error occurs
    @display {label: "Get number of members in lexicographical range"}
    remote function zLexCount(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                              @display {label: "Maximum value"} string max) 
                              returns @display {label: "Number of members"} int | Error {
        return zLexCount(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Return a range of members in a sorted set, by index.
    #
    # + key - Key of the sorted set
    # + min - Minimum index of the range
    # + max - Maximum index of the range
    # + return - Range of members in a sorted set, by index, or `Error` if an Error occurs
    @display {label: "Get members by index range in sorted set"}
    remote function zRange(@display {label: "Key"} string key, @display {label: "Minimum index"} int min, 
                           @display {label: "Maximum index"} int max) 
                           returns @display {label: "Array of members"} string[] | Error {
        return zRange(self.datasource, java:fromString(key), min, max);
    }

    # Return a range of members in a sorted set, by lexicographical range from lowest to highest.
    #
    # + key - Key of the sorted set
    # + min - Minimum lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Array of members in the specified lexicographical value range ordered from lowest to highest or `Error`
    #            if an Error occurs
    @display {label: "Get members by lexicographical range from sorted set"}
    remote function zRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                                @display {label: "Maximum value"} string max) 
                                returns @display {label: "Array of members"} string[] | Error {
        return zRangeByLex(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Return a range of members in a sorted set, by lexicographical range ordered from highest to
    # lowest.
    #
    # + key - Key of the sorted set
    # + min - Lexicographical value of the range
    # + max - Maximum lexicographical value of the range
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
    # + key - Key of sorted set
    # + min - Minimum score of range
    # + max - Maximum score of range
    # + return - Array of members in the specified score range ordered from lowest to highest or `Error` if an Error
    #            occurs
    @display {label: "Get members by score range in a sorted set"}
    remote function zRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                                  @display {label: "Maximum value"} float max) 
                                  returns @display {label: "Array of members"} string[] | Error {
        return zRangeByScore(self.datasource, java:fromString(key), min, max);
    }

    # Determine index of a member in a sorted set.
    #
    # + key - Key of the sorted set
    # + member - Member of which the index needs to be obtained
    # + return - Index of the member or `Error` if an Error occurs
    @display {label: "Get index of member in sorted set"}
    remote function zRank(@display {label: "Key"} string key, @display {label: "Value of member"} string member) 
                          returns @display {label: "Index"} int | Error {
        return zRank(self.datasource, java:fromString(key), java:fromString(member));
    }

    # Remove one or more members from a sorted set
    #
    # + key - Key of the sorted set
    # + members - Members to be removed
    # + return - Number of members removed from the sorted set, not including non existing members or `Error` if an
    #            Error occurs
    @display {label: "Remove members from sorted set"}
    remote function zRem(@display {label: "Key"} string key, @display {label: "Array of members"} string[] members) 
                         returns @display {label: "Number of members removed"} int | Error {
        return zRem(self.datasource, java:fromString(key), members);
    }


    # Remove all members in a sorted set between the given lexicographical range.
    #
    # + key - Key of the sorted set
    # + min - Minimum lexicographical value of the range
    # + max - Maximum lexicographical value of the range
    # + return - Number of members removed from the sorted set or `Error` if an Error occurs
    @display {label: "Remove members between range in lexicographical range"}
    remote function zRemRangeByLex(@display {label: "Key"} string key, @display {label: "Minimum value"} string min, 
                                   @display {label: "Maximum value"} string max) 
                                   returns @display {label: "Number of members removed"} int | Error {
        return zRemRangeByLex(self.datasource, java:fromString(key), java:fromString(min), java:fromString(max));
    }

    # Remove all members in a sorted set within the given indices.
    #
    # + key - Key of the sorted set
    # + min - Minimum index of the range
    # + max - Maximum index of the range
    # + return - Number of members removed from the sorted set or `Error` if an Error occurs
    @display {label: "Number of members removed between indexes"}
    remote function zRemRangeByRank(@display {label: "Key"} string key, @display {label: "Minimum index"} int min, 
                                    @display {label: "Maximum index"} int max) 
                                    returns @display {label: "Number of members removed"} int | Error {
        return zRemRangeByRank(self.datasource, java:fromString(key), min, max);
    }

    # Remove all members in a sorted set within the given scores.
    #
    # + key - Key of the sorted set
    # + min - Minimum score of the range
    # + max - Maximum score of the range
    # + return - Number of members removed from the sorted set or `Error` if an Error occurs
    @display {label: "Number of members removed between range of scores"}
    remote function zRemRangeByScore(@display {label: "Key"} string key, @display {label: "Minimum value"} float min, 
                                     @display {label: "Maximum value"} float max) 
                                     returns @display {label: "Remove members between range from sorted set"} int | Error {
        return zRemRangeByScore(self.datasource, java:fromString(key), min, max);
    }

    # Return a range of members in a sorted set, by index, ordered highest to lowest.
    #
    # + key - Key of the sorted set
    # + min - Minimum index of the range
    # + max - Maximum index of the range
    # + return - Number of elements in the specified index range or `Error` if an Error occurs
    @display {label: "Get members between range of indexes"}
    remote function zRevRange(@display {label: "Key"} string key, @display {label: "Minimum index"} int min, 
                              @display {label: "Maximum index"} int max) 
                              returns @display {label: "Array of members"} string[] | Error {
        return zRevRange(self.datasource, java:fromString(key), min, max);
    }

    # Return a range of members in a sorted set, by score from highest to lowest.
    #
    # + key - Key of the sorted set
    # + min - Minimum score of the range
    # + max - Maximum score of the range
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
    # + key - Key of the sorted set
    # + member - Member of which the index needs to be obtained
    # + return - Index of the member or `Error` if an Error occurs
    @display {label: "Get member index in sorted set"}
    remote function zRevRank(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                             returns @display {label: "Index"} int | Error {
        return zRevRank(self.datasource, java:fromString(key), java:fromString(member));
    }

    # Determine the score of a member in a sorted set
    #
    # + key - Key of the sorted set
    # + member - Member of which the score needs to be obtained
    # + return - Score of the member or `Error` if an Error occurs
    @display {label: "Get member score in sorted set"}
    remote function zScore(@display {label: "Key"} string key, @display {label: "Member"} string member) 
                           returns @display {label: "Score"} float | Error {
        return zScore(self.datasource, java:fromString(key), java:fromString(member));
    }

    # Return the union of multiple sorted sets
    #
    # + destination - Destination key of the resulting set
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
    # + key - Key of the hash
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
    # + key - Key of the hash
    # + field - Array of fields to be deleted
    # + return - Boolean `true` if the hash contains the field. boolean false if the hash does not contain
    #            field or key does not exist or `Error` if an Error occurs
    @display {label: "Check existance of hash fields"}
    remote function hExists(@display {label: "Key"} string key, @display {label: "Field"} string 'field) 
                            returns @display {label: "Result"} boolean | Error {
        return hExists(self.datasource, java:fromString(key), java:fromString('field));
    }

    # Get the value of a hash field.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + return - Value of the field or `Error` if an Error occurs
    @display {label: "Get value of the hash field"} 
    remote function hGet(@display {label: "Key"} string key, @display {label: "Field"} string 'field) 
                         returns @display {label: "Value of field"} string | Error {
        return <string>java:toString(check hGet(self.datasource, java:fromString(key), java:fromString('field)));
    }

    # Get the all values of a hash.
    #
    # + key - Key of the hash
    # + return - Map of field-value pairs or `Error` if an Error occurs
    @display {label: "Get all values of hash"} 
    remote function hGetAll(@display {label: "Key"} string key) 
                            returns @display {label: "Field-value pairs"} map<any> | Error {
        return hGetAll(self.datasource, java:fromString(key));
    }

    # Increment the integer value of a hash field by the given number.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + amount - Amount to increment
    # + return - Value of the field or `Error` if an Error occurs
    @display {label: "Increment integer value in hash"} 
    remote function hIncrBy(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                            @display {label: "Value to increment"} int amount) 
                            returns @display {label: "Incremented value"} int | Error {
        return hIncrBy(self.datasource, java:fromString(key), java:fromString('field), amount);
    }

    # Increment the float value of a hash field by the given number.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + amount - Amount to increment
    # + return - Value of the field or `Error` if an Error occurs
    @display {label: "Increment integer value in hash"} 
    remote function hIncrByFloat(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                                 @display {label: "Value to increment"} float amount) 
                                 returns @display {label: "Incremented value"} float | Error {
        return hIncrByFloat(self.datasource, java:fromString(key), java:fromString('field), amount);
    }

    # Get all the fields in a hash.
    #
    # + key - Key of the hash
    # + return - Array of hash fields or `Error` if an Error occurs
    @display {label: "Get all fields in hash"} 
    remote function hKeys(@display {label: "Key"} string key) 
                          returns @display {label: "Array of hash fields"} string[] | Error {
        return hKeys(self.datasource, java:fromString(key));
    }

    # Get the number of fields in a hash.
    #
    # + key - Key of the hash
    # + return - Number of fields or `Error` if an Error occurs
    @display {label: "Get number of fields in hash"} 
    remote function hLen(@display {label: "Key"} string key) 
                         returns @display {label: "Number of hash fields"} int | Error {
        return hLen(self.datasource, java:fromString(key));
    }

    # Get the values of all the given hash fields.
    #
    # + key - Key of the hash
    # + fields - Array of hash fields
    # + return - Map of field-value pairs or `Error` if an Error occurs
    @display {label: "Get values of selected fields in hash"} 
    remote function hMGet(@display {label: "Key"} string key, @display {label: "Array of fields"} string[] fields) 
                          returns @display {label: "Field-value pairs"} map<any> | Error {
        return hMGet(self.datasource, java:fromString(key), fields);
    }

    # Set multiple hash fields to multiple values.
    #
    # + key - Key of the hash
    # + fieldValueMap - Map of field-value pairs
    # + return - String with the value `OK` if the operation was successful, or `Error` if an Error occurs
    @display {label: "Add values to hash fields"} 
    remote function hMSet(@display {label: "Key"} string key, 
                          @display {label: "Field-value pairs"} map<any> fieldValueMap) 
                          returns @display {label: "Result"} string | Error {
        return <string>java:toString(check hMSet(self.datasource, java:fromString(key), fieldValueMap));
    }

    # Set the string value of a hash field.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + value - Value to be set to the field
    # + return - Boolean `true` if field is a new field in the hash and value was set. boolean false if
    #         field already exists in the hash and the value was updated, or `Error` if an Error occurs
    @display {label: "Add value to hash field"} 
    remote function hSet(string key, string 'field, string value) returns boolean | Error {
        return hSet(self.datasource, java:fromString(key), java:fromString('field), java:fromString(value));
    }

    # Set the string value of a hash field, only if the field does not exist.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + value - Value to be set to the field
    # + return - Boolean `true` if field is a new field in the hash and value was set. boolean false if
    #            field already exists in the hash and no operation was performed, or `Error` if an Error occurs
    @display {label: "Add value to hash for non-existance field"} 
    remote function hSetNx(@display {label: "Key"} string key, @display {label: "Field"} string 'field, 
                           @display {label: "Value"} string value) returns @display {label: "Result"} boolean | Error {
        return hSetNx(self.datasource, java:fromString(key), java:fromString('field), java:fromString(value));
    }

    # Get the string length of the field value in a hash.
    #
    # + key - Key of the hash
    # + field - Field in the hash
    # + return - Length of the field value, or 0 when field is not present in the hash or key does
    #            not exist at all, or `Error` if an Error occurs
    @display {label: "Get string length for value"}
    remote function hStrln(@display {label: "Key"} string key, @display {label: "Field in the hash"} string 'field) 
                           returns @display {label: "String length of value"} int | Error {
        return hStrln(self.datasource, java:fromString(key), java:fromString('field));
    }

    # Get all the values in a hash.
    #
    # + key - Key of the hash
    # + return - Array of values in the hash, or an empty array when key does not exist or `Error` if an Error occurs
    @display {label: "Get values in stored hash"}
    remote function hVals(@display {label: "Key"} string key) 
                          returns @display {label: "Array of values"} string[] | Error {
        return hVals(self.datasource, java:fromString(key));
    }

    // Key Commands

    # Delete one or more keys.
    #
    # + keys - Key to be deleted
    # + return - Number of keys that were removed
    @display {label: "Delete bulk of keys"}
    remote function del(@display {label: "Array of keys"} string[] keys) 
                        returns @display {label: "Number of keys deleted"} int | Error {
        return del(self.datasource, keys);
    }

    # Determine how many keys exist.
    #
    # + keys - Keys of which existence to be found out
    # + return - Number of existing keys or `Error` if an Error occurs
    @display {label: "Check keys exists"}
    remote function exists(@display {label: "Array of keys"} string[] keys) 
                           returns @display {label: "Number of keys exists"} int | Error {
        return exists(self.datasource, keys);
    }

    # Set a key's time to live in seconds.
    #
    # + key - Keys of which expiry time to be set
    # + seconds - Expiry in seconds
    # + return - Boolean `true` if the timeout was set. false if key does not exist or the timeout could not be set or
    # `Error` if an Error occurs
    @display {label: "Set time to live in seconds"}
    remote function expire(@display {label: "Key"} string key, @display {label: "Time in seconds"} int seconds) 
                           returns @display {label: "Result"} boolean | Error {
        return expire(self.datasource, java:fromString(key), seconds);
    }

    # Find all keys matching the given pattern.
    #
    # + pattern - Pattern to match
    # + return - Array of keys matching the given pattern or `Error` if an Error occurs
    @display {label: "Get keys matching a pattern"}
    remote function keys(@display {label: "Pattern string"} string pattern) 
                         returns @display {label: "Array of keys"} string[] | Error {
        return keys(self.datasource, java:fromString(pattern));
    }

    # Move a key to another database.
    #
    # + key - Key to be moved
    # + database - Database to which the key needs to be moved
    # + return - Boolean true if key was succesfully moved, boolean false otherwise or `Error` if an Error occurs
    @display {label: "Move a key"}
    remote function move(@display {label: "Key"} string key, 
                         @display {label: "Database to which key should be moved"} int database) 
                         returns @display {label: "Result"} boolean | Error {
        return move(self.datasource, java:fromString(key), database);
    }

    # Remove the expiration from a key.
    #
    # + key - Key of which expiry time should be removed
    # + return - Boolean `true` if the timeout was removed. boolean `false` if key does not exist or does not have
    #            an associated timeout, or `Error` if an Error occurs
    @display {label: "Remove key timeout"}
    remote function persist(@display {label: "Key"} string key) returns @display {label: "Result"} boolean | Error {
        return persist(self.datasource, java:fromString(key));
    }

    # Set a key's time to live in milliseconds.
    #
    # + key - Key of which expiry time should be removed
    # + timeMilliSeconds - Expiry time in milli seconds
    # + return - Boolean `true` if the timeout was set. boolean false if key does not exist or the timeout could not
    #         be set, or `Error` if an Error occurs
    @display {label: "Set TTL(ms)"}
    remote function pExpire(@display {label: "Key"} string key, 
                            @display {label: "TTL(ms)"} int timeMilliSeconds) 
                            returns @display {label: "Result"} boolean | Error {
        return pExpire(self.datasource, java:fromString(key), timeMilliSeconds);
    }

    # Get the time to live for a key in milliseconds.
    #
    # + key - Key of which time-to-live should be obtained
    # + return - TTL of the key, in milli seconds or `Error` if an Error occurs
    @display {label: "Get TTL(ms)"}
    remote function pTtl(@display {label: "Key"} string key) 
                         returns @display {label: "TTL(ms)"} int | Error {
        return pTtl(self.datasource, java:fromString(key));
    }

    # Return a random key from the keyspace.
    #
    # + return - Random key, or `nil` when the database is empty or `Error` if an Error occurs
    @display {label: "Get a random key"}
    remote function randomKey() returns @display {label: "Key"} string | Error {
        return <string>java:toString(check randomKey(self.datasource));
    }

    # Rename a key.
    #
    # + key - Key to be renamed
    # + newName - New name of the key
    # + return - String with the value `OK` if the operation was successful or `Error` if an Error occurs
    @display {label: "Rename a key"}
    remote function rename(@display {label: "Key"} string key, @display {label: "New name to rename"} string newName)
                           returns @display {label: "Result"} string | Error {
        return <string>java:toString(check rename(self.datasource, java:fromString(key), java:fromString(newName)));
    }

    # Rename a key, only if the new key does not exist.
    #
    # + key - Key to be renamed
    # + newName - New name of the key
    # + return - Boolean `true` if key was renamed to newkey. boolean `false` if newkey already exists. Or `Error` if an
    #            Error occurs
    @display {label: "Rename a non-existance key"}
    remote function renameNx(@display {label: "Key"} string key, @display {label: "New name to rename"} string newName)
                             returns @display {label: "Result"} boolean | Error {
        return renameNx(self.datasource, java:fromString(key), java:fromString(newName));
    }

    # Sort elements in a list, set or sorted set.
    #
    # + key - Key of the data typeure to be sorted
    # + return - Sorted array containing the members of the sorted data type or `Error` if an Error occurs
    @display {label: "Sort elements"}
    remote function sort(@display {label: "Key"} string key) 
                         returns @display {label: "Sorted array of members"} string[] | Error {
        return sort(self.datasource, java:fromString(key));
    }

    # Get the time to live for a key.
    #
    # + key - Key of which the time to live needs to be obtained
    # + return - Time to live in seconds or a negative value/`Error` in order to signal an Error in evaluating ttl.
    #         Whether it is a negative value of an `Error` would differ depending on whether the Error occurs at DB
    #         level or the driver level
    @display {label: "Get TTL of key"}
    remote function ttl(@display {label: "Key"} string key) 
                        returns @display {label: "Time to live(s)"} int | Error {
        return ttl(self.datasource, java:fromString(key));
    }

    # Determine the type stored at key.
    #
    # + key - Key of which the type needs to be obtained
    # + return - Type stored at key
    @display {label: "Type of a key"}
    remote function redisType(@display {label: "Key"} string key) 
                              returns @display {label: "Type of value of a key"} string | Error {
        return <string>java:toString(check redisType(self.datasource, java:fromString(key)));
    }

    // Connection commands

    # Ping the server.
    # 
    # + return - String with the value `PONG` if the operation was successful
    @display {label: "Ping the server"}
    remote function ping() returns @display {label: "Result"} string | Error {
        return <string>java:toString(check ping(self.datasource));
    }

    # Authenticate to the server.
    #
    # + password - Password to authenticate
    # + return - String with the value `OK` if the operation was successful or `Error` if an Error occurs
    @display {label: "Authenticate to server"}
    remote function auth(@display {label: "Password"} string password) 
                         returns @display {label: "Result"} string | Error {
        return <string>java:toString(check auth(self.datasource, java:fromString(password)));
    }

    # Echo the given string.
    #
    # + message - Message to be echo-ed
    # + return - Message itself if the operation was successful or `Error` if an Error occurs
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
# 
# + datasource - data source handle
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
