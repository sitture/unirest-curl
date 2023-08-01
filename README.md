# unirest-curl

An interceptor for logging Unirest requests as curl requests.

![Build](https://github.com/sitture/unirest-curl/workflows/Build/badge.svg) ![Github Publish](https://github.com/sitture/unirest-curl/workflows/Github%20Publish/badge.svg) ![Maven Publish](https://github.com/sitture/unirest-curl/workflows/Maven%20Publish/badge.svg) [![Maven Central](https://img.shields.io/maven-central/v/com.github.sitture/unirest-curl.svg)](https://mvnrepository.com/search?q=com.github.sitture) [![Maintainability](https://api.codeclimate.com/v1/badges/05417905d6cedb0b2e49/maintainability)](https://codeclimate.com/github/sitture/unirest-curl/maintainability) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?maxAge=2592000)](https://opensource.org/licenses/MIT) [![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](../../issues)

All notable changes to this project are documented in [CHANGELOG.md](CHANGELOG.md).
The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

The latest version of the library can be found under [Releases](https://github.com/sitture/unirest-curl/releases).

## Setup

Add the following both `unirest-java` and `unirest-curl` dependencies:

### Maven

```xml
<dependency>
    <groupId>com.konghq</groupId>
    <artifactId>unirest-java-core</artifactId>
    <version>${version}</version>
</dependency>
<dependency>
    <groupId>com.github.sitture</groupId>
    <artifactId>unirest-curl</artifactId>
    <version>${version}</version>
</dependency>
```

### Github Packages

If you would like to use github package instead of maven central, add the following repository to pom.xml.

```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/sitture/unirest-curl</url>
  </repository>
</repositories>
```

### Gradle

```groovy
compile 'com.github.sitture:unirest-curl:${version}'
```

## Usage

To Intercept requests and log them as curl requests:

1. Create a Logger object using the RequestLogger interface. E.g. To log requests to stdout.

```java
public class CurlLogger implements RequestLogger {
    @Override
    public void log(final String curlRequest) {
        System.out.println(curlRequest);
    }
}
```

2. Add the interceptor to Unirest config:

```java
Unirest.config().interceptor(new CurlInterceptor(new CurlLogger()));
// Example request
Unirest.get("https://reqres.in/api/users?page=2").asJson();
```

That's it. Now every time a request is made from Unirest, it'll log the request as curl. :)

## Issues & Contributions

Please [open an issue here](../../issues) on GitHub
if you have a problem, suggestion, or other comment.

Pull requests are welcome and encouraged! Any contributions should include new or updated unit tests as necessary to maintain thorough test coverage.

Read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.
