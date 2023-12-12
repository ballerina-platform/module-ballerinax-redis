// Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org)
//
// WSO2 LLC. licenses this file to you under the Apache License,
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

import ballerina/test;

ConnectionConfig redisConfig = {
    host: "localhost",
    password: "",
    options: {
        connectionPooling: true,
        isClusterConnection: false,
        ssl: false,
        startTls: false,
        verifyPeer: false,
        connectionTimeout: 500
    }
};

Client redis = check new (redisConfig);

@test:BeforeSuite
public function initDb() {
    setupStringValues();
    setupKeyValues();
    setupListValues();
    setupHashValues();
    setupSetValues();
    setupSortedSetValues();
}

function setupStringValues() {

    string[] keyArray = [
        "GetTestKey",
        "AppendTestKey",
        "BitCountTestKey",
        "testBitOpKey1",
        "testBitOpKey2",
        "testDecrKey",
        "testDecrByKey",
        "testGetBitKey",
        "testGetRangekey",
        "testGetSetKey",
        "testIncrKey",
        "testIncrByKey",
        "testIncrByFloatKey",
        "testSetBitKey",
        "testSetExKey",
        "testSetRangeKey",
        "testStrlnKey",
        "testMGetKey1",
        "testMGetKey2",
        "testMSetNxKey1",
        "testPSetExKey"
    ];

    string[] valueArray = [
        "GetTestValue",
        "AppendTestValue",
        "BitCountTestValue",
        "101",
        "100",
        "8",
        "10",
        "testGetBitValue",
        "1234567",
        "testGetSetValue",
        "6",
        "5",
        "7",
        "10101",
        "testSetExValue",
        "testSetRangeValue",
        "testStrlnValue",
        "testMGetValue1",
        "testMGetValue2",
        "testMSetNxValue1",
        "testSetExValue"
    ];

    foreach int i in 0 ..< keyArray.length() {
        string|error setResult = redis->set(keyArray[i], valueArray[i]);
        if setResult is error {
            test:assertFail(string `failed to set key: ${keyArray[i]} with value: ${valueArray[i]} due to: ${setResult.message()}`);
        }
    }
}

function setupKeyValues() {
    string[] keyArray = [
        "testDelKey1",
        "testDelKey2",
        "testDelKey3",
        "testExistsKey",
        "testExpireKey",
        "testKeysKey1",
        "testKeysKey2",
        "testKeysKey3",
        "testMoveKey",
        "testPersistKey",
        "testPExpireKey",
        "testPTtlKey",
        "testRenameKey",
        "testRenameNxKey",
        "testRenameNxKey1",
        "testRenameNxKeyExisting",
        "testTypeKey"
    ];

    string[] valueArray = [
        "testDelValue1",
        "testDelValue2",
        "testDelValue3",
        "testExistsValue",
        "testExpireValue",
        "testKeysValue1",
        "testKeysValue2",
        "testKeysValue3",
        "testMoveValue4",
        "testPersistValue",
        "testPExpireValue",
        "testPTtlValue",
        "testRenameValue",
        "testRenameNxValue",
        "testRenameNxValue1",
        "testRenameNxValueExisting",
        "testTypeValue"
    ];

    foreach int i in 0 ..< keyArray.length() {
        string|error setResult = redis->set(keyArray[i], valueArray[i]);
        if setResult is error {
            test:assertFail(string `failed to set key: ${keyArray[i]} with value: ${valueArray[i]} due to: ${setResult.message()}`);
        }
    }

    string listKey = "testSortKey";
    string[] listValues = ["2", "3", "1", "4", "8", "0"];
    int|error lPushResult = redis->lPush(listKey, listValues);
    if lPushResult is error {
        test:assertFail(string `failed to push values to list with key: ${listKey} due to: ${lPushResult.message()}`);
    }
}

