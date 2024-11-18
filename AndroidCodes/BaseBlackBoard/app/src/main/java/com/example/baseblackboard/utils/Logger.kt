package com.example.baseblackboard.utils

import android.util.Log

fun Any.lgd(message: String?) {
    lgd(null, message)
}

fun Any.lgd(tag: String?, message: String?) {
    Log.d(createTag(tag), message ?: "null")
}

fun Any.lgi(message: String?) {
    lgi(null, message)
}

fun Any.lgi(tag: String?, message: String?) {
    Log.i(createTag(tag), message ?: "null")
}

fun Any.lgw(message: String?) {
    lgw(null, message)
}

fun Any.lgw(tag: String?, message: String?) {
    Log.w(createTag(tag), message ?: "null")
}

fun Any.lge(message: String?) {
    lge(null, message)
}

fun Any.lge(tag: String?, message: String?) {
    Log.e(createTag(tag), message ?: "null")
}

fun Any.createTag(tag: String? = null): String {
    return MapTag.createTag(javaClass.name, tag)
}

private object MapTag {

    private val subClass = HashMap<String, String>()
    private val mapTag = HashMap<String, String>()

    fun createTag(className: String, tag: String?): String {
        return createTag(tag ?: subClass(className))
    }

    private fun subClass(klass: String): String {
        return subClass.getOrPut(klass) {
            val dropKey = "\$Companion"
            var input = klass
            input = input.split(".").last()
            if (input.endsWith(dropKey)) {
                input = input.dropLast(dropKey.length)
            }
            input
        }
    }

    fun createTag(tag: String): String {
        return mapTag.getOrPut(tag) {
            val startTag = "MU SRV"
            if (tag.contains(startTag)) {
                return tag
            }
            var input = tag
            val maxLength = 20
            val sizeInput = input.length
            input = if (sizeInput > maxLength) {
                input.substring(0, maxLength)
            } else {
                val delta = maxLength - sizeInput
                val space = StringBuilder()
                for (i in 0 until delta) {
                    space.append(" ")
                }
                input + space
            }
            "[$startTag - $input]"
        }
    }
}