import ballerinax/redis;
import ballerina/io;

redis:ClientEndpointConfiguration redisConfig = {
    host: "localhost:6379",
    password: "",
    options: { connectionPooling: true, isClusterConnection: false, ssl: false,
        startTls: false, verifyPeer: false }
};

public function main() returns error? {

    redis:Client conn = check new (redisConfig);

    var setResult = conn->set("Language", "Ballerina");
    if (setResult is string) {
        io:println("String value inserted" + setResult);    // setResult is "OK"
    } else {
        io:println("Error while set");
    }

    var getResult = conn->get("Language");
    if (getResult is string) {
        io:println(getResult);  // getResult is "Ballerina"
    } else if (getResult is ()) {
        io:println("Key not found");
    } else {
        io:println("Error while fetching");
    }

    conn.stop();
}
