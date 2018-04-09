package ballerina.redis;

///////////////////////////////
// Redis Client Endpoint
/////////////////////////   //////

@Description {value:"Represents Redis client endpoint"}
@Field {value:"epName: The name of the endpoint"}
@Field {value:"config: The configurations associated with the endpoint"}
public type Client object {
    public {
        string epName;
        ClientEndpointConfiguration clientEndpointConfig;
        RedisClient redisClient;
    }

    @Description {value:"Gets called when the endpoint is being initialized during the package initialization."}
    public function init(ClientEndpointConfiguration clientEndpointConfig);

    public function register(typedesc serviceType) {
    }

    public function start() {
    }

    @Description {value:"Returns the connector that client code uses"}
    @Return {value:"The connector that client code uses"}
    public function getClient() returns RedisClient {
        return self.redisClient;
    }

    @Description {value:"Stops the registered service"}
    @Return {value:"Error occured during registration"}
    public function stop() {
    }
};

public native function createRedisClient(ClientEndpointConfiguration clientEndpointConfig) returns RedisClient;

public function Client::init(ClientEndpointConfiguration clientEndpointConfig) {
    self.redisClient = createRedisClient(clientEndpointConfig);
}

public type ClientEndpointConfiguration {
    string host = "",
    string password = "",
    ConnectionProperties options,
};

@Description {value:"Connection Properties for Redis connector"}
@Field {value:"clientName:The clientName of the connection"}
@Field {value:"poolingEnabled:Boolean value depending on whether the connection pooling is enabled or not"}
@Field {value:"sslEnabled:Boolean value depending on whether SSL is enabled or not"}
@Field {value:"startTlsEnabled:Boolean value depending on whether startTLS is enabled or not"}
@Field {value:"verifyPeerEnabled:Boolean value depending on whether peer verification is enabled or not"}
@Field {value:"database:The database to be used with the connection"}
@Field {value:"connectionTimeout:The timeout value for the connection"}
public type ConnectionProperties {
    string clientName,

    boolean poolingEnabled,
    boolean isClusterConnection,
    boolean sslEnabled,
    boolean startTlsEnabled,
    boolean verifyPeerEnabled,

    int database = -1,
    int connectionTimeout = -1,
};
