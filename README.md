# Common Utils

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.aaru637/common-utils.svg)](https://search.maven.org/artifact/io.github.aaru637/common-utils)

A comprehensive Java utility library providing common helper methods for JSON manipulation, date/time operations, file handling, type conversion, and general utilities.

## Getting Started

### Maven
```xml
<dependency>
    <groupId>io.github.aaru637</groupId>
    <artifactId>common-utils</artifactId>
    <version>1.0.6</version>
</dependency>
```

### Gradle
```groovy
implementation 'io.github.aaru637:common-utils:1.0.6'
```

## Features

### 1. JsonUtils
Thread-safe JSON serialization and deserialization using Gson.
```java
// Serialize object to JSON
String json = JsonUtils.toJson(myObject);

// Deserialize JSON to Object
MyClass obj = JsonUtils.fromJson(json, MyClass.class);

// Deserialize to Map with independent types
Map<String, Integer> map = JsonUtils.fromJsonMap(json, String.class, Integer.class);
```

### 2. DateTimeUtils
Comprehensive date and time operations.
```java
DateTimeUtils utils = new DateTimeUtils();
String formatted = utils.formatDate("yyyy-MM-dd");
LocalDateTime nextWeek = utils.getStartOfNextWeek();
```

### 3. CommonUtils
Helper methods for null checks, string operations, and random generation.
```java
if (CommonUtils.isNotEmpty(myString)) {
    String random = CommonUtils.randomString(10);
}
```

### 4. FileOperations
Utilities for file reading, writing, and manipulation.

### 5. TypeConverter
Safe type conversion utilities between various Java types.

## DTOs & Response Formatting

The library provides a consistent structure for API responses using generic DTOs.

### ApiResponse
A generic container that encapsulates the status, data payload, and metadata.

```java
ApiResponse response = new ApiResponse();
response.setStatus(200);
response.setData(user);
response.setMetaResponse(new MetaResponse());
```

### MetaResponse & MetaInfo
Used for tracking operation success and accumulating detailed messages or errors.

```java
MetaResponse meta = new MetaResponse();
meta.add(new MetaInfo("ERR_001", "Invalid input"), true);
response.setMetaResponse(meta);
```

## Deployment

To deploy a new version to Maven Central:

1. Set the following environment variables (or update `settings.xml` directly):
   - `OSSRH_USERNAME_TOKEN`
   - `OSSRH_PASSWORD_TOKEN`
   - `GPG_KEY_NAME`
   - `GPG_KEY_PASSPHRASE`
2. Run the deployment command:
   ```bash
   mvn clean deploy -s settings.xml
   ```

## License
This project is licensed under the Apache License, Version 2.0.