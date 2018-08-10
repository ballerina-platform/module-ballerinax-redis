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

documentation {
    Represents Redis client endpoint.
}
public type Client object {

    private ClientEndpointConfiguration clientEndpointConfig;
    private CallerActions callerActions;

    documentation {
        Gets called when the endpoint is being initialized during the package initialization.
    }
    public function init(ClientEndpointConfiguration config) {
        self.callerActions = createClient(config);
    }

    documentation {
        Returns the CallerActions object that client code uses.

        R{{}} The CallerActions object  that client code uses
    }
    public function getCallerActions() returns CallerActions {
        return self.callerActions;
    }

    documentation {
        Stops the registered service.
    }
    public function stop() {
        close(self.callerActions);
    }
};

documentation {
	An internal function used by clients to shutdown the connection/connection pool(if pooling enabled).

	P{{callerActions}} CallerActions object that encapsulates the connection/connection pool
}
extern function close (CallerActions callerActions);

extern function createClient(ClientEndpointConfiguration clientEndpointConfig) returns CallerActions;

documentation {
    The Client endpoint configuration for Redis databases.

    F{{host}} The host of the Redis database
    F{{password}} Password for the database connection
    F{{options}} Properties for the connection configuration
}
public type ClientEndpointConfiguration record {
    string host,
    string password,
    Options options,
};

documentation {
    Connection options for Redis Client Endpoint.

    F{{clientName}} The clientName of the connection
    F{{connectionPooling}} Boolean value depending on whether the connection
    pooling is enabled or not
    F{{ssl}} Boolean value depending on whether SSL is enabled or not
    F{{startTls}} Boolean value depending on whether startTLS is enabled or not
    F{{verifyPeer}} Boolean value depending on whether peer verification is
    enabled or not
    F{{database}} The database to be used with the connection
    F{{connectionTimeout}} The timeout value for the connection
}
public type Options record {
    string clientName,

    boolean connectionPooling,
    boolean isClusterConnection,
    boolean ssl,
    boolean startTls,
    boolean verifyPeer,

    int database = -1,
    int connectionTimeout = -1,
};
