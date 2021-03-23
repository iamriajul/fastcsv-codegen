package dev.riajul.fastcsv.codegen.sample.models

import dev.riajul.fastcsv.codegen.annotations.CsvCodegen

/**
 * From https://jsonplaceholder.typicode.com/todos
 */
@CsvCodegen
data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)
