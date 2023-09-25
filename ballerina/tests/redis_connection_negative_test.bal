import ballerina/test;

@test:Config {
}
public function testConnectionsWithInvalidHost() returns error? {
    ConnectionConfig redisConfig = {
        host: "mylocalhost",
        password: "",
        options: {
            connectionPooling: true,
            isClusterConnection: false,
            ssl: false,
            startTls: false,
            verifyPeer: false
        }
    };

    Client|error testConn = new (redisConfig);
    test:assertTrue(testConn is Client);
    if testConn is Client {
        string|error setResult = testConn->set("key", "value");
        test:assertTrue(setResult is error, "testConnections: Result is unexpected");
        test:assertTrue((<error>setResult).message().includes("Error occurred while obtaining connection from the pool"), "testConnections: Result is unexpected");
    }
}

@test:Config {
}
public function testConnectionsWithMultipleHosts() {
    ConnectionConfig redisConfig = {
        host: "mylocalhost, localhost",
        password: ""
    };
    Client|error testConn = trap new (redisConfig);
    test:assertTrue(testConn is error);
    test:assertTrue((<error>testConn).detail().toString().includes("More than one hosts have been provided for a non-cluster connection"), "testConnectionsWithMultipleHosts: Result is unexpected");

}

@test:Config {
}
public function testConnectionsWithInvalidPort() {
    ConnectionConfig redisConfig = {
        host: "localhost:port",
        password: ""
    };
    Client|error testConn = trap new (redisConfig);
    test:assertTrue(testConn is error);
    test:assertTrue((<error>testConn).detail().toString().includes("the port of the host string must be an integer"), "testConnectionsWithMultipleHosts: Result is unexpected");
}