function setupListValues() {
    string[] keyArray = [
        "testBLPopKey",
        "testBRPopKey",
        "testLIndexKey",
        "testLInsertKey",
        "testLLenKey",
        "testLPopKey",
        "testLPushKey",
        "testLPushXKey",
        "testLRangeKey",
        "testLRemKey",
        "testLSetKey",
        "testLTrimKey",
        "testRPopKey",
        "testRPopLPushKey1",
        "testRPopLPushKey2",
        "testRPushKey",
        "testRPushXKey"
    ];
    string[][] valueArray = [
        ["testBLPopValue1", "testBLPopValue2"],
        ["testBRPopValue1", "testBRPopValue2"],
        ["testLIndexValue1", "testLIndexValue2"],
        ["testLInsertValue1", "testLInsertValue2", "pivotValue", "testLInsertValue4"],
        ["testLLenValue1", "testLLenValue2", "testLLenValue3"],
        ["testLPopValue1", "testLPopValue2"],
        ["testLPushValue1"],
        ["testLPushXValue1"],
        ["0", "1", "2", "3", "4"],
        ["testLRemValue1", "toBeRemovedValue", "toBeRemovedValue", "testLRemValue4"],
        ["testLSetValue1", "testLSetValue2", "testLSetValue3"],
        ["One", "Two", "Three"],
        ["testRPopValue1", "testRPopValue2"],
        ["One", "Two", "Three"],
        ["One", "Two"],
        ["testRPushValue1"],
        ["testRPushXValue1"]
    ];

    foreach int i in 0 ..< keyArray.length() {
        int|error pushResult = redis->lPush(keyArray[i], valueArray[i]);
        if pushResult is error {
            test:assertFail(string `failed to push list with key: ${keyArray[i]} due to: ${pushResult.message()}`);
        }
    }
}

function setupHashValues() {
    string[] hashKeyArray = [
        "testHDelKey",
        "testHGetKey",
        "testHExistsKey",
        "testHGetAllKey",
        "testHIncrByKey",
        "testHIncrByFloatKey",
        "testHKeysKey",
        "testHLenKey",
        "testHMGetKey",
        "testHStrlnKey",
        "testHValsKey"
    ];

    string[][] hashFieldArray = [
        ["testHDelField1", "testHDelField2", "testHDelField3"],
        ["testHGetField1", "testHGetField2"],
        ["testHExistsField1", "testHExistsField2"],
        ["testHGetAllField1", "testHGetAllField2"],
        ["testHIncrByField1"],
        ["testHIncrByFloatField1"],
        ["testHKeysField1", "testHKeysField2", "testHKeysField3"],
        ["testHLenField1", "testHLenField2", "testHLenField3"],
        ["testHMGetField1", "testHMGetField2", "testHMGetField3"],
        ["testHStrlnField1"],
        ["testHValsField1", "testHValsField2", "testHValsField3"]
    ];

    string[][] hashValueArray = [
        ["testHDelValue1", "testHDelValue2", "testHDelValue3"],
        ["testHGetValue1", "testHGetValue2"],
        ["testHExistsValue1", "testHExistsValue2"],
        ["testHGetAllValue1", "testHGetAllValue2"],
        ["6"],
        ["7"],
        ["testHKeysValue1", "testHKeysValue2", "testHKeysValue3"],
        ["testHLenValue1", "testHLenValue2", "testHLenValue3"],
        ["testHMGetValue1", "testHMGetValue2", "testHMGetValue3"],
        ["testHStrlnValue1"],
        ["testHValsValue1", "testHValsValue2", "testHValsValue3"]
    ];

    foreach int i in 0 ..< hashKeyArray.length() {
        map<string> hashValueMap = {};
        foreach int j in 0 ..< hashFieldArray[i].length() {
            hashValueMap[hashFieldArray[i][j]] = hashValueArray[i][j];
        }

        string|error hMSetResult = redis->hMSet(hashKeyArray[i], hashValueMap);
        if hMSetResult is error {
            test:assertFail(string `failed to add hash with key: ${hashKeyArray[i]} due to: ${hMSetResult.message()}`);
        }
    }
}

