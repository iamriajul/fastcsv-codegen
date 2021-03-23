import dev.riajul.fastcsv.codegen.sample.logd
import dev.riajul.fastcsv.codegen.sample.logt
import dev.riajul.fastcsv.codegen.sample.models.PostCsvCodegen
import java.io.File

fun main() {
    val csv = Res::class.java.getResource("test_csv.csv")
        .readText()

    logt("fastcsv-codegen") {
        val items = PostCsvCodegen.fromCsv(csv)
        logd("${items.count()} Items")
        File("./fastcsv-codgen.txt").writeText(items.toString())
    }
}