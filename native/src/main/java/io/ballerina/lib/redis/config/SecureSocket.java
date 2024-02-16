/*
 * Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org)
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.lib.redis.config;

/**
 * Represents Redis connection secure socket configurations.
 *
 * @param trustStoreCert trust store certificate
 * @param strCert        certificate in string format
 * @param keyStore       key store
 * @param CertKey        certificate and private key
 * @param protocols      SSL/TLS protocols
 * @param ciphers        SSL/TLS ciphers
 * @param verifyPeer     whether to verify the peer
 * @param startTls       whether to enable startTLS
 * @since 3.0.0
 */
public record SecureSocket(TrustStore trustStoreCert, String strCert, KeyStore keyStore, CertKey CertKey,
                           String[] protocols, String[] ciphers, boolean verifyPeer, boolean startTls) {

}
