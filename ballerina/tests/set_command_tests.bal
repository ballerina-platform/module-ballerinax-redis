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
import ballerina/test;

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSAdd() returns error? {
    int sAddResult = check redis->sAdd("testSAddKey", ["testSAddValue3", "testSAddValue4", "testSAddValue5"]);
    test:assertEquals(sAddResult, 3);

    boolean sIsMemberResult = check redis->sIsMember("testSAddKey", "testSAddValue3");
    test:assertTrue(sIsMemberResult);
    boolean sIsMemberResult2 = check redis->sIsMember("testSAddKey", "testSAddValue4");
    test:assertTrue(sIsMemberResult2);
    boolean sIsMemberResult3 = check redis->sIsMember("testSAddKey", "testSAddValue5");
    test:assertTrue(sIsMemberResult3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSCard() returns error? {
    int sCardResult = check redis->sCard("testSCardKey");
    test:assertEquals(sCardResult, 2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSDiff() returns error? {
    string[] result = check redis->sDiff(["{SetTag}testSDiffKey1", "{SetTag}testSDiffKey2"]);
    test:assertEquals(result.length(), 2);
    test:assertTrue((result[0] == "Three" && result[1] == "Four") || (result[0] == "Four" && result[1] == "Three"));
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSDiffStore() returns error? {
    int sDiffResult = check redis->sDiffStore("{SetTag}testSDiffStoreDestKey", ["{SetTag}testSDiffKey1", "{SetTag}testSDiffKey2"]);
    test:assertEquals(sDiffResult, 2);

    boolean sIsMemberResult = check redis->sIsMember("{SetTag}testSDiffStoreDestKey", "Three");
    test:assertTrue(sIsMemberResult);
    boolean sIsMemberResult2 = check redis->sIsMember("{SetTag}testSDiffStoreDestKey", "Four");
    test:assertTrue(sIsMemberResult2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSInter() returns error? {
    string[] result = check redis->sInter(["{SetTag}testSInterKey1", "{SetTag}testSInterKey2"]);
    test:assertTrue((result[0] == "One" && result[1] == "Two") || (result[0] == "Two" && result[1] == "One"));
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSInterStore() returns error? {
    int result = check redis->sInterStore("{SetTag}testSInterDestKey", ["{SetTag}testSInterKey1", "{SetTag}testSInterKey2"]);
    test:assertEquals(result, 2);

    boolean sIsMemberResult = check redis->sIsMember("{SetTag}testSInterDestKey", "One");
    test:assertTrue(sIsMemberResult);
    boolean sIsMemberResult2 = check redis->sIsMember("{SetTag}testSInterDestKey", "Two");
    test:assertTrue(sIsMemberResult2);
    boolean sIsMemberResult3 = check redis->sIsMember("{SetTag}testSInterDestKey", "Three");
    test:assertFalse(sIsMemberResult3);
    boolean sIsMemberResult4 = check redis->sIsMember("{SetTag}testSInterDestKey", "Four");
    test:assertFalse(sIsMemberResult4);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSIsMember() returns error? {
    boolean result = check redis->sIsMember("testSIsMemberKey", "testSIsMemberValue");
    test:assertTrue(result);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSMembers() returns error? {
    string[] result = check redis->sMembers("testSMembersKey");

    test:assertEquals(result.length(), 3);
    boolean allMembersRetrieved = true;
    string[] memberArray = ["testSMembersValue1", "testSMembersValue2", "testSMembersValue3"];
    foreach var m in memberArray {
        boolean memberExists = false;
        foreach var r in result {
            if (m == r) {
                memberExists = true;
                break;
            }
        }
        if (!memberExists) {
            allMembersRetrieved = false;
            break;
        }
    }
    test:assertTrue(allMembersRetrieved);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSMove() returns error? {
    boolean sIsMemberResult = check redis->sIsMember("{SetTag}testSMoveKey1", "testSMoveValue");
    test:assertTrue(sIsMemberResult);
    boolean sIsMemberResult2 = check redis->sIsMember("{SetTag}testSMoveKey2", "testSMoveValue");
    test:assertFalse(sIsMemberResult2);

    boolean moveResult = check redis->sMove("{SetTag}testSMoveKey1", "{SetTag}testSMoveKey2", "testSMoveValue");
    test:assertTrue(moveResult);

    boolean sIsMemberResult3 = check redis->sIsMember("{SetTag}testSMoveKey1", "testSMoveValue");
    test:assertFalse(sIsMemberResult3);
    boolean sIsMemberResult4 = check redis->sIsMember("{SetTag}testSMoveKey2", "testSMoveValue");
    test:assertTrue(sIsMemberResult4);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSPop() returns error? {
    string[]? result = check redis->sPop("testSPopKey", 2);
    if result is () {
        test:assertFail("key not found");
    }
    test:assertEquals(result.length(), 2);

    string[] memberArray = ["testSPopValue1", "testSPopValue2", "testSPopValue3"];
    test:assertTrue(result.every(function(string m) returns boolean {
        return memberArray.indexOf(m) != ();
    }));

    boolean sIsMemberResult = check redis->sIsMember("testSPopKey", result[0]);
    test:assertFalse(sIsMemberResult);
    boolean sIsMemberResult2 = check redis->sIsMember("testSPopKey", result[1]);
    test:assertFalse(sIsMemberResult2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSRandMember() returns error? {
    string[] result = check redis->sRandMember("testSRandMemberKey", 2);

    test:assertEquals(result.length(), 2);
    boolean allMembersPopped = true;
    string[] memberArray = ["testSRandMemberValue1", "testSRandMemberValue2", "testSRandMemberValue3"];
    foreach var r in result {
        boolean memberExists = false;
        foreach var m in memberArray {
            if (r == m) {
                memberExists = true;
                break;
            }
        }
        if (!memberExists) {
            allMembersPopped = false;
            break;
        }
    }
    test:assertTrue(allMembersPopped);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSRem() returns error? {
    int result = check redis->sRem("testSRemKey", ["testSRemValue1", "testSRemValue3"]);
    test:assertEquals(result, 2);

    boolean sIsMemberResult = check redis->sIsMember("testSRemKey", "testSRemValue1");
    test:assertFalse(sIsMemberResult);
    boolean sIsMemberResult2 = check redis->sIsMember("testSRemKey", "testSRemValue3");
    test:assertFalse(sIsMemberResult2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSUnion() returns error? {
    string[] result = check redis->sUnion(["{SetTag}testUnionKey1", "{SetTag}testUnionKey2"]);

    test:assertEquals(result.length(), 4);
    boolean allUnionMembersExist = true;
    string[] unionArray = ["testUnionValue1", "testUnionValue2", "testUnionValue3", "testUnionValue4"];
    foreach var r in result {
        boolean memberExists = false;
        foreach var u in unionArray {
            if (r == u) {
                memberExists = true;
                break;
            }
        }
        if (!memberExists) {
            allUnionMembersExist = false;
            break;
        }
    }
    test:assertTrue(allUnionMembersExist);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSUnionStore() returns error? {
    int result = check redis->sUnionStore("{SetTag}testSUnionStoreDestKey", ["{SetTag}testUnionKey1", "{SetTag}testUnionKey2"]);
    test:assertEquals(result, 4);

    boolean sIsMemberResult1 = check redis->sIsMember("{SetTag}testSUnionStoreDestKey", "testUnionValue1");
    test:assertTrue(sIsMemberResult1);
    boolean sIsMemberResult2 = check redis->sIsMember("{SetTag}testSUnionStoreDestKey", "testUnionValue2");
    test:assertTrue(sIsMemberResult2);
    boolean sIsMemberResult3 = check redis->sIsMember("{SetTag}testSUnionStoreDestKey", "testUnionValue3");
    test:assertTrue(sIsMemberResult3);
    boolean sIsMemberResult4 = check redis->sIsMember("{SetTag}testSUnionStoreDestKey", "testUnionValue4");
    test:assertTrue(sIsMemberResult4);
}
