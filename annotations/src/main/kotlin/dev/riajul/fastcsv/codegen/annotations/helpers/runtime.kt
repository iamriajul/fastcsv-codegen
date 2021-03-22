package dev.riajul.fastcsv.codegen.annotations.helpers

import de.siegmar.fastcsv.reader.CsvReader

val csvReader = CsvReader().apply {
    setContainsHeader(true)
}