package dev.riajul.fastcsv.codegen.annotations

/**
 * Exclude properties from being detected CsvCodegen
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
@MustBeDocumented
annotation class CsvCodegenExclude()