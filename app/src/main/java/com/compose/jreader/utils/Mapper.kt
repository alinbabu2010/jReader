package com.compose.jreader.utils

interface Mapper<T, V> {
    fun map(data: T): V
}