package com.example.vladislav.androidstudy.kotlin.demo

/**
 *
 */
class ReflectionDemo {

    val any = Any::class
    data class SomeDataClass(val someField: String)

    fun anyDemo() {
        println(any.java)       // class java.lang.Object
        println(any::class)     // class kotlin.jvm.internal.ClassReference (Kotlin reflection is not available)
        println((any.java.kotlin as Any).javaClass.toString())  // class kotlin.jvm.internal.ClassReference
    }

    fun dataClassDemo() {
        // class com.example.vladislav.androidstudy.kotlin.demo.ReflectionDemo$SomeDataClass (Kotlin reflection is not available)
        println(SomeDataClass::class)
        // class com.example.vladislav.androidstudy.kotlin.demo.ReflectionDemo$SomeDataClass
        println(SomeDataClass::class.java)
        // class kotlin.jvm.internal.ClassReference
        println((SomeDataClass::class as Any).javaClass.toString())

        val someDataClass = SomeDataClass("someValue")
        // class com.example.vladislav.androidstudy.kotlin.demo.ReflectionDemo$SomeDataClass (Kotlin reflection is not available)
        println(someDataClass::class)
    }
}