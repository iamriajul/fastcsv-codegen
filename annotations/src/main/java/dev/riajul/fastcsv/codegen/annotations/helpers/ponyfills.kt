package dev.riajul.fastcsv.codegen.annotations.helpers

import de.siegmar.fastcsv.reader.CsvRow

fun CsvRow.getFieldOrNull(name: String, returnNullIfEmpty: Boolean = true): String? {
    return try {
        val value = getField(name)
        return if (returnNullIfEmpty && value == "") null else value
    } catch (e: Exception) {
        null
    }
}

fun String?.toBooleanOrNull(): Boolean? {
    if (this == null) {
        return null
    }
    return toBoolean()
}

fun String.toChar(): Char {
    return toCharArray()[0]
}

fun String?.toCharOrNull(): Char? {
    if (this == null) {
        return null
    }
    return toChar()
}