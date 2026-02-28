# Common Utils

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.aaru637/common-utils.svg)](https://search.maven.org/artifact/io.github.aaru637/common-utils)

A comprehensive Java utility library providing common helper methods for JSON manipulation, date/time operations, file handling, type conversion, PDF generation, and advanced validation annotations.

## Getting Started

### Maven
```xml
<dependency>
    <groupId>io.github.aaru637</groupId>
    <artifactId>common-utils</artifactId>
    <version>2.0.1</version>
</dependency>
```

### Gradle
```groovy
implementation 'io.github.aaru637:common-utils:2.0.1'
```

## Features

### 1. JsonUtils
Thread-safe JSON serialization and deserialization using Gson.
```java
// Serialize object to JSON
String json = JsonUtils.toJson(myObject);

// Deserialize JSON to Object
MyClass obj = JsonUtils.fromJson(json, MyClass.class);
```

### 2. PdfService
Advanced PDF generation supporting raw bytes, HTML strings, and Thymeleaf templates.
```java
PdfService pdfService = new PdfService();
PdfRequestDTO request = PdfRequestDTO.builder()
    .fileName("invoice.pdf")
    .fileContent("<h1>Hello [(${name})]</h1>")
    .data(Map.of("name", "John Doe"))
    .build();

byte[] pdfBytes = pdfService.generatePdf(request);
pdfService.savePdfToFile(pdfBytes, "output/invoice.pdf");
```

### 3. Validation Annotations
Powerful annotations for data validation and normalization.

#### @EnumValidator
Validates if a string value matches a constant in a specified Enum.
```java
public class UserDTO {
    @EnumValidator(enumClass = UserStatus.class, message = "Invalid status")
    private String status;
}
```

#### @StringNormalizer
Normalizes strings during Jackson deserialization (trim, case conversion, capitalization).
```java
public class ProfileDTO {
    @StringNormalizer(trim = true, capitalize = true)
    private String firstName;
    
    @StringNormalizer(caseConversion = StringCase.UPPER)
    private String countryCode;
}
```

#### @Patchable
Distinguishes between missing fields and explicitly null fields in PATCH requests.
```java
public class UpdateRequest extends FieldPresenceChecker {
    @Patchable
    private String email;
}
```

### 4. DTOs & Response Formatting
Consistent structure for API responses using generic DTOs.

#### ApiResponse
A generic container that encapsulates the status, data payload, and metadata.
```java
ApiResponse response = new ApiResponse();
response.setStatus(200);
response.setData(user);
```

#### MetaResponse & MetaInfo
Thread-safe metadata tracking for success/failure status and detailed messages.
```java
MetaResponse meta = new MetaResponse();
meta.add(new MetaInfo("ERR_001", "Invalid input"), true);
response.setMetaResponse(meta);
```

## Deployment

To deploy a new version to Maven Central:

1. Set environment variables for OSSRH and GPG credentials.
2. Run the deployment command:
   ```bash
   mvn clean deploy -s settings.xml
   ```

## License
This project is licensed under the Apache License, Version 2.0.