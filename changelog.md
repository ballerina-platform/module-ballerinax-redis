# Change Log
This file contains all the notable changes done to the Ballerina Redis package through the releases.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- [Added `mGetOptional`, a nil-safe variant of `mGet` that represents a missing key as `()`](https://github.com/ballerina-platform/ballerina-library/issues/8889)

### Changed

### Fixed
- [Fixed `mGet` returning fewer results than requested when the key list contained duplicates](https://github.com/ballerina-platform/ballerina-library/issues/8908)
- [Fixed `mGet` failing with a raw `InherentTypeViolation` error when a key does not exist; it now fails with a clear message pointing to `mGetOptional`](https://github.com/ballerina-platform/ballerina-library/issues/8889)

## [3.3.0] - 2026-07-06

### Added
- [Added `flushDb` and `flushAll` remote functions to the `redis:Client` to remove all the keys from the currently selected database or from all the databases respectively](https://github.com/ballerina-platform/ballerina-library/issues/8880)

### Changed

### Fixed
- [Fixed client initialization for empty connection configs](https://github.com/ballerina-platform/ballerina-library/issues/6157)

## [3.0.0] - 2024-03-08

### Added
- [Added support for Redis 7.x.x server versions](https://github.com/ballerina-platform/ballerina-library/issues/5953)
- [Added support for Redis cluster connections and cluster-specific operations](https://github.com/ballerina-platform/ballerina-library/issues/4980)
- [Added support for secure connections to Redis servers using SSL/TLS](https://github.com/ballerina-platform/ballerina-library/issues/6053)
- [Added support to directly provide Redis server URI for connection](https://github.com/ballerina-platform/ballerina-library/issues/6053)

### Changed
- [Improved exception handling to return redis module-specific error types, instead of Ballerina generic error values](https://github.com/ballerina-platform/module-ballerinax-redis/pull/197)