function setupSetValues() {
    string[] keyArray = [
        "testSAddKey",
        "testSDiffKey1",
        "testSDiffKey2",
        "testSInterKey1",
        "testSInterKey2",
        "testSIsMemberKey",
        "testSMembersKey",
        "testSPopKey",
        "testSRandMemberKey",
        "testSRemKey",
        "testUnionKey1",
        "testUnionKey2"

    ];

    string[][] valueArray = [
        ["testSAddValue1", "testSAddValue2"],
        ["One", "Two", "Three", "Four"],
        ["One", "Two"],
        ["One", "Two", "Three", "Four"],
        ["One", "Two"],
        ["testSIsMemberValue"],
        ["testSMembersValue1", "testSMembersValue2", "testSMembersValue3"],
        ["testSPopValue1", "testSPopValue2", "testSPopValue3"],
        ["testSRandMemberValue1", "testSRandMemberValue2", "testSRandMemberValue3"],
        ["testSRemValue1", "testSRemValue2", "testSRemValue3"],
        ["testUnionValue1", "testUnionValue2"],
        ["testUnionValue2", "testUnionValue3", "testUnionValue4"]
    ];

    foreach int i in 0 ..< keyArray.length() {
        int|error setResult = redis->sAdd(keyArray[i], valueArray[i]);
        if setResult is error {
            test:assertFail(string `failed to add set with key: ${keyArray[i]}} due to: ${setResult.message()}`);
        }
    }
}

