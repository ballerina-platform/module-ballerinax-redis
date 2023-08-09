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

    var result = conn->decr("decrKey");
    if (result is int) {
        io:println(result);
    } else {
        io:println("Error in decrementing");
    }

    result = conn->decrBy("decrByKey", 3);
    if (result is int) {
        io:println(result);
    } else {
        io:println("Error in decrementing");
    }

    conn.stop();
}
