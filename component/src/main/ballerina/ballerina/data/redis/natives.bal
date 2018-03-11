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

package ballerina.data.redis;

@Description {value:"Connection Properties for Redis connector"}
@Field {value:"clientName:The clientName of the connection"}
@Field {value:"poolingEnabled:Boolean value depending on whether the connection pooling is enabled or not"}
@Field {value:"sslEnabled:Boolean value depending on whether SSL is enabled or not"}
@Field {value:"startTlsEnabled:Boolean value depending on whether startTLS is enabled or not"}
@Field {value:"verifyPeerEnabled:Boolean value depending on whether peer verification is enabled or not"}
@Field {value:"database:The database to be used with the connection"}
@Field {value:"connectionTimeout:The timeout value for the connection"}
public struct ConnectionProperties {
    string clientName;

    boolean poolingEnabled = false;
    boolean isClusterConnection = false;
    boolean sslEnabled = false;
    boolean startTlsEnabled = false;
    boolean verifyPeerEnabled = false;

    int database = -1;
    int connectionTimeout = -1;
}

@Description {value:"Cassandra client connector."}
@Param {value:"hosts: Comma separated list of addresses of Redis nodes which are used to discover the cluster
topology"}
@Param {value:"password: The password to connect to a authentication enabled Redis instance"}
@Param {value:"options: Optional properties for Redis connection"}
public connector ClientConnector (string hosts, string password, ConnectionProperties options) {

    map sharedMap = {};

    // String operations

    @Description {value:"Append a value to a key"}
    @Param {value:"key:The key"}
    @Param {value:"value:The string value to be appended"}
    @Return {value:"Length of the string after the operation"}
    native action append (string key, string value) (int);

    @Description {value:"Count set bits in a string"}
    @Param {value:"key:The key"}
    @Return {value:"The number of bits set to 1"}
    native action bitCount (string key) (int);

    @Description {value:"Perform bitwise AND between strings"}
    @Param {value:"destination:Result key of the operation"}
    @Param {value:"value:Input keys to perform AND between"}
    @Return {value:"The size of the string stored in the destination key, that is equal to the size of the longest
    input string"}
    native action bitOpAnd (string destination, string[] keys) (int);

    @Description {value:"Perform bitwise OR between strings"}
    @Param {value:"destination:Result key of the operation"}
    @Param {value:"value:Input keys to perform AND between"}
    @Return {value:"The size of the string stored in the destination key, that is equal to the size of the longest
    input string"}
    native action bitOpOr (string destination, string[] keys) (int);

    @Description {value:"Perform bitwise NOT on a string"}
    @Param {value:"destination:Result key of the operation"}
    @Param {value:"value:Input keys to perform AND between"}
    @Return {value:"The size of the string stored in the destination key"}
    native action bitOpNot (string destination, string key) (int);

    @Description {value:"Perform bitwise XOR between strings"}
    @Param {value:"destination:Result key of the operation"}
    @Param {value:"value:Input keys to perform AND between"}
    @Return {value:"The size of the string stored in the destination key, that is equal to the size of the longest
    input string"}
    native action bitOpXor (string destination, string[] key) (int);

    @Description {value:"Decrement the integer value of a key by one"}
    @Param {value:"key:The key"}
    @Return {value:"The value of the key after the decrement"}
    native action decr (string key) (int);

    @Description {value:"Decrement the integer value of a key by the given number"}
    @Param {value:"key:The key"}
    @Return {value:"The value of the key after the decrement"}
    native action decrBy (string key, int value) (int);

    @Description {value:"Returns the bit value at offset in the string value stored at key"}
    @Param {value:"key:The key"}
    @Param {value:"offset:The offset"}
    @Return {value:"the bit value stored at offset"}
    native action getBit (string key, int offset) (int);

    @Description {value:"Get a substring of the string stored at a key"}
    @Param {value:"key:The key"}
    @Param {value:"start:The starting point of the substring"}
    @Param {value:"end:The end point of the substring"}
    @Return {value:"The substring"}
    native action getRange (string key, int start, int end) (string);

    @Description {value:"Set the string value of a key and return its old value"}
    @Param {value:"key:The key"}
    @Param {value:"value:The value to be set"}
    @Return {value:"the old value stored at key, or null when key does not exist"}
    native action getSet (string key, string value) (string);

    @Description {value:"Get the value of a key"}
    @Param {value:"key:The key"}
    @Return {value:"the value of the key, or null when key does not exist"}
    native action get (string key) (string);

    @Description {value:"Increment the integer value of a key by one"}
    @Param {value:"key:The key"}
    @Return {value:"the value of the key after increment"}
    native action incr (string key) (int);

    @Description {value:"Increment the integer value of a key by the given amount"}
    @Param {value:"key:The key"}
    @Param {value:"value:The amount to increment"}
    @Return {value:"the value of the key after increment"}
    native action incrBy (string key, int value) (int);

    @Description {value:"Increment the integer value of a key by the given amount"}
    @Param {value:"key:The key"}
    @Param {value:"value:The amount to increment"}
    @Return {value:"the value of the key after increment"}
    native action incrByFloat(string key, float value) (float);

    @Description {value:"Get the values of all the given keys"}
    @Param {value:"key:The key"}
    @Return {value:"Array of values at the specified keys"}
    native action mGet (string[] keys) (string[]);

    @Description {value:"Set multiple keys to multiple values"}
    @Param {value:"keyValueMap:A map of key-value pairs to be set"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action mSet (map keyValueMap) (string);

    @Description {value:"Set multiple keys to multiple values, only if none of the keys exist"}
    @Param {value:"keyValueMap:A map of key-value pairs to be set"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action mSetNx (map keyValueMap) (boolean);

    @Description {value:"Set the value and expiration in milliseconds of a key"}
    @Param {value:"key:The key"}
    @Param {value:"value:The value to be set"}
    @Param {value:"value:Expiration time in milli seconds"}
    @Return {value:"New value of the key"}
    native action pSetEx (string key, string value, int expirationTime) (string);

    @Description {value:"Sets or clears the bit at offset in the string value stored at key"}
    @Param {value:"key:The key"}
    @Param {value:"value:The value to be set"}
    @Param {value:"offset:The offset at which the value should be set"}
    @Return {value:"the original bit value stored at offset"}
    native action setBit (string key, int value, int offset) (int);

    @Description {value:"Set the value and expiration of a key"}
    @Param {value:"key:The key"}
    @Param {value:"value:The value to be set"}
    @Param {value:"expirationPeriodSeconds:Expiration time to be set, in seconds"}
    @Return {value:"New value of the key"}
    native action setEx (string key, string value, int expirationPeriodSeconds) (string);

    @Description {value:"Set the value of a key, only if the key does not exist"}
    @Param {value:"key:The key"}
    @Param {value:"value:The value to be set"}
    @Return {value:"New value of the key"}
    native action setNx (string key, string value) (boolean);

    @Description {value:"Overwrite part of a string at key starting at the specified offset"}
    @Param {value:"key:The key"}
    @Param {value:"offset:The offset at which the value should be set"}
    @Param {value:"value:The value to be set"}
    @Return {value:"The length of the string after it was modified"}
    native action setRange (string key, int offset, string value) (int);

    @Description {value:"Get the length of the value stored in a key"}
    @Param {value:"key:The key"}
    @Return {value:" the length of the string at key, or 0 when key does not exist"}
    native action strln (string key) (int);

    @Description {value:"Set the value of a key"}
    @Param {value:"key:The key"}
    @Param {value:"value:The value to be set"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action set (string key, string value) (string);

    //list operations

    @Description {value:"Prepend one or multiple values to a list"}
    @Param {value:"key:The key"}
    @Param {value:"value:The values to be prepended"}
    @Return {value:"The length of the list after the push operation(s)"}
    native action lPush (string key, string[] values) (int);

    @Description {value:"Remove and get the first element in a list"}
    @Param {value:"key:The key"}
    @Return {value:"the value of the first element, or null when key does not exist"}
    native action lPop (string key) (string);

    @Description {value:"Prepend one or multiple values to a list, only if the list exists"}
    @Param {value:"key:The key"}
    @Param {value:"value:The values to be prepended"}
    @Return {value:"The length of the list after the push operation(s)"}
    native action lPushX (string key, string[] values) (int);

    @Description {value:"Remove and get the first element in a list, or block until one is available"}
    @Param {value:"timeout:The timeout in seconds"}
    @Param {value:"key:The keys"}
    @Return {value:"null when no element could be popped and the timeout expired. A map containing one item, with the
    key being  the name of the key where an element was popped and the second element  being the value of the popped
    element."}
    native action bLPop (int timeOut, string[] keys) (map);

    @Description {value:"Remove and get the last element in a list, or block until one is available"}
    @Param {value:"timeout:The timeout in seconds"}
    @Param {value:"key:The keys"}
    @Return {value:"null when no element could be popped and the timeout expired. A map containing one item, with the
    key being  the name of the key where an element was popped and the second element  being the value of the popped
    element."}
    native action bRPop (int timeOut, string[] keys) (map);

    @Description {value:"Get an element from a list by its index"}
    @Param {value:"key:The key"}
    @Param {value:"index:The index from which the element should be retrieved"}
    @Return {value:"The value at the given index"}
    native action lIndex (string key, int index) (string);

    @Description {value:"Insert an element before or after another element in a list"}
    @Param {value:"key:The key"}
    @Param {value:"before:boolean value representing Whether element should be inserted before or after the pivot"}
    @Param {value:"pivot:The pivot"}
    @Return {value:"the length of the list after the insert operation, or -1 when the value pivot not
     found"}
    native action lInsert (string key, boolean before, string pivot, string value) (int);

    @Description {value:"Get the length of a list"}
    @Param {value:"key:The key"}
    @Return {value:"The length of the list at key"}
    native action lLen (string key) (int);

    @Description {value:"Get a range of elements from a list"}
    @Param {value:"key:The key"}
    @Param {value:"start:The begining index of the range"}
    @Param {value:"stop:The last index of the range"}
    @Return {value:"Array of elements in the specified range"}
    native action lRange (string key, int start, int stop) (string[]);

    @Description {value:"Remove elements from a list"}
    @Param {value:"key:The key"}
    @Param {value:"count:The number of elements to be removed"}
    @Param {value:"value:The value which the elements to be removed should be equal to"}
    @Return {value:"Number of elements removed"}
    native action lRem (string key, int count, string value) (int);

    @Description {value:"Set the value of an element in a list by its index"}
    @Param {value:"key:The key of the list"}
    @Param {value:"index:The index of the element of which the value needs to be set"}
    @Param {value:"value:The value to be set"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action lSet (string key, int index, string value) (string);

    @Description {value:"Trim a list to the specified range"}
    @Param {value:"key:The key of the list"}
    @Param {value:"start:The starting index of the range"}
    @Param {value:"stop:The end index of the range"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action lTrim (string key, int start, int stop) (string);

    @Description {value:"Remove and get the last element in a list"}
    @Param {value:"key:The key of the list"}
    @Return {value:"The value of the last element, or null when key does not exist"}
    native action rPop (string key) (string);

    @Description {value:"Remove the last element in a list, append it to another list and return it"}
    @Param {value:"source:The source key"}
    @Param {value:"destination:The destination key"}
    @Return {value:"The element being popped and pushed"}
    native action rPopLPush (string source, string destination) (string);

    @Description {value:" Append one or multiple values to a list"}
    @Param {value:"key:The key of the list"}
    @Param {value:"values:Array of values to be appended"}
    @Return {value:"The length of the list after the push operation"}
    native action rPush (string key, string[] values) (int);

    @Description {value:" Append one or multiple values to a list, only if the list exists"}
    @Param {value:"key:The key of the list"}
    @Param {value:"values:Array of values to be appended"}
    @Return {value:"The length of the list after the push operation"}
    native action rPushX (string key, string[] values) (int);

    // Set Commands
    @Description {value:"Add one or more members to a set"}
    @Param {value:"key:The key of the set"}
    @Param {value:"values:Array of values to be added"}
    @Return {value:"the number of elements that were added to the set, not including all the elements which were
    already present in the set"}
    native action sAdd (string key, string[] values) (int);

    @Description {value:"Get the number of members in a set"}
    @Param {value:"key:The key of the set"}
    @Return {value:"The cardinality (number of elements) of the set"}
    native action sCard (string key) (int);

    @Description {value:"Return set resulting from the difference between the first set and all the successive sets"}
    @Param {value:"keys:The keys of the sets"}
    @Return {value:"An array of members of the resulting set"}
    native action sDiff (string[] keys) (string[]);

    @Description {value:"Obtain the set resulting from the difference between the first set and all the successive
    sets and store at the provided destination"}
    @Param {value:"destination:The destination key of the resulting set"}
    @Return {value:"The number of members in the resulting set"}
    native action sDiffStore (string destination, string[] keys) (int);

    @Description {value:"Return the intersection of the provided sets"}
    @Param {value:"keys:The keys of the sets to be intersected"}
    @Return {value:"An array of members of the resulting set"}
    native action sInter (string[] keys) (string[]);

    @Description {value:"Obtain the intersection of the provided sets and store at the provided destination"}
    @Param {value:"destination:The destination key of the resulting set"}
    @Param {value:"keys:The keys of the sets to be intersected"}
    @Return {value:"An array of members of the resulting set"}
    native action sInterStore (string destination, string[] keys) (int);

    @Description {value:"Determine if a given value is a member of a set"}
    @Param {value:"key:The key of the set"}
    @Param {value:"value:The value"}
    @Return {value:"boolean true/false depending on whether the value is a member of the set or not"}
    native action sIsMember (string key, string value) (boolean);

    @Description {value:"Get all the members in a set"}
    @Param {value:"key:The key of the set"}
    @Return {value:"Array of all members in the set"}
    native action sMembers (string key) (string[]);

    @Description {value:"Move a member from one set to another"}
    @Param {value:"source:The source key"}
    @Param {value:"destination:The destination key"}
    @Return {value:"true if the element is moved. false if the element is not a member of source and no
     operation was performed"}
    native action sMove (string source, string destination, string member) (boolean);


    @Description {value:"Remove and return a random member from a set"}
    @Param {value:"key:The source key"}
    @Param {value:"count:Number of members to pop"}
    @Return {value:"Array of removed elements or null if key does not exist"}
    native action sPop (string key, int count) (string[]);

    @Description {value:"Get one or multiple random members from a set"}
    @Param {value:"key:The key of the set"}
    @Param {value:"count:Number of members to obtain"}
    @Return {value:"Array of the randomly selected elements, or null when key does not exist"}
    native action sRandMember (string key, int count) (string[]);

    @Description {value:"Remove one or more members from a set"}
    @Param {value:"key:The key of the set"}
    @Param {value:"members:Array of members to remove"}
    @Return {value:"The number of members that were removed from the set, not including non existing members"}
    native action sRem (string key, string[] members) (int);

    @Description {value:"Return the union of multiple sets"}
    @Param {value:"keys:Array of keys of sets"}
    @Return {value:"Array of members of the resulting set"}
    native action sUnion (string[] keys) (string[]);

    @Description {value:"Return the union of multiple sets"}
    @Param {value:"destination: The destination key of the resulting set"}
    @Param {value:"keys:Array of keys of sets"}
    @Return {value:"Number of members of the resulting set"}
    native action sUnionStore (string destination, string[] keys) (int);

    //Sorted set Commands
    @Description {value:"Add one or more members to a sorted set, or update its score if it already exist"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"memberScoreMap:A map of members and corresponding scores"}
    @Return {value:"the number of elements that were added to the sorted set, not including all the elements which were
    already present in the set for which the score was updated"}
    native action zAdd (string key, map memberScoreMap) (int);

    @Description {value:"Get the number of members in a sorted set"}
    @Param {value:"key:The key of the sorted set"}
    @Return {value:"The cardinality (number of elements) of the sorted set"}
    native action zCard (string key) (int);

    @Description {value:"Count the members in a sorted set with scores within the given range"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum score of the range"}
    @Param {value:"max:The maximum score of the range"}
    @Return {value:"The number of elements in the specified score range"}
    native action zCount (string key, float min, float max) (int);

    @Description {value:"Increment the score of a member in a sorted set"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"amount:The amount to increment"}
    @Param {value:"member:The member whose score to be incremented"}
    @Return {value:"The new score of the member"}
    native action zIncrBy (string key, float amount, string member) (float);

    @Description {value:"Intersect multiple sorted sets and store the resulting sorted set in a new key"}
    @Param {value:"destination:The destination key of the resulting sorted set"}
    @Param {value:"keys:The keys of the sorted sets to be intersected"}
    @Return {value:"The number of elements in the resulting sorted set"}
    native action zInterStore (string destination, string[] keys) (int);

    @Description {value:"Count the members in a sorted set within the given lexicographical range"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum lexicographical value of the range"}
    @Param {value:"max:The maximum lexicographical value of the range"}
    @Return {value:"The number of elements in the specified lexicographical value range"}
    native action zLexCount (string key, string min, string max) (int);

    @Description {value:"Return a range of members in a sorted set, by index"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum index of the range"}
    @Param {value:"max:The maximum index of the range"}
    @Return {value:"The number of elements in the specified index range"}
    native action zRange (string key, int min, int max) (string[]);

    @Description {value:"Return a range of members in a sorted set, by lexicographical range from lowest to highest"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum lexicographical value of the range"}
    @Param {value:"max:The maximum lexicographical value of the range"}
    @Return {value:"Array of members in the specified lexicographical value range ordered from lowest to highest"}
    native action zRangeByLex (string key, string min, string max) (string[]);

    @Description {value:"Return a range of members in a sorted set, by lexicographical range ordered from highest to
    lowest"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The lexicographical value of the range"}
    @Param {value:"max:The maximum lexicographical value of the range"}
    @Return {value:"Array of members in the specified lexicographical value range ordered from highest to lowest"}
    native action zRevRangeByLex (string key, string min, string max) (string[]);

    @Description {value:"Return a range of members in a sorted set, by score from lowest to highest"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum score of the range"}
    @Param {value:"max:The maximum score of the range"}
    @Return {value:"Array of members in the specified score range ordered from lowest to highest"}
    native action zRangeByScore (string key, float min, float max) (string[]);

    @Description {value:"Determine the index of a member in a sorted set"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"member:The member of which the index needs to be obtained"}
    @Return {value:"The index of the member"}
    native action zRank (string key, string member) (int);

    @Description {value:"Remove one or more members from a sorted set"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"members:The members to be removed"}
    @Return {value:"The number of members removed from the sorted set, not including non existing members"}
    native action zRem (string key, string[] members) (int);

    @Description {value:"Remove all members in a sorted set between the given lexicographical range"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum lexicographical value of the range"}
    @Param {value:"max:The maximum lexicographical value of the range"}
    @Return {value:"The number of members removed from the sorted set"}
    native action zRemRangeByLex (string key, string min, string max) (int);

    @Description {value:"Remove all members in a sorted set within the given indices"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum index of the range"}
    @Param {value:"max:The maximum index of the range"}
    @Return {value:"The number of members removed from the sorted set"}
    native action zRemRangeByRank (string key, int min, int max) (int);

    @Description {value:"Remove all members in a sorted set within the given scores"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum score of the range"}
    @Param {value:"max:The maximum score of the range"}
    @Return {value:"The number of members removed from the sorted set"}
    native action zRemRangeByScore (string key, float min, float max) (int);

    @Description {value:"Return a range of members in a sorted set, by index, ordered highest to lowest"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum index of the range"}
    @Param {value:"max:The maximum index of the range"}
    @Return {value:"The number of elements in the specified index range"}
    native action zRevRange (string key, int min, int max) (string[]);

    @Description {value:"Return a range of members in a sorted set, by score from highest to lowest"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"min:The minimum score of the range"}
    @Param {value:"max:The maximum score of the range"}
    @Return {value:"Array of members in the specified score range ordered from highest to lowest"}
    native action zRevRangeByScore (string key, float min, float max) (string[]);

    @Description {value:"Determine the index of a member in a sorted set"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"member:The member of which the index needs to be obtained"}
    @Return {value:"The index of the member"}
    native action zRevRank (string key, string member) (int);

    @Description {value:"Determine the score of a member in a sorted set"}
    @Param {value:"key:The key of the sorted set"}
    @Param {value:"member:The member of which the score needs to be obtained"}
    @Return {value:"The score of the member"}
    native action zScore (string key, string member) (float);

    @Description {value:"Return the union of multiple sorted sets"}
    @Param {value:"destination: The destination key of the resulting set"}
    @Param {value:"keys:Array of keys of sorted sets"}
    @Return {value:"Number of members of the resulting sorted set"}
    native action zUnionStore (string dest, string[] keys) (int);

    //Hash Commands

    @Description {value:"Delete one or more hash fields"}
    @Param {value:"key: The key of the hash"}
    @Param {value:"fields:Array of fields to be deleted"}
    @Return {value:"Number of fields that were removed from the hash, not including specified but non existing fields"}
    native action hDel (string key, string[] fields) (int);

    @Description {value:"Determine if a hash field exists"}
    @Param {value:"key: The key of the hash"}
    @Param {value:"field:Array of fields to be deleted"}
    @Return {value:"boolean true if the hash contains the field. boolean false if the hash does not contain
    field or key does not exist"}
    native action hExists (string key, string field) (boolean);

    @Description {value:"Get the value of a hash field"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"field:The field"}
    @Return {value:"The value of the field"}
    native action hGet (string key, string field) (string);

    @Description {value:"Get the all values of a hash"}
    @Param {value:"key:The key of the hash"}
    @Return {value:"Map of field-value pairs"}
    native action hGetAll (string key) (map);

    @Description {value:"Increment the integer value of a hash field by the given number"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"field:The field"}
    @Param {value:"amount:The amount to increment"}
    @Return {value:"The value of the field"}
    native action hIncrBy (string key, string field, int amount) (int);

    @Description {value:"Increment the float value of a hash field by the given number"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"field:The field"}
    @Param {value:"amount:The amount to increment"}
    @Return {value:"The value of the field"}
    native action hIncrByFloat (string key, string field, float amount) (float);

    @Description {value:"Get all the fields in a hash"}
    @Param {value:"key:The key of the hash"}
    @Return {value:"Array of hash fields"}
    native action hKeys (string key) (string[]);

    @Description {value:"Get the number of fields in a hash"}
    @Param {value:"key:The key of the hash"}
    @Return {value:"Number of fields"}
    native action hLen (string key) (int);

    @Description {value:"Get the values of all the given hash fields"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"fields:Array of hash fields"}
    @Return {value:"Map of field-value pairs"}
    native action hMGet (string key, string[] fields) (map);

    @Description {value:"Set multiple hash fields to multiple values"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"fieldValueMap:Map of field-value pairs"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action hMSet (string key, map fieldValueMap) (string);

    @Description {value:"Set the string value of a hash field"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"field:The field"}
    @Param {value:"value:The value to be set to the field"}
    @Return {value:"boolean true if field is a new field in the hash and value was set. boolean false if
     field already exists in the hash and the value was updated"}
    native action hSet (string key, string field, string value) (boolean);

    @Description {value:"Set the string value of a hash field, only if the field does not exist"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"field:The field"}
    @Param {value:"value:The value to be set to the field"}
    @Return {value:"boolean true if field is a new field in the hash and value was set. boolean false if
     field already exists in the hash and no operation was performed"}
    native action hSetNx (string key, string field, string value) (boolean);

    @Description {value:"Get the string length of the field value in a hash"}
    @Param {value:"key:The key of the hash"}
    @Param {value:"field:The field"}
    @Return {value:"length of the field value, or 0 when field is not present in the hash or key does
     not exist at all"}
    native action hStrln (string key, string field) (int);

    @Description {value:"Get all the values in a hash"}
    @Param {value:"key:The key of the hash"}
    @Return {value:"Array of values in the hash, or an empty array when key does not exist"}
    native action hVals (string key) (string[]);

    //Key Commands

    @Description {value:"Delete one or more keys"}
    @Param {value:"keys:The key to be deleted"}
    @Return {value:"The number of keys that were removed"}
    native action del(string[] keys) (int);

    @Description {value:"Determine how many keys exist"}
    @Param {value:"keys:The keys of which existence to be found out"}
    @Return {value:"The number of existing keys"}
    native action exists(string[] keys) (int);

    @Description {value:"Set a key's time to live in seconds"}
    @Param {value:"key:The keys of which expiry time to be set"}
    @Param {value:"seconds:Expiry in seconds"}
    @Return {value:"true if the timeout was set. false if key does not exist or the timeout could not be set"}
    native action expire(string key, int seconds) (boolean);

    @Description {value:"Find all keys matching the given pattern"}
    @Param {value:"pattern:The pattern to match"}
    @Return {value:"Array of keys matching the given pattern"}
    native action keys(string pattern) (string[]);

    @Description {value:"Move a key to another database"}
    @Param {value:"key:The key to be moved"}
    @Param {value:"database:The database to which the key needs to be moved"}
    @Return {value:"boolean true if key was succesfully moved, boolean false otherwise"}
    native action move(string key, int database) (boolean);

    @Description {value:"Remove the expiration from a key"}
    @Param {value:"key:The key of which expiry time should be removed"}
    @Return {value:"boolean true if the timeout was removed. boolean false if key does not exist or does not have
     an associated timeout"}
    native action persist(string key) (boolean);

    @Description {value:"Set a key's time to live in millisecondsy"}
    @Param {value:"key:The key of which expiry time should be removed"}
    @Param {value:"timeMilliSeconds:The expiry time in milli seconds"}
    @Return {value:"boolean true if the timeout was set. boolean false if key does not exist or the timeout could not
     be set"}
    native action pExpire(string key, int timeMilliSeconds) (boolean);

    @Description {value:"Get the time to live for a key in milliseconds"}
    @Param {value:"key:The key of which time-to-live should be obtained"}
    @Return {value:"time-to-live of the key, in milli seconds"}
    native action pTtl(string key) (int);

    @Description {value:"Return a random key from the keyspace"}
    @Return {value:"The random key, or null when the database is empty"}
    native action randomKey() (string);

    @Description {value:"Rename a key"}
    @Param {value:"key:The key to be renamed"}
    @Param {value:"newName:The new name of the key"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action rename(string key, string newName) (string);

    @Description {value:"Rename a key, only if the new key does not exist"}
    @Param {value:"key:The key to be renamed"}
    @Param {value:"newName:The new name of the key"}
    @Return {value:"boolean true if key was renamed to newkey. boolean false if newkey already exists"}
    native action renameNx(string key, string newName) (boolean );

    @Description {value:"Sort the elements in a list, set or sorted set"}
    @Param {value:"key:The key of the data structure to be sorted"}
    @Return {value:"Sorted array containing the members of the sorted data structure"}
    native action sort(string key) (string[]);

    @Description {value:"Get the time to live for a key"}
    @Param {value:"key:The key of which the time to live needs to be obtained"}
    @Return {value:"Time to live in seconds or a negative value in order to signal an error"}
    native action ttl(string key) (int);

    @Description {value:"Determine the type stored at key"}
    @Param {value:"key:The key of which the type needs to be obtained"}
    @Return {value:"Time to live in seconds or a negative value in order to signal an error"}
    native action redisType(string key) (string);

    //Connection commands

    @Description {value:"Authenticate to the server"}
    @Param {value:"password:The password"}
    @Return {value:"A string with the value \"OK\" if the operation was successful"}
    native action auth(string password) (string);

    @Description {value:"Echo the given string"}
    @Param {value:"message:The message to be echo-ed"}
    @Return {value:"The message itself if the operation was successful"}
    native action echo(string message) (string);

    @Description {value:"Ping the server"}
    @Return {value:"A string with the value \"PONG\" if the operation was successful"}
    native action ping() (string);

    @Description {value:"Close the connection"}
    @Return {value:"A string with the value \"OK\""}
    native action quit () (string);
}
