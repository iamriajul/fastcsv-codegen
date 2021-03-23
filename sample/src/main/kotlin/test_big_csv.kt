import dev.riajul.fastcsv.codegen.sample.logd
import dev.riajul.fastcsv.codegen.sample.logt
import dev.riajul.fastcsv.codegen.sample.models.TodoCsvCodegen

fun main() {
    // test_big_csv.csv contains 20 thousands rows of data.
    val csv = Res::class.java.getResource("test_big_csv.csv")
        .readText()

    logt("fastcsv-codegen") {
        val items = TodoCsvCodegen.fromCsv(csv)
        logd("${items.count()} Items")
    }
}