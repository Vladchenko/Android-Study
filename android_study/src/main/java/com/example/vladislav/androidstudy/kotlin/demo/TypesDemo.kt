package com.example.vladislav.androidstudy.kotlin.demo

/** https://kotlinlang.org/docs/typecasts.html#smart-casts */
class TypesDemo {

    fun typesDemo() {
        val valueByte: Byte = 10
        println(valueByte.javaClass)    // byte (notice, a primitive)

        val valueLong = 10L
        println(valueLong.javaClass)    // long (notice, a primitive)

        val pi = 3.14 // Double
        // val one: Double = 1  // Error: type mismatch
        val oneFloat = 1.0f     // float    1f or 1F or 1.0F or 1e10
        println(oneFloat.javaClass)     // float (notice, a primitive)

        val valueSmallerInt = 1
        println(valueSmallerInt.javaClass)    // int (notice, a primitive)
        println(valueSmallerInt)    // 1

        val valueBiggerInt = 1000000000000000
        println(valueBiggerInt.javaClass)    // long (notice, a primitive)
        println(valueBiggerInt)    // 1000000000000000

//        val valueBiggerInt2 = 10000000000000000000000000000       // The value is out of range

        val value1: Short = 10
        val value2: Short = 20
        println((value1 + value2).javaClass)    // int (notice, a primitive)
        val value3 = value1 + value2
        println(value3.javaClass)    // int (notice, a primitive)

        val value4: UByte = 10u
        val value5: UByte = 20u
        println((value4 + value5).javaClass)    // class kotlin.UInt
        val value6 = value4 + value5
        println(value6.javaClass)    // class kotlin.UInt

        val value7: Int = Integer.MAX_VALUE
        val value8: Int = Integer.MAX_VALUE
        println((value7 + value8).javaClass)    // int (notice, a primitive)
        val value9 = value7 + value8
        println(value9.javaClass)    // int (notice, a primitive)
        println(value9)     // -2   Doesn't raise type to Long

        val value10: Int = Integer.MAX_VALUE
        val value11: Int = Integer.MAX_VALUE
        println((value10 + value11.toLong()).javaClass)    // long (notice, a primitive)
        val value12 = value10 + value11.toLong()
        println(value12.javaClass)    // long (notice, a primitive)
        println(value12)     // 4294967294

        val i = 1
        val d = 1.0
        val f = 1.0f
        fun printDouble(d: Double) {
            print(d)
        }
        printDouble(d)
//    printDouble(i) // Error: Type mismatch
//    printDouble(f) // Error: Type mismatch
    }

    fun demoCasts() {
        val x: Any = 0
        // x is automatically cast to String on the right-hand side of `||`
        if (x !is String || x.length == 0) return

        // x is automatically cast to String on the right-hand side of `&&`
        if (x is String && x.length > 0) {
            print(x.length) // x is automatically cast to String
        }

        // Smart casts work for when expressions and while loops as well:
        when (x) {
            is Int -> print(x + 1)
            is String -> print(x.length + 1)
            is IntArray -> print(x.sum())
        }
    }
}