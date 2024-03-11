package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Strings ops demo. String utilizes Unicode characters that use UTF-16 coding.
 *
 * @author Yanchenko Vladislav
 * @since 25.01.2021
 */
class StringsDemo {

    // private val string1: String  // Property must be initialized or be abstract
    // private val string2: String = null   // Null can not be a value of a non-null type string
    private val string2: String? = null     // Nullable value is null
    private val languageName: String = "Kotlin"
    private val string3: String = String.toString()
    private var longString = """
        This is a 
        long string that may contain several rows
    """

    // Counts letters "a" in a string "Васька", using anonymous function
    private val charNumber = "Васька".count { letter -> letter == 'а' }
//    private val charNumber = "Васька".count { it == 'а' }     // Same to previous one

    private val borderColor: String
        get() = "black" // Custom getter
    private var stringRepresentation: String = ""
        set(value) {    // Custom setter. "value" can be named differently
            field = stringLengthFunc(value).toString() // parses the string and assigns values to other properties
        }
    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation

    // Anonymous function. Returns a length of a passed string
    private val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    fun stringsDemo() {
        stringRepresentation = "1234567"
        println(stringRepresentation)   // 7
        println("Hello world")
        println(string3)        // kotlin.jvm.internal.StringCompanionObject@f9a32f6
        println(longString)
        println("1 + 2 = ${1 + 2}")
        val s = "abc" + 1
        println(s + "def")      // abc1def
        println("\"asrgthyuujmhngb\".length = ${"asrgthyuujmhngb".length}")
        println("\"asrgthyuujmhngb\".count() = ${"asrgthyuujmhngb".count()}")
        println("Equals str1 with str2 = ${"str1".equals("str2")}")
        println("Compare row and row = ${"row".compareTo("row")}")
        println("Compare row and row2 = ${"row".compareTo("row2")}")
        println("Compare row2 and row = ${"row2".compareTo("row")}")
        println("\"some string\".get(2) = ${"some string".get(2)}")
        println("\"some string\"[2] = ${"some string"[2]}")
        println("\"some string\".subSequence(2, 8) = ${"some string".subSequence(2, 8)}")
//        val p: String by lazy {   // This value is computed only when first time referred to
//            // compute the string
//        }
        val p = "Some String".also(::println)   // Assigns and prints the string
        longString.uppercase()  // Checking for a null before casting to uppercase
        stringLengthFunc("qwertyu")
        val s1 = "Hello, world!\n"  // Escaped strings can contain escaped characters.
        val text = """
            |Tell me and I forget.
            |Teach me and I remember.
            |Involve me and I learn.
            |(Benjamin Franklin)
            """.trimMargin()    // To remove leading whitespace from multiline strings
        // By default, a pipe symbol | is used as margin prefix, but you can choose another character and pass it as a parameter, like trimMargin(">").
        val price = """
            ${'$'}_9.99
        """ //  $_9.99
        // Formats with four decimals and sign
        val floatNumber = String.format("%+.4f", 3.141592)
        println(floatNumber)    // +3.1416
    }
}
