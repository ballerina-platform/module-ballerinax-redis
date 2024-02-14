// Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org) 
//
// WSO2 LLC. licenses this file to you under the Apache License,
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

# The client endpoint configuration for Redis.
#
# + connection - connection configuration for the Redis database
# + connectionPooling - whether connection pooling is enabled
# + isClusterConnection - whether the connection is a cluster connection
@display {label: "Connection Config"}
public type ConnectionConfig record {|
    @display {label: "Connection Type"}
    ConnectionString|ConnectionParams connection;
    @display {label: "Connection Pooling Enabled"}
    boolean connectionPooling = false;
    @display {label: "Cluster Mode Enabled"}
    boolean isClusterConnection = false;
|};

# The connection parameters based configurations.
#
# + host - host address of the Redis database
# + port - port of the Redis database 
# + password - The password for the Redis database  
# + options - other connection options of the connection configuration
@display {label: "Connection Parameters"}
type ConnectionParams record {|
    @display {label: "Host"}
    string host;
    @display {label: "Port"}
    int port;
    @display {label: "Password"}
    string password?;
    @display {label: "Connection Options"}
    Options options = {};
|};

# The connection string(URI) based configurations.
#
# + uri - The connection string for the Redis database
@display {label: "Connection String"}
type ConnectionString record {|
    string uri;
|};

# Connection options for Redis client endpoint.
#
# + ssl - Whether SSL is enabled 
# + startTls - Whether STARTTLS is enabled
# + verifyPeer - Whether peer verification is enabled
# + clientName - The name of the client
# + database - The database index
# + connectionTimeout - The connection timeout in seconds
@display {label: "Connection Options"}
public type Options record {|
    @display {label: "SSL Enabled"}
    boolean ssl = false;
    @display {label: "STARTTLS Enabled"}
    boolean startTls = false;
    @display {label: "Peer Verification Enabled"}
    boolean verifyPeer = false;
    @display {label: "Client Name"}
    string clientName?;
    @display {label: "Database"}
    int database = -1;
    @display {label: "Connection Timeout"}
    int connectionTimeout = 60;
|};
