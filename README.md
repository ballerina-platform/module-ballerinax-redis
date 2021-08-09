# Ballerina Redis Connector

[![Build](https://github.com/ballerina-platform/module-ballerinax-redis/workflows/CI/badge.svg)](https://github.com/ballerina-platform/module-ballerinax-redis/actions?query=workflow%3ACI)
[![GitHub Last Commit](https://img.shields.io/github/last-commit/ballerina-platform/module-ballerinax-redis.svg)](https://github.com/ballerina-platform/module-ballerinax-redis/commits/master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

[Redis](https://redis.io/) is an open source, in-memory data structure store that you can use as a database, cache, and message broker. It supports data structures such as strings, hashes, lists, sets and sorted sets.

The Redis [Ballerina](https://ballerina.io/) connector provides operations to connect and interact with the Redis server over the network.

For more information, go to the module(s).
- [`redis`](redis/Module.md)

## Building from the source
### Setting up the prerequisites

1. Download and install Java SE Development Kit (JDK) version 11. You can install either [OpenJDK](https://adoptopenjdk.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

    > **Note:** Set the JAVA_HOME environment variable to the path name of the directory into which you installed JDK.

2. Download and install [Ballerina Swan Lake Beta3](https://ballerina.io/). 

### Building the source
Execute the following commands to build from the source:

* To build the package:
   ```   
   bal build -c
   ```
* To build the package without tests:
   ```
   bal build -c --skip-tests
   ```
## Contributing to Ballerina
As an open source project, Ballerina welcomes contributions from the community. 

For more information, see the [Contribution Guidelines](https://github.com/ballerina-platform/ballerina-lang/blob/master/CONTRIBUTING.md).

## Code of conduct
All contributors are encouraged to read the [Ballerina Code of Conduct](https://ballerina.io/code-of-conduct).

## Useful links
* Discuss about code changes of the Ballerina project via [ballerina-dev@googlegroups.com](mailto:ballerina-dev@googlegroups.com).
* Chat live with us via our [Slack channel](https://ballerina.io/community/slack/).
* Post all technical questions on Stack Overflow with the [#ballerina](https://stackoverflow.com/questions/tagged/ballerina) tag.
