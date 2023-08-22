import ballerinax/redis;
import ballerina/io;

redis:ConnectionConfig redisConfig = {
    host: "localhost:6379",
    password: "",
    options: { connectionPooling: true, isClusterConnection: false, ssl: false,
        startTls: false, verifyPeer: false }
};

public function main() returns error? {

    redis:Client conn = check new (redisConfig);
    
    var listPushresult = conn->lPush("NumberList", ["One", "Two"]);
    if (listPushresult is int) {    // listPushresult is "2" number of elements pushed to a list
        io:println("Number of elements pushed: " + listPushresult.toString());
    } else {
        io:println("Error occurred while calling `lPush`");
    }

    var lPopResult = conn->lPop("NumberList");
    if (lPopResult is string) {
        io:println("Popped element: " + lPopResult);    // lPopResult is "Two"
    } else if (lPopResult is ()) {
        io:println("Key not found");
    } else {
        io:println("Error occurred while calling `lPop`");
    }

    conn.stop();
}
