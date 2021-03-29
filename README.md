# fastcsv-codegen
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/dev.riajul.fastcsv-codegen/annotations?server=https%3A%2F%2Fs01.oss.sonatype.org)
### Ultra Fast Csv Deserialization to Data Class based on Code Generations.

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