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
public type Options record {|
    @display{label: "Client Name"} 
    string clientName = "";
    @display{label: "Connection Pooling Enabled"} 
    boolean connectionPooling = false;
    @display{label: "Cluster Connection Enabled"} 
    boolean isClusterConnection = false;
    @display{label: "SSL Enabled"} 
    boolean ssl = false;
    @display{label: "STARTTLS Enabled"} 
    boolean startTls = false;
    @display{label: "Peer Verification Enabled"} 
    boolean verifyPeer = false;
    @display{label: "Database"} 
    int database = -1;
    @display{label: "Connection Timeout"} 
    int connectionTimeout = -1;
|};

# The Client endpoint configuration for Redis databases.
#
# + host - The host of the Redis database
# + password - Password for the database connection
# + options - Properties for the connection configuration
@display{label: "Connection Config"} 
public type ConnectionConfig record {|
    @display{label: "Host"} 
    string host = "localhost";
    @display{label: "Password"} 
    string password = "";
    @display{label: "Connection Options"} 
    Options options = {};
|};
