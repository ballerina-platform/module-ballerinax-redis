// Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

documentation {
    The Caller Actions for Redis databases.
}
public type CallerActions object {
    // String operations
    documentation {
        Append a value to a key.

        P{{key}} The key
        P{{value}} The string value to be appended
        R{{}} Length of the string after the operation or `error` if an error occurs
    }
    public extern function append(string key, string value) returns (int|error);

    documentation {
        Count set bits in a string.

        P{{key}} The key
        R{{}} The number of bits set to 1 or `error` if an error occurs
    }
    public extern function bitCount(string key) returns (int|error);

    documentation {
        Perform bitwise AND between strings.

        P{{destination}} Result key of the operation
        P{{keys}} Input keys to perform AND between
        R{{}} The size of the string stored in the destination key, that is equal to the size of the longest input
              string or `error` if an error occurs
    }
    public extern function bitOpAnd(string destination, string[] keys)
        returns (int|error);

    documentation {
        Perform bitwise OR between strings.

        P{{destination}} Result key of the operation
        P{{keys}} Input keys to perform AND between
        R{{}} The size of the string stored in the destination key, that is equal to the size of the longest input
              string or `error` if an error occurs
    }
    public extern function bitOpOr(string destination, string[] keys)
        returns (int|error);

    documentation {
        Perform bitwise NOT on a string.

        P{{destination}} Result key of the operation
        P{{key}} Input keys to perform AND between
        R{{}} The size of the string stored in the destination key or `error` if an error occurs
    }
    public extern function bitOpNot(string destination, string key)
        returns (int|error);

    documentation {
        Perform bitwise XOR between strings.

        P{{destination}} Result key of the operation
        P{{key}} Input keys to perform AND between
        R{{}} The size of the string stored in the destination key, that is equal to the size of the longest input
              string or `error` if an error occurs
    }
    public extern function bitOpXor(string destination, string[] key)
        returns (int|error);

    documentation {
        Decrement the integer value of a key by one.

        P{{key}} The key
        R{{}} The value of the key after the decrement or `error` if an error occurs
    }
    public extern function decr(string key) returns (int|error);

    documentation {
        Decrement the integer value of a key by the given number.

        P{{key}} The key
        P{{value}} The value to be decremented
        R{{}} The value of the key after the decrement or `error` if an error occurs
    }
    public extern function decrBy(string key, int value) returns (int|error);

    documentation {
        Returns the bit value at offset in the string value stored at key.

        P{{key}} The key
        P{{offset}} The offset
        R{{}} The bit value stored at offset or `error` if an error occurs
    }
    public extern function getBit(string key, int offset) returns (int|error);

    documentation {
        Get a substring of the string stored at a key.

        P{{key}} The key
        P{{startPos}} The starting point of the substring
        P{{end}} The end point of the substring
        R{{}} The substring or `error` if an error occurs
    }
    public extern function getRange(string key, int startPos, int end)
        returns (string|error);

    documentation {
        Set the string value of a key and return its old value.

        P{{key}} The key
        P{{value}} The value to be set
        R{{}} The old value stored at key, or nil when key does not exist or `error` if an error occurs
    }
    public extern function getSet(string key, string value) returns (string?|error);

    documentation {
        Get the value of a key.

        P{{key}} The key
        R{{}} The value of the key, or nil when key does not exist or `error` if an error occurs
    }
    public extern function get(string key) returns (string?|error);

    documentation {
        Increment the integer value of a key by one.

        P{{key}} The key
        R{{}} The value of the key after increment or `error` if an error occurs
    }
    public extern function incr(string key) returns (int|error);

    documentation {
	    Increment the integer value of a key by the given amount.

        P{{key}} The key
        P{{value}} The amount to increment
        R{{}} The value of the key after increment or `error` if an error occurs
    }
    public extern function incrBy(string key, int value) returns (int|error);

    documentation {
        Increment the integer value of a key by the given amount.

        P{{key}} The key
        P{{value}} The amount to increment
        R{{}} The value of the key after increment or `error` if an error occurs
     }
    public extern function incrByFloat(string key, float value) returns (float|error);

    documentation {
        Get the values of all the given keys.

        P{{keys}} The keys of which the values need to be retrieved
        R{{}} Array of values at the specified keys or `error` if an error occurs
    }
    public extern function mGet(string[] keys) returns (string[]|error);

    documentation {
        Set multiple keys to multiple values.

        P{{keyValueMap}} A map of key-value pairs to be set
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function mSet(map keyValueMap) returns (string|error);

    documentation {
        Set multiple keys to multiple values, only if none of the keys exist.

        P{{keyValueMap}} A map of key-value pairs to be set
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function mSetNx(map keyValueMap) returns (boolean|error);

    documentation {
        Set the value and expiration in milliseconds of a key.

        P{{key}} The key
        P{{value}} The value to be set
        P{{expirationTime}} Expiration time in milli seconds
        R{{}} New value of the key or `error` if an error occurs
    }
    public extern function pSetEx(string key, string value, int expirationTime)
        returns (string|error);

    documentation {
        Sets or clears the bit at offset in the string value stored at key.

        P{{key}} The key
        P{{value}} The value to be set
        P{{offset}} The offset at which the value should be set
        R{{}} The original bit value stored at offset or `error` if an error occurs
    }
    public extern function setBit(string key, int value, int offset) returns (int|error);

    documentation {
        Set the value and expiration of a key.

        P{{key}} The key
        P{{value}} The value to be set
        P{{expirationPeriodSeconds}} Expiration time to be set, in seconds
        R{{}} New value of the key or `error` if an error occurs
    }
    public extern function setEx(string key, string value, int expirationPeriodSeconds)
        returns (string|error);

    documentation {
        Set the value of a key, only if the key does not exist.

        P{{key}} The key
        P{{value}} The value to be set
        R{{}} New value of the key or `error` if an error occurs
    }
    public extern function setNx(string key, string value) returns (boolean|error);

    documentation {
        Overwrite part of a string at key starting at the specified offset.

        P{{key}} The key
        P{{offset}} The offset at which the value should be set
        P{{value}} The value to be set
        R{{}} The length of the string after it was modified or `error` if an error occurs
    }
    public extern function setRange(string key, int offset, string value)
        returns (int|error);

    documentation {
        Get the length of the value stored in a key.

        P{{key}} The key
        R{{}} the length of the string at key, or 0 when key does not exist or `error` if an error occurs
    }
    public extern function strln(string key) returns (int|error);

    documentation {
        Set the value of a key.

        P{{key}} The key
        P{{value}} The value to be set
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function setVal(string key, string value) returns (string|error);

    //list operations

    documentation {
        Prepend one or multiple values to a list.

        P{{key}} The key
        P{{values}} The values to be prepended
        R{{}} The length of the list after the push operation(s) or `error` if an error occurs
    }
    public extern function lPush(string key, string[] values) returns (int|error);

    documentation {
        Remove and get the first element in a list.

        P{{key}} The key
        R{{}} The value of the first element, or nil when key does not exist or `error` if an error occurs
    }
    public extern function lPop(string key) returns (string?|error);

    documentation {
        Prepend one or multiple values to a list, only if the list exists.

        P{{key}} The key
        P{{values}} The values to be prepended
        R{{}} The length of the list after the push operation(s) or `error` if an error occurs
    }
    public extern function lPushX(string key, string[] values) returns (int|error);

    documentation {
        Remove and get the first element in a list, or block until one is available.

        P{{timeOut}} The timeout in seconds
        P{{keys}} The keys
        R{{}} `nil` when no element could be popped and the timeout expired. A map containing one item, with the
              key being  the name of the key where an element was popped and the second element  being the value of the
              popped element, or `error` if an error occurs
    }
    public extern function bLPop(int timeOut, string[] keys) returns (map?|error);

    documentation {
        Remove and get the last element in a list, or block until one is available.

        P{{timeOut}} The timeout in seconds
        P{{keys}} The keys
        R{{}} `nil` when no element could be popped and the timeout expired. A map containing one item, with the
              key being  the name of the key where an element was popped and the second element being the value of the
              popped element, or `error` if an error occurs
    }
    public extern function bRPop(int timeOut, string[] keys) returns (map?|error);

    documentation {
        Get an element from a list by its index.

        P{{key}} The key
        P{{index}} The index from which the element should be retrieved
        R{{}} The value at the given index or `error` if an error occurs
    }
    public extern function lIndex(string key, int index) returns (string|error);

    documentation {
        Insert an element before or after another element in a list.

        P{{key}} The key
        P{{before}} boolean value representing Whether element should be inserted before or after the pivot
        P{{pivot}} The pivot
        P{{value}} The value
        R{{}} the length of the list after the insert operation, or -1 when the value pivot not found, or `error` if
        an error occurs
    }
    public extern function lInsert(string key, boolean before, string pivot, string value)
        returns (int|error);

    documentation {
        Get the length of a list.

        P{{key}} The key
        R{{}} The length of the list at key or `error` if an error occurs
    }
    public extern function lLen(string key) returns (int|error);

    documentation {
        Get a range of elements from a list.

        P{{key}} The key
        P{{startPos}} The begining index of the range
        P{{stopPos}} The last index of the range
        R{{}} Array of elements in the specified range or `error` if an error occurs
    }
    public extern function lRange(string key, int startPos, int stopPos)
        returns (string[]|error);

    documentation {
        Remove elements from a list.

        P{{key}} The key
        P{{count}} The number of elements to be removed
        P{{value}} The value which the elements to be removed should be equal to
        R{{}} Number of elements removed or `error` if an error occurs
    }
    public extern function lRem(string key, int count, string value) returns (int|error);

    documentation {
        Set the value of an element in a list by its index.

        P{{key}} The key of the list
        P{{index}} The index of the element of which the value needs to be set
        P{{value}} The value to be set
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function lSet(string key, int index, string value)
        returns (string|error);

    documentation {
        Trim a list to the specified range.

        P{{key}} The key of the list
        P{{startPos}} The starting index of the range
        P{{stopPos}} The end index of the range
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function lTrim(string key, int startPos, int stopPos) returns (string|error);

    documentation {
        Remove and get the last element in a list.

        P{{key}} The key of the list
        R{{}} The value of the last element, or `nil` when key does not exist or `error` if an error occurs
    }
    public extern function rPop(string key) returns (string?|error);

    documentation {
        Remove the last element in a list, append it to another list and return it.

        P{{source}} The source key
        P{{destination}} The destination key
        R{{}} The element being popped and pushed or `error` if an error occurs
    }
    public extern function rPopLPush(string source, string destination)
        returns (string|error);

    documentation {
        Append one or multiple values to a list.

        P{{key}} The key of the list
        P{{values}} Array of values to be appended
        R{{}} The length of the list after the push operation or `error` if an error occurs
    }
    public extern function rPush(string key, string[] values) returns (int|error);

    documentation {
        Append one or multiple values to a list, only if the list exists.

        P{{key}} The key of the list
        P{{values}} Array of values to be appended
        R{{}} The length of the list after the push operation or `error` if an error occurs
    }
    public extern function rPushX(string key, string[] values) returns (int|error);

    // Set Commands
    documentation {
        Add one or more members to a set.

        P{{key}} The key of the set
        P{{values}} Array of values to be added
        R{{}} The number of elements that were added to the set, not including all the elements which were
              already present in the set, or `error` if an error occurs
    }
    public extern function sAdd(string key, string[] values) returns (int|error);

    documentation {
        Get the number of members in a set

        P{{key}} The key of the set
        R{{}} The cardinality (number of elements) of the set or `error` if an error occurs
    }
    public extern function sCard(string key) returns (int|error);

    documentation {
        Return set resulting from the difference between the first set and all the successive sets

        P{{keys}} The keys of the sets
        R{{}} An array of members of the resulting set or `error` if an error occurs
    }
    public extern function sDiff(string[] keys) returns (string[]|error);

    documentation {
        Obtain the set resulting from the difference between the first set and all the successive.
        sets and store at the provided destination.

        P{{destination}} The destination key of the resulting set
        P{{keys}} The keys of the sets to find the difference of
        R{{}} The number of members in the resulting set or `error` if an error occurs
    }
    public extern function sDiffStore(string destination, string[] keys)
        returns (int|error);

    documentation {
        Return the intersection of the provided sets.

        P{{keys}} The keys of the sets to be intersected
        R{{}} An array of members of the resulting set or `error` if an error occurs
    }
    public extern function sInter(string[] keys) returns (string[]|error);

    documentation {
        Obtain the intersection of the provided sets and store at the provided destination.

        P{{destination}} The destination key of the resulting set
        P{{keys}} The keys of the sets to be intersected
        R{{}} An array of members of the resulting set or `error` if an error occurs
    }
    public extern function sInterStore(string destination, string[] keys)
        returns (int|error);

    documentation {
        Determine if a given value is a member of a set.

        P{{key}} The key of the set
        P{{value}} The value
        R{{}} boolean true/false depending on whether the value is a member of the set or not, or `error` if an error
              occurs
    }
    public extern function sIsMember(string key, string value) returns (boolean|error);

    documentation {
        Get all the members in a set.

        P{{key}} The key of the set
        R{{}} Array of all members in the set or `error` if an error occurs
    }
    public extern function sMembers(string key) returns (string[]|error);

    documentation {
        Move a member from one set to another.

        P{{source}} The source key
        P{{destination}} The destination key
        P{{member}} The member to be moved
        R{{}} `true` if the element is moved. `false` if the element is not a member of source and no
               operation was performed or `error` if an error occurs
    }
    public extern function sMove(string source, string destination, string member)
        returns (boolean|error);


    documentation {
        Remove and return a random member from a set.

        P{{key}} The source key
        P{{count}} Number of members to pop
        R{{}} Array of removed elements or `nil` if key does not exist or `error` if an error occurs
    }
    public extern function sPop(string key, int count) returns (string[]?|error);

    documentation {
        Get one or multiple random members from a set.

        P{{key}} The key of the set
        P{{count}} Number of members to obtain
        R{{}} Array of the randomly selected elements, or `nil` when key does not exist or `error` if an error occurs
    }
    public extern function sRandMember(string key, int count) returns (string[]?|error);

    documentation {
        Remove one or more members from a set.

        P{{key}} The key of the set
        P{{members}} Array of members to remove
        R{{}} The number of members that were removed from the set, not including non existing members or `error` if
              an error occurs
    }
    public extern function sRem(string key, string[] members) returns (int|error);

    documentation {
        Return the union of multiple sets.

        P{{keys}} Array of keys of sets
        R{{}} Array of members of the resulting set or `error` if an error occurs
    }
    public extern function sUnion(string[] keys) returns (string[]|error);

    documentation {
        Return the union of multiple sets.

        P{{destination}} The destination key of the resulting set
        P{{keys}} Array of keys of sets
        R{{}} Number of members of the resulting set or `error` if an error occurs
    }
    public extern function sUnionStore(string destination, string[] keys)
        returns (int|error);

    //Sorted set Commands
    documentation {
        Add one or more members to a sorted set, or update its score if it already exist.

        P{{key}} The key of the sorted set
        P{{memberScoreMap}} A map of members and corresponding scores
        R{{}} The number of elements that were added to the sorted set, not including all the elements which were
              already present in the set for which the score was updated, or `error` if an error occurs
    }
    public extern function zAdd(string key, map memberScoreMap) returns (int|error);

    documentation {
        Get the number of members in a sorted set.
 
        P{{key}} The key of the sorted set
        R{{}} The cardinality (number of elements) of the sorted set or `error` if an error occurs
    }
    public extern function zCard(string key) returns (int|error);

    documentation {
        Count the members in a sorted set with scores within the given range.

        P{{key}} The key of the sorted set
        P{{min}} The minimum score of the range
        P{{max}} The maximum score of the range
        R{{}} The number of elements in the specified score range or `error` if an error occurs
    }
    public extern function zCount(string key, float min, float max) returns (int|error);

    documentation {
        Increment the score of a member in a sorted set.

        P{{key}} The key of the sorted set
        P{{amount}} The amount to increment
        P{{member}} The member whose score to be incremented
        R{{}} The new score of the member or `error` if an error occurs
    }
    public extern function zIncrBy(string key, float amount, string member)
        returns (float|error);

    documentation {
        Intersect multiple sorted sets and store the resulting sorted set in a new key.

        P{{destination}} The destination key of the resulting sorted set
        P{{keys}} The keys of the sorted sets to be intersected
        R{{}} The number of elements in the resulting sorted set or `error` if an error occurs
    }
    public extern function zInterStore(string destination, string[] keys)
        returns (int|error);

    documentation {
        Count the members in a sorted set within the given lexicographical range.

        P{{key}} The key of the sorted set
        P{{min}} The minimum lexicographical value of the range
        P{{max}} The maximum lexicographical value of the range
        R{{}} The number of elements in the specified lexicographical value range or `error` if an error occurs
    }
    public extern function zLexCount(string key, string min, string max)
        returns (int|error);

    documentation {
        Return a range of members in a sorted set, by index.

        P{{key}} The key of the sorted set
        P{{min}} The minimum index of the range
        P{{max}} The maximum index of the range
        R{{}} The range of members in a sorted set, by index, or `error` if an error occurs
    }
    public extern function zRange(string key, int min, int max) returns (string[]|error);

    documentation {
        Return a range of members in a sorted set, by lexicographical range from lowest to highest.

        P{{key}} The key of the sorted set
        P{{min}} The minimum lexicographical value of the range
        P{{max}} The maximum lexicographical value of the range
        R{{}} Array of members in the specified lexicographical value range ordered from lowest to highest or `error`
              if an error occurs
    }
    public extern function zRangeByLex(string key, string min, string max)
        returns (string[]|error);

    documentation {
        Return a range of members in a sorted set, by lexicographical range ordered from highest to
        lowest.
        
        P{{key}} The key of the sorted set
        P{{min}} The lexicographical value of the range
        P{{max}} The maximum lexicographical value of the range
        R{{}} Array of members in the specified lexicographical value range ordered from highest to lowest or `error`
              if an error occurs
    }
    public extern function zRevRangeByLex(string key, string min, string max)
        returns (string[]|error);

    documentation {
        Return a range of members in a sorted set, by score from lowest to highest.
    
        P{{key}} The key of the sorted set
        P{{min}} The minimum score of the range
        P{{max}} The maximum score of the range
        R{{}} Array of members in the specified score range ordered from lowest to highest or `error` if an error occurs
    }
    public extern function zRangeByScore(string key, float min, float max)
        returns (string[]|error);

    documentation {
        Determine the index of a member in a sorted set.

        P{{key}} The key of the sorted set
        P{{member}} The member of which the index needs to be obtained
        R{{}} The index of the member or `error` if an error occurs
    }
    public extern function zRank(string key, string member) returns (int|error);

    documentation {
        Remove one or more members from a sorted set

        P{{key}} The key of the sorted set
        P{{members}} The members to be removed
        R{{}} The number of members removed from the sorted set, not including non existing members or `error` if an
              error occurs
    }
    public extern function zRem(string key, string[] members) returns (int|error);

    documentation {
        Remove all members in a sorted set between the given lexicographical range.

        P{{key}} The key of the sorted set
        P{{min}} The minimum lexicographical value of the range
        P{{max}} The maximum lexicographical value of the range
        R{{}} The number of members removed from the sorted set or `error` if an error occurs
    }
    public extern function zRemRangeByLex(string key, string min, string max)
        returns (int|error);

    documentation {
        Remove all members in a sorted set within the given indices.

        P{{key}} The key of the sorted set
        P{{min}} The minimum index of the range
        P{{max}} The maximum index of the range
        R{{}} The number of members removed from the sorted set or `error` if an error occurs
    }
    public extern function zRemRangeByRank(string key, int min, int max)
        returns (int|error);

    documentation {
        Remove all members in a sorted set within the given scores.

        P{{key}} The key of the sorted set
        P{{min}} The minimum score of the range
        P{{max}} The maximum score of the range
        R{{}} The number of members removed from the sorted set or `error` if an error occurs
    }
    public extern function zRemRangeByScore(string key, float min, float max)
        returns (int|error);

    documentation {
        Return a range of members in a sorted set, by index, ordered highest to lowest.

        P{{key}} The key of the sorted set
        P{{min}} The minimum index of the range
        P{{max}} The maximum index of the range
        R{{}} The number of elements in the specified index range or `error` if an error occurs
    }
    public extern function zRevRange(string key, int min, int max)
        returns (string[]|error);

    documentation {
        Return a range of members in a sorted set, by score from highest to lowest.

        P{{key}} The key of the sorted set
        P{{min}} The minimum score of the range
        P{{max}} The maximum score of the range
        R{{}} Array of members in the specified score range ordered from highest to lowest or `error` if an error occurs
    }
    public extern function zRevRangeByScore(string key, float min, float max)
        returns (string[]|error);

    documentation {
        Determine the index of a member in a sorted set

        P{{key}} The key of the sorted set
        P{{member}} The member of which the index needs to be obtained
        R{{}} The index of the member or `error` if an error occurs
}
    public extern function zRevRank(string key, string member) returns (int|error);

    documentation {
        Determine the score of a member in a sorted set

        P{{key}} The key of the sorted set
        P{{member}} The member of which the score needs to be obtained
        R{{}} The score of the member or `error` if an error occurs
}
    public extern function zScore(string key, string member) returns (float|error);

    documentation {
        Return the union of multiple sorted sets

        P{{destination}} The destination key of the resulting set
        P{{keys}} Array of keys of sorted sets
        R{{}} Number of members of the resulting sorted set or `error` if an error occurs
}
    public extern function zUnionStore(string destination, string[] keys) returns (int|error);

    //Hash Commands

    documentation {
        Delete one or more hash fields.

        P{{key}} The key of the hash
        P{{fields}} Array of fields to be deleted
        R{{}} Number of fields that were removed from the hash, not including specified but non existing fields or
             `error` if an error occurs
    }
    public extern function hDel(string key, string[] fields) returns (int|error);

    documentation {
        Determine if a hash field exists.

        P{{key}} The key of the hash
        P{{field}} Array of fields to be deleted
        R{{}} boolean `true` if the hash contains the field. boolean false if the hash does not contain
              field or key does not exist or `error` if an error occurs
    }
    public extern function hExists(string key, string field) returns (boolean|error);

    documentation {
        Get the value of a hash field.

        P{{key}} The key of the hash
        P{{field}} The field
        R{{}} The value of the field or `error` if an error occurs
    }
    public extern function hGet(string key, string field) returns (string|error);

    documentation {
        Get the all values of a hash.

        P{{key}} The key of the hash
        R{{}} Map of field-value pairs or `error` if an error occurs
    }
    public extern function hGetAll(string key) returns (map|error);

    documentation {
        Increment the integer value of a hash field by the given number.

        P{{key}} The key of the hash
        P{{field}} The field
        P{{amount}} The amount to increment
        R{{}} The value of the field or `error` if an error occurs
    }
    public extern function hIncrBy(string key, string field, int amount)
        returns (int|error);

    documentation {
        Increment the float value of a hash field by the given number.

        P{{key}} The key of the hash
        P{{field}} The field
        P{{amount}} The amount to increment
        R{{}} The value of the field or `error` if an error occurs
    }
    public extern function hIncrByFloat(string key, string field, float amount)
        returns (float|error);

    documentation {
        Get all the fields in a hash.

        P{{key}} The key of the hash
        R{{}} Array of hash fields or `error` if an error occurs
    }
    public extern function hKeys(string key) returns (string[]|error);

    documentation {
        Get the number of fields in a hash.

        P{{key}} The key of the hash
        R{{}} Number of fields or `error` if an error occurs
    }
    public extern function hLen(string key) returns (int|error);

    documentation {
        Get the values of all the given hash fields.

        P{{key}} The key of the hash
        P{{fields}} Array of hash fields
        R{{}} Map of field-value pairs or `error` if an error occurs
    }
    public extern function hMGet(string key, string[] fields) returns (map|error);

    documentation {
        Set multiple hash fields to multiple values.

        P{{key}} The key of the hash
        P{{fieldValueMap}} Map of field-value pairs
        R{{}} A string with the value `OK` if the operation was successful, or `error` if an error occurs
}
    public extern function hMSet(string key, map fieldValueMap) returns (string|error);

    documentation {
        Set the string value of a hash field.

        P{{key}} The key of the hash
        P{{field}} The field
        P{{value}} The value to be set to the field
        R{{}} boolean `true` if field is a new field in the hash and value was set. boolean false if
              field already exists in the hash and the value was updated, or `error` if an error occurs
    }
    public extern function hSet(string key, string field, string value)
        returns (boolean|error);

    documentation {
	    Set the string value of a hash field, only if the field does not exist.

        P{{key}} The key of the hash
        P{{field}} The field
        P{{value}} The value to be set to the field
        R{{}} boolean `true` if field is a new field in the hash and value was set. boolean false if
              field already exists in the hash and no operation was performed, or `error` if an error occurs
   }
    public extern function hSetNx(string key, string field, string value)
        returns (boolean|error);

    documentation {
        Get the string length of the field value in a hash.

        P{{key}} The key of the hash
        P{{field}} The field
        R{{}} The length of the field value, or 0 when field is not present in the hash or key does
              not exist at all, or `error` if an error occurs
    }
    public extern function hStrln(string key, string field) returns (int|error);

    documentation {
        Get all the values in a hash.

        P{{key}} The key of the hash
        R{{}} Array of values in the hash, or an empty array when key does not exist or `error` if an error occurs
    }
    public extern function hVals(string key) returns (string[]|error);

    //Key Commands

    documentation {
        Delete one or more keys.

        P{{keys}} The key to be deleted
        R{{}} The number of keys that were removed or `error` if an error occurs
    }
    public extern function del(string[] keys) returns (int|error);

    documentation {
        Determine how many keys exist.

        P{{keys}} The keys of which existence to be found out
        R{{}} The number of existing keys or `error` if an error occurs
    }
    public extern function exists(string[] keys) returns (int|error);

    documentation {
        Set a key's time to live in seconds.

        P{{key}} The keys of which expiry time to be set
        P{{seconds}} Expiry in seconds
        R{{}} true if the timeout was set. false if key does not exist or the timeout could not be set or `error` if
              an error occurs
    }
    public extern function expire(string key, int seconds) returns (boolean|error);

    documentation {
        Find all keys matching the given pattern.

        P{{pattern}} The pattern to match
        R{{}} Array of keys matching the given pattern or `error` if an error occurs
    }
    public extern function keys(string pattern) returns (string[]|error);

    documentation {
        Move a key to another database.

        P{{key}} The key to be moved
        P{{database}} The database to which the key needs to be moved
        R{{}} boolean true if key was succesfully moved, boolean false otherwise or `error` if an error occurs
    }
    public extern function move(string key, int database) returns (boolean|error);

    documentation {
        Remove the expiration from a key.

        P{{key}} The key of which expiry time should be removed
        R{{}} boolean `true` if the timeout was removed. boolean `false` if key does not exist or does not have
              an associated timeout, or `error` if an error occurs
    }
    public extern function persist(string key) returns (boolean|error);

    documentation {
        Set a key's time to live in milliseconds..

        P{{key}} The key of which expiry time should be removed
        P{{timeMilliSeconds}} The expiry time in milli seconds
        R{{}} boolean `true` if the timeout was set. boolean false if key does not exist or the timeout could not
              be set, or `error` if an error occurs
    }
    public extern function pExpire(string key, int timeMilliSeconds)
        returns (boolean|error);

    documentation {
        Get the time to live for a key in milliseconds

        P{{key}} The key of which time-to-live should be obtained
        R{{}} time-to-live of the key, in milli seconds or `error` if an error occurs
    }
    public extern function pTtl(string key) returns (int|error);

    documentation {
        Return a random key from the keyspace.

        R{{}} The random key, or `nil` when the database is empty or `error` if an error occurs
    }
    public extern function randomKey() returns (string?|error);

    documentation {
        Rename a key.

        P{{key}} The key to be renamed
        P{{newName}} The new name of the key
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function rename(string key, string newName) returns (string|error);

    documentation {
        Rename a key, only if the new key does not exist.

        P{{key}} The key to be renamed
        P{{newName}} The new name of the key
        R{{}} boolean `true` if key was renamed to newkey. boolean `false` if newkey already exists. Or `error` if an
              error occurs
    }
    public extern function renameNx(string key, string newName) returns (boolean|error);

    documentation {
        Sort the elements in a list, set or sorted set.

        P{{key}} The key of the data typeure to be sorted
        R{{}} Sorted array containing the members of the sorted data type or `error` if an error occurs
    }
    public extern function sort(string key) returns (string[]|error);

    documentation {
        Get the time to live for a key.

        P{{key}} The key of which the time to live needs to be obtained
        R{{}} Time to live in seconds or a negative value/`error` in order to signal an error in evaluating ttl.
              Whether it is a negative value of an `error` would differ depending on whether the error occurs at DB
              level or the driver level
    }
    public extern function ttl(string key) returns (int|error);

    documentation {
        Determine the type stored at key.

        P{{key}} The key of which the type needs to be obtained
        R{{}} Time to live in seconds or a negative value/`error` in order to signal an error in evaluating te type.
              Whether it is a negative value of an `error` would differ depending on whether the error occurs at DB
              level or the driver level
    }
    public extern function redisType(string key) returns (string|error);

    //Connection commands

    documentation {
        Authenticate to the server.

        P{{password}} The password
        R{{}} A string with the value `OK` if the operation was successful or `error` if an error occurs
    }
    public extern function auth(string password) returns (string|error);

    documentation {
        Echo the given string.

        P{{message}} The message to be echo-ed
        R{{}} The message itself if the operation was successful or `error` if an error occurs
    }
    public extern function echo(string message) returns (string|error);

    documentation {
        Ping the server.

        R{{}} A string with the value `PONG` if the operation was successful or `error` if an error occurs
    }
    public extern function ping() returns (string|error);

};
