# fastcsv-codegen
Ultra Fast Csv Deserialization to Data Class based on Code Generations.

Uses [osiegmar/FastCSV](https://github.com/osiegmar/FastCSV) to parse CSV which is very fast! and to deserialize your **CSV to Data Class** it uses code generation via kapt (kotlin annotation processing).

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
val items = PostCsvCodegen.fromCsv(csvString)
```
Yes! Deserializing is as simple as one line code!

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
