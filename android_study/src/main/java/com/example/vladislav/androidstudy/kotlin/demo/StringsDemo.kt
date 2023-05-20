package com.example.vladislav.androidstudy.kotlin.demo

/**
 *
 *
 * @author Yanchenko Vladislav
 * @since 25.01.2021
 */
class StringsDemo {

    // private val string1: String  // Property must be initialized or be abstract
    // private val string2: String = null   // Null can not be a value of a non-null type string
    private val string2: String? = null     // Nullable value is null
    private val languageName: String = "Kotlin"
    private val string3: String? = String.toString()
    private var longString = """This is a 
        long string that may contain several rows
    """
    private val charNumber = "Васька".count { letter -> letter == 'а' } // Counts letters "a" in a
    // string "Васька", using anonymous function

    private val borderColor: String get() = "black" // What's this get() ?
    private var stringRepresentation: String
        get() = this.toString()
        set(value) {    // Custom setter. "value" can be named differently
            stringLengthFunc(value) // parses the string and assigns values to other properties
        }
    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation

    // Anonymous function. Returns a length of a passed string
    private val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    fun stringsDemo() {
        stringRepresentation = "1234567"
        println(stringRepresentation)   // What's this com.example.vladislav.androidstudy.kotlin.demo.StringsDemo@93ef7dd ?
        println("Hello world")
        println(string3)
        println(longString)
        println("1 + 2 = ${1 + 2}")
        println("\"asrgthyuujmhngb\".length = ${"asrgthyuujmhngb".length}")
        println("\"asrgthyuujmhngb\".count() = ${"asrgthyuujmhngb".count()}")
        println("Equals str1 with str2 = ${"str1".equals("str2")}")
        println("Compare row and row = ${"row".compareTo("row")}")
        println("Compare row and row2 = ${"row".compareTo("row2")}")
        println("Compare row2 and row = ${"row2".compareTo("row")}")
        println("\"some string\".get(2) = ${"some string".get(2)}")
        println("\"some string\"[2] = ${"some string"[2]}")
        println("\"some string\".subSequence(2, 8) = ${"some string".subSequence(2, 8)}")
//        val p: String by lazy {
//            // compute the string
//        }
        val p = "Some String".also(::println)   // Assigns and prints the string
        longString?.uppercase()  // Checking for a null before casting to uppercase
        stringLengthFunc("qwertyu")
    }
}
