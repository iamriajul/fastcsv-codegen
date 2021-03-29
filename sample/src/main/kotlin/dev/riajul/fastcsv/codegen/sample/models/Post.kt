package dev.riajul.fastcsv.codegen.sample.models

import dev.riajul.fastcsv.codegen.annotations.CsvCodegen

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
) {
    private val myCustomField = true
}