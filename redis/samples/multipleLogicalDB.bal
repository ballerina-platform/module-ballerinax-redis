import ballerinax/redis;
import ballerina/io;

redis:ClientEndpointConfiguration redisConfig1 = {
    host: "localhost:6379",
    password: "",
    options: { connectionPooling: true, isClusterConnection: false, ssl: false,
        startTls: false, verifyPeer: false }
};

redis:ClientEndpointConfiguration redisConfig2 = {
    host: "localhost:6379",
    password: "",
    options: { connectionPooling: true, isClusterConnection: false, ssl: false,
        startTls: false, verifyPeer: false, database: 1 }
};

public function main() returns error? {

    redis:Client conn1 = check new (redisConfig1);
    
    redis:Client conn2 = check new (redisConfig2);
    
    var result = conn1->set("Project1", "Ballerina");
    if (result is string) {
        io:println(result);
    } else {
        io:println("Error while set");
    }

    var result1 = conn1->exists(["Project1"]);
    if (result1 is int) {
        io:println(result1.toString());
    } else {
        io:println("Error while exists");
    }

    var result2 = conn2->exists(["Project1"]);
    if (result2 is int) {
        io:println(result2.toString());
    } else {
        io:println("Error while exists");
    }

    conn1.stop();
    conn2.stop();
}
