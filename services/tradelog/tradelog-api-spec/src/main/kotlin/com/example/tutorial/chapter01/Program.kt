package com.example.tutorial.chapter01

import com.example.tradelog.api.spec.model.TransactionType

class DocumentImpl : Document {
    override val size: Long
        get() = 0

    override val version: Long
        get() = 0
}


interface Document {
    val version: Long
    val size: Long

    val name: String
        get() = "NoName"
}

class Submarine {
    fun fire() {
        println("Firing torpedoes")
    }

    fun submerge() {
        println("Submerging")
    }

}


fun foo(str: String, fn: (String) -> String) {
    var applied = fn(str)
    println(applied)
}

//foo("hello", {it.reversed()})

fun main(args: Array<String>) {
    println("Hello World!")
    val documentImpl = DocumentImpl()
    println(documentImpl.name)
    val transactionType = TransactionType.lookup("XYZ")
    println(transactionType)
}
