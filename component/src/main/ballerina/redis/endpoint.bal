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

# Represents Redis client endpoint.
public type Client object {

    private ClientEndpointConfiguration clientEndpointConfig;
    private CallerActions callerActions;

    # Gets called when the endpoint is being initialized during the module initialization.
    public function init(ClientEndpointConfiguration config) {
        self.callerActions = createClient(config);
    }

    # Returns the CallerActions object that client code uses.
    #
    # + return - The CallerActions object  that client code uses
    public function getCallerActions() returns CallerActions {
        return self.callerActions;
    }

    # Stops the registered service.
    public function stop() {
        close(self.callerActions);
    }
};

# An internal function used by clients to shutdown the connection/connection pool(if pooling enabled).
#
# + callerActions - CallerActions object that encapsulates the connection/connection pool
extern function close (CallerActions callerActions);

extern function createClient(ClientEndpointConfiguration clientEndpointConfig) returns CallerActions;

# The Client endpoint configuration for Redis databases.
#
# + host - The host of the Redis database
# + password - Password for the database connection
# + options - Properties for the connection configuration
public type ClientEndpointConfiguration record {
    string host;
    string password;
    Options options;
};

# Connection options for Redis Client Endpoint.
#
# + clientName - The clientName of the connection
# + connectionPooling - Boolean value depending on whether the connection
#   pooling is enabled or not
# + isClusterConnection - Whether to enable cluster connection or not
# + ssl - Boolean value depending on whether SSL is enabled or not
# + startTls - Boolean value depending on whether startTLS is enabled or not
# + verifyPeer - Boolean value depending on whether peer verification is
#   enabled or not
# + database - The database to be used with the connection
# + connectionTimeout - The timeout value for the connection
public type Options record {
    string clientName;
    boolean connectionPooling;
    boolean isClusterConnection;
    boolean ssl;
    boolean startTls;
    boolean verifyPeer;
    int database = -1;
    int connectionTimeout = -1;
};
