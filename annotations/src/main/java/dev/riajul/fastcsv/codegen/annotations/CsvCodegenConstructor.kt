package dev.riajul.fastcsv.codegen.annotations

/**
 * Use if you have multiple constructors. By default generator choose first public constructor in the class.
 * Use this annotation to mark your constructor, which will be used to map csv into your class.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CONSTRUCTOR)
@MustBeDocumented
annotation class CsvCodegenConstructor()