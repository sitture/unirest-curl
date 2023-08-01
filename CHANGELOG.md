# CHANGELOG

All notable changes to this project are documented in this file.
The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## 0.5.0

### Updated

- Upgrades to use unirest 4.x

## 0.2.0

### Updated

- Updates to latest version of dependencies

## 0.1.2

### Fixed

- Fixes an issue with body when empty or null fields provided.

## 0.1.1

### Updated

- Adds a space between header key and value. i.e. "--header Key: Value"
- Splits query params into separate `--data` items

## 0.1.0

### Added

- Initial release of the unirest to curl interceptor.
- Adds support for extracting `methodtype`, `headers`, `simple body`, `params body`.
