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
import ballerina/crypto;

# The client endpoint configuration for Redis.
#
# + connection - Connection configurations of the Redis server. This can be either a single URI or a set of parameters
# + connectionPooling - Flag to indicate whether connection pooling is enabled
# + isClusterConnection - Flag to indicate whether the connection is a cluster connection
# + secureSocket - Configurations related to SSL/TLS encryption
@display {label: "Connection Config"}
public type ConnectionConfig record {|
    @display {label: "Connection Type"}
    ConnectionUri|ConnectionParams connection?;
    @display {label: "Connection Pooling Enabled"}
    boolean connectionPooling = false;
    @display {label: "Cluster Mode Enabled"}
    boolean isClusterConnection = false;
    @display {label: "Secure Socket Configurations"}
    SecureSocket secureSocket?;
|};

# The connection parameters based configurations.
#
# + host - Host address of the Redis database  
# + port - Port of the Redis database  
# + username - The username for the Redis database
# + password - The password for the Redis database  
# + options - Other connection options of the connection configuration
@display {label: "Connection Parameters"}
type ConnectionParams record {|
    @display {label: "Host"}
    string host = "localhost";
    @display {label: "Port"}
    int port = 6379;
    @display {label: "Username"}
    string username?;
    @display {label: "Password"}
    string password?;
    @display {label: "Connection Options"}
    Options options = {};
|};

# The redis Connection URI based configurations. This can become useful when working with 
# managed Redis databases, where the cloud provider usually provides a connection URI.
#
# + uri - The connection URI for the Redis database
@display {label: "Connection URI"}
type ConnectionUri string;

# Connection options for Redis client endpoint.
#
# + clientName - Name of the client
# + database - Database index which the client should interact with. Not applicable for cluster connections
# + connectionTimeout - Connection timeout in seconds
@display {label: "Connection Options"}
public type Options record {|
    @display {label: "Client Name"}
    string clientName?;
    @display {label: "Database Index"}
    int database = 0;
    @display {label: "Connection Timeout"}
    int connectionTimeout = 60;
|};

# Configurations for secure communication with the Redis server.
#
# + cert - Configurations associated with `crypto:TrustStore` or single certificate file that the client trusts  
# + key - Configurations associated with `crypto:KeyStore` or combination of certificate and private key of the client  
# + protocols - List of protocols used for the connection established to Redis Server, such as TLSv1.2, TLSv1.1, TLSv1.
# + ciphers - List of ciphers to be used for SSL connections
# + verifyMode - The SSL/TLS verification mode. This can be either NONE, CA, or FULL.
# + startTls - Whether StartTLS is enabled
@display {label: "Secure Socket Configurations"}
public type SecureSocket record {|
    @display {label: "Certificate"}
    crypto:TrustStore|string cert;
    @display {label: "Key"}
    crypto:KeyStore|CertKey key?;
    @display {label: "Protocols to be used for the connection to the Redis Server"}
    string[] protocols?;
    @display {label: "Ciphers"}
    string[] ciphers?;
    @display {label: "Peer Verification Mode"}
    SslVerifyMode verifyMode = NONE;
    @display {label: "StartTLS Enabled"}
    boolean startTls = false;
|};

# Represents a combination of certificate, private key, and private key password if encrypted.
#
# + certFile - File containing the certificate
# + keyFile - File containing the private key in PKCS8 format
# + keyPassword - Password of the private key if it is encrypted
@display {label: "Certificate Key Configurations"}
public type CertKey record {|
    @display {label: "Certificate File Path"}
    string certFile;
    @display {label: "Private Key File Path"}
    string keyFile;
    @display {label: "Private Key Password"}
    string keyPassword?;
|};

# Represents the SSL/TLS verification mode.
#
# + NONE - No verification
# + CA - Verify the server's certificate against the provided CA certificates
# + FULL - Verify the server's certificate against the provided CA certificates and also verify the server's hostname
public enum SslVerifyMode {
    NONE,
    CA,
    FULL
}
