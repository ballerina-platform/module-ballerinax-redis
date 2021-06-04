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
        io:println("String value inserted");    // setResult is "OK"
    } else {
        io:println("Error while setting");
    }
    
    var appendResult = conn->append("Language", "New");
    if (appendResult is int) {
        io:println("Length of string appended: " + appendResult.toString());    // appendResult is "11" length of appended string
    } else {
        io:println("Error while appending");
    }

    var getResult = conn->get("Language");
    if (getResult is string) {
        io:println(getResult);  // getResult is "BallerinaNew"
    } else if (result is ()) {
        io:println("Key not found");
    } else {
        io:println("Error while fetching");
    }

    conn.stop();
}
