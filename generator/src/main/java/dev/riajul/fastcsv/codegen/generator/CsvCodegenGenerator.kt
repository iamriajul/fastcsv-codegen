package dev.riajul.fastcsv.codegen.generator

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import de.siegmar.fastcsv.reader.CsvReader
import dev.riajul.fastcsv.codegen.annotations.CsvCodegen
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import org.jetbrains.annotations.Nullable
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class) // For registering the service
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
class CsvCodegenGenerator : AbstractProcessor() {

    private val annotation = CsvCodegen::class.java

    override fun getSupportedAnnotationTypes() = setOf(annotation.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {

        try {
            for (type in roundEnvironment.getElementsAnnotatedWith(annotation)) {
                generateCsvCodegenClass(type)
            }
        } catch (e: Exception) {
            error(e.toString())
            return false
        }

        return true
    }

    private fun generateCsvCodegenClass(element: Element) {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val className = element.simpleName.toString()

        val userClassName = ClassName(packageName, className)
        val ourClassName = ClassName(packageName,  className + "CsvCodegen")
        val list = ClassName("kotlin.collections", "List")

        val csvFieldGettingMethodCalls = element.enclosedElements.filter { it.kind == ElementKind.FIELD }.filter {
            // This means data class constructor val parameters.
            it.modifiers.contains(Modifier.FINAL) &&
            !it.modifiers.contains(Modifier.STATIC)
        }.map {
            val fieldType = getFieldType(it)
            if (fieldType == null) {
                error("${it.simpleName}'s data type is not supported!")
                return
            }
            getCsvFieldValue(it.simpleName.toString(), fieldType)
        }

        val fFromCsv = FunSpec
            .builder("fromCsv")
            .addParameter("csv", String::class)
            .returns(list.parameterizedBy(userClassName))
            .addCode(
                CodeBlock.builder()
                    .beginControlFlow("val items: %T = csvReader.read(csv.reader()).rows.map { row -> ",
                        list.parameterizedBy(userClassName))

                    .addStatement("%T(", userClassName)
                    .apply {
                        indent()
                        for ((index, methodCall) in csvFieldGettingMethodCalls.withIndex()) {
                            if (index == csvFieldGettingMethodCalls.lastIndex) {
                                addStatement("row.$methodCall")
                            } else {
                                addStatement("row.$methodCall,")
                            }
                        }
                        unindent()
                    }
                    .addStatement(")")

                    .endControlFlow()

                    .addStatement("return items")
                    .build()
            )
            .build()

        val fileSpec = FileSpec.builder(packageName, ourClassName.simpleName)
            .addComment("This is a generated file. Do not edit.")
            .addImport(
                "dev.riajul.fastcsv.codegen.annotations.helpers",
                "csvReader",
                "getFieldOrNull",
                "toBooleanOrNull",
                "toChar",
                "toCharOrNull"
            )
            .addType(
                TypeSpec.objectBuilder(ourClassName)
                    .addOriginatingElement(element)
                    .addFunction(
                        fFromCsv
                    )
                    .build()
            )
            .build()

        fileSpec.writeTo(processingEnv.filer)
    }

    private fun getCsvFieldValue(name: String, fieldType: FieldType): String {
        return when(fieldType) {
            FieldType.Int -> "getField(name)!!.toInt()"
            FieldType.IntNullable -> "getFieldOrNull(name)?.toIntOrNull()"
            FieldType.Long -> "getField(name)!!.toLong()"
            FieldType.LongNullable -> "getFieldOrNull(name)?.toLongOrNull()"
            FieldType.Short -> "getField(name)!!.toShort()"
            FieldType.ShortNullable -> "getFieldOrNull(name)?.toShortOrNull()"
            FieldType.Byte -> "getField(name)!!.toByte()"
            FieldType.ByteNullable -> "getFieldOrNull(name)?.toByteOrNull()"
            FieldType.Double -> "getField(name)!!.toDouble()"
            FieldType.DoubleNullable -> "getFieldOrNull(name)?.toDoubleOrNull()"
            FieldType.Float -> "getField(name)!!.toFloat()"
            FieldType.FloatNullable -> "getFieldOrNull(name)?.toFloatOrNull()"
            FieldType.Boolean -> "getField(name)!!.toBoolean()"
            FieldType.BooleanNullable -> "getFieldOrNull(name)?.toBooleanOrNull()"
            FieldType.Char -> "getField(name)!!.toChar()"
            FieldType.CharNullable -> "getFieldOrNull(name)?.toCharOrNull()"
            FieldType.String -> "getField(name)!!.toString()"
            FieldType.StringNullable -> "getFieldOrNull(name)"
        }.replace("name", "\"$name\"")
    }

    private enum class FieldType {
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
    }

    private fun getFieldType(field: Element): FieldType? {

        val type = field.asType()
        val typeName = type.asTypeName()
        val typeNameString = typeName.toString()

        val isNullable = field.annotationMirrors.find { it.annotationType.asTypeName().toString() == Nullable::class.asTypeName().toString() } != null

        // java.lang.* Numbers are nullable with ?
        return when (typeNameString) {
            // Numbers
            "kotlin.Int" -> FieldType.Int
            "java.lang.Integer" -> FieldType.IntNullable
            "kotlin.Long" -> FieldType.Long
            "java.lang.Long" -> FieldType.LongNullable
            "kotlin.Short" -> FieldType.Short
            "java.lang.Short" -> FieldType.ShortNullable
            "kotlin.Byte" -> FieldType.Byte
            "java.lang.Byte" -> FieldType.ByteNullable
            // Decimal Numbers
            "kotlin.Double" -> FieldType.Double
            "java.lang.Double" -> FieldType.DoubleNullable
            "kotlin.Float" -> FieldType.Float
            "java.lang.Float" -> FieldType.FloatNullable
            // Booleans
            "kotlin.Boolean" -> FieldType.Boolean
            "java.lang.Boolean" -> FieldType.BooleanNullable
            // Strings
            "kotlin.Char" -> FieldType.Char
            "java.lang.Character" -> FieldType.CharNullable
            "java.lang.String" -> if (!isNullable) FieldType.String else FieldType.StringNullable
            else -> {
                error("Type is not supported: $typeNameString")
                null
            }
        }
    }

    fun error(message: String) {
        processingEnv
            ?.messager
            ?.printMessage(Diagnostic.Kind.ERROR, formatMessage(message))
    }

    fun warning(message: String) {
        processingEnv
            ?.messager
            ?.printMessage(Diagnostic.Kind.WARNING, formatMessage(message))
    }

    fun formatMessage(message: String): String {
        return "CsvCodeGen.Generator: $message"
    }
}