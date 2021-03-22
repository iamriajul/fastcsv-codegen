package dev.riajul.fastcsv.codegen.sample

import kotlin.system.measureTimeMillis

fun logd(s: String) {
    println(s)
}

inline fun logt(suffix: String = "", block: () -> Unit) {
    logd(measureTimeMillis(block).toString() + " milliseconds ($suffix)")
}