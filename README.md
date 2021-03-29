# FastCSV-Codegen
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/dev.riajul.fastcsv-codegen/annotations?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/#nexus-search;gav~dev.riajul.fastcsv-codegen~~~~)
[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/iamriajul/fastcsv-codegen/tests)](https://github.com/iamriajul/fastcsv-codegen/actions)
[![GitHub issues](https://img.shields.io/github/issues/iamriajul/fastcsv-codegen)][tracker]
[![Star on GitHub](https://img.shields.io/github/stars/iamriajul/fastcsv-codegen.svg?style=flat&logo=github&colorB=deeppink&label=stars)][repo]
[![GitHub top language](https://img.shields.io/github/languages/top/iamriajul/fastcsv-codegen)][repo]
[![License: MIT](https://img.shields.io/badge/license-MIT-purple.svg)](https://opensource.org/licenses/MIT)
### Ultra-Fast CSV Deserialization to Data Class based on Code Generations.

[repo]: https://github.com/iamriajul/adhan-dart
[tracker]: https://github.com/iamriajul/adhan-dart/issues

Uses [osiegmar/FastCSV ^1.0.4](https://github.com/osiegmar/FastCSV) to parse CSV which is very fast! and to deserialize your **CSV to Data Class** it uses code generation via kapt (kotlin annotation processing).

The annotation is `@CsvCodegen` and it generates file with suffix of CsvCodegen, eg: PostCsvCodegen for you Post data class.

A Data Class Implementing @CsvCodegen Interface.
```kotlin
@CsvCodegen
data class Post(
    val id: Int,
    val title: String,
    val content: String?,
    val a: Char,
    val b: Char?,
    val author_id: Int?,
    val is_public: Boolean,
    val is_updated: Boolean?,
)
```
Deserializing
```kotlin
val items: List<Post> = PostCsvCodegen.fromCsv(csvString)
```
Yes! Deserializing is as simple as one line code! You don't write all those boilerplate codes!

It currently support these data types
```kotlin
Int,
IntNullable,
Long,
LongNullable,
Short,
ShortNullable,
Byte,
ByteNullable,
Double,
DoubleNullable,
Float,
FloatNullable,
Boolean,
BooleanNullable,
Char,
CharNullable,
String,
StringNullable
```

#### Installation
Add Repository
```kotlin
maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
```
Add Dependency
```kotlin
implementation("dev.riajul.fastcsv-codegen:annotations:1.0-alpha-1-SNAPSHOT")
kapt("dev.riajul.fastcsv-codegen:generator:1.0-alpha-1-SNAPSHOT")
```