function setupSortedSetValues() {
    string[] keyArray = [
        "testZAddKey",
        "testZCardKey",
        "testZCountKey",
        "testZIncrByKey",
        "testZInterStoreKey1",
        "testZInterStoreKey2",
        "testZInterStoreDestKey",
        "testZLexCountKey",
        "testZRangeKey",
        "testZRankKey",
        "testZRemKey",
        "testZRemRangeByLexKey",
        "testZRemRangeByRankKey",
        "testZRemRangeByScoreKey",
        "testZRangeByLexKey",
        "testZScoreKey",
        "testZUnionStoreKey1",
        "testZUnionStoreKey2"
    ];

    map<float>[] memberScoreMap = [];

    // ZAdd values
    map<float> zAddMap = {};
    zAddMap["testZAddValue1"] = 1;

    // ZCard values
    map<float> zCardMap = {};
    zCardMap["testZCardValue1"] = 1;
    zCardMap["testZCardValue2"] = 1;

    // ZCount values
    map<float> zCountMap = {};
    zCountMap["testZCountValue1"] = 1;
    zCountMap["testZCountValue2"] = 2;
    zCountMap["testZCountValue3"] = 2;
    zCountMap["testZCountValue4"] = 4;

    // ZIncrBy values
    map<float> zIncrByMap = {};
    zIncrByMap["testZIncrByValue1"] = 6;

    // ZInterStore values 1
    map<float> zInterStoreMap1 = {};
    zInterStoreMap1["One"] = 1;
    zInterStoreMap1["Six"] = 6;
    zInterStoreMap1["Three"] = 3;

    // ZInterStore values 2
    // TODO - support duplicate entries
    map<float> zInterStoreMap2 = {};
    zInterStoreMap2["One"] = 1;
    zInterStoreMap2["One"] = 2;
    zInterStoreMap2["Six"] = 6;

    // ZInterStoreDest values 
    map<float> zInterStoreDestMap = {};
    zInterStoreDestMap["Seven"] = 7;

    // ZLexCount values
    map<float> zLexCountMap = {};
    zLexCountMap["a"] = 1;
    zLexCountMap["b"] = 1;
    zLexCountMap["c"] = 1;
    zLexCountMap["d"] = 1;
    zLexCountMap["e"] = 1;
    zLexCountMap["f"] = 1;
    zLexCountMap["g"] = 1;

    // ZRange values
    map<float> zRangeMap = {};
    zRangeMap["a"] = 1;
    zRangeMap["b"] = 2;
    zRangeMap["c"] = 5;
    zRangeMap["d"] = 1;
    zRangeMap["e"] = 3;
    zRangeMap["f"] = 4;
    zRangeMap["g"] = 8;

    // ZRank values
    map<float> zRankMap = {};
    zRankMap["a"] = 1;
    zRankMap["b"] = 2;
    zRankMap["c"] = 5;

    // ZRem values
    map<float> zRemMap = {};
    zRemMap["a"] = 1;
    zRemMap["b"] = 2;
    zRemMap["c"] = 5;

    // ZRemRangeByLex values
    map<float> zRemRangeByLexMap = {};
    zRemRangeByLexMap["foo"] = 1;
    zRemRangeByLexMap["zap"] = 1;
    zRemRangeByLexMap["zip"] = 1;
    zRemRangeByLexMap["ALPHA"] = 1;
    zRemRangeByLexMap["alpha"] = 1;
    zRemRangeByLexMap["aaaa"] = 1;
    zRemRangeByLexMap["b"] = 1;
    zRemRangeByLexMap["c"] = 1;
    zRemRangeByLexMap["d"] = 1;
    zRemRangeByLexMap["e"] = 1;

    // ZRemRangeByRank values
    map<float> zRemRangeByRankMap = {};
    zRemRangeByRankMap["One"] = 1;
    zRemRangeByRankMap["Two"] = 2;
    zRemRangeByRankMap["Three"] = 2;
    zRemRangeByRankMap["Four"] = 3;
    zRemRangeByRankMap["Five"] = 5;
    zRemRangeByRankMap["Six"] = 6;

    // ZRemRangeByScore values
    map<float> zRemRangeByScoreMap = {};
    zRemRangeByScoreMap["One"] = 1;
    zRemRangeByScoreMap["Two"] = 2;
    zRemRangeByScoreMap["Three"] = 2;
    zRemRangeByScoreMap["Four"] = 3;
    zRemRangeByScoreMap["Five"] = 5;
    zRemRangeByScoreMap["Six"] = 6;

    // ZRangeByLex values
    map<float> zRangeByLexMap = {};
    zRangeByLexMap["a"] = 1;
    zRangeByLexMap["b"] = 1;
    zRangeByLexMap["c"] = 1;
    zRangeByLexMap["d"] = 1;
    zRangeByLexMap["e"] = 1;
    zRangeByLexMap["f"] = 1;
    zRangeByLexMap["g"] = 1;

    // ZScore values
    map<float> zScoreMap = {};
    zScoreMap["a"] = 1.2;
    zScoreMap["b"] = 2.3;

    // ZUnionStore values 1
    // TODO: modify the "zAdd" function to accept a list instead of a map to support duplicate entries 
    map<float> zUnionStoreMap1 = {};
    zUnionStoreMap1["a"] = 1;
    zUnionStoreMap1["b"] = 2;
    zUnionStoreMap1["c"] = 5;
    zUnionStoreMap1["d"] = 1;

    // ZUnionStore values 2
    map<float> zUnionStoreMap2 = {};
    zUnionStoreMap2["a"] = 1;
    zUnionStoreMap2["f"] = 4;
    zUnionStoreMap2["g"] = 3;

    memberScoreMap.push(zAddMap);
    memberScoreMap.push(zCardMap);
    memberScoreMap.push(zCountMap);
    memberScoreMap.push(zIncrByMap);
    memberScoreMap.push(zInterStoreMap1);
    memberScoreMap.push(zInterStoreMap2);
    memberScoreMap.push(zInterStoreDestMap);
    memberScoreMap.push(zLexCountMap);
    memberScoreMap.push(zRangeMap);
    memberScoreMap.push(zRankMap);
    memberScoreMap.push(zRemMap);
    memberScoreMap.push(zRemRangeByLexMap);
    memberScoreMap.push(zRemRangeByRankMap);
    memberScoreMap.push(zRemRangeByScoreMap);
    memberScoreMap.push(zRangeByLexMap);
    memberScoreMap.push(zScoreMap);
    memberScoreMap.push(zUnionStoreMap1);
    memberScoreMap.push(zUnionStoreMap2);

    foreach int i in 0 ..< memberScoreMap.length() {
        int|error result = redis->zAdd(keyArray[i], memberScoreMap[i]);
        if result is error {
            test:assertFail(string `failed to insert the sorted set with key: ${keyArray[i]}: due to: ${result.message()}`);
        }
    }
}
