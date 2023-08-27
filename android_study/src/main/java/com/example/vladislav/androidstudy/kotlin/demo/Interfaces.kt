package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Interfaces demo
 *
 * @author Yanchenko Vladislav on 27.08.2020.
 */
interface PersonNameProvider {
    val name: String

    //    val lastName : String = "Yan" //Property initializers are not allowed in interfaces
    // Interfaces in Kotlin may have a default realization and if there is one, then one mustn't
    // implement this function
    fun provideName(name: String? = "Vlad") = "Name is + $name"
    fun provideName2(name: String?) = name ?: "Vlad"
}

interface SessionProvider {
    val sessionId: String
}

class Interfaces {

    class InterfacesDemo(override val name: String) :
        PersonNameProvider {
        // Since interface doesn't allow a default value for a properties, so when implementing such interface,
        // one has to assign a value to "name". So, the overridden value has to be put into class :
        // override val name: String = ""  , or to constructor, as shown in this example
    }

    class InterfacesDemo2(override val sessionId: String) : PersonNameProvider,
        SessionProvider {
        override val name: String
            get() = "Vladchenko"    // Specific getter
    }

    class InterfacesDemo3(
        override val name: String,
        override val sessionId: String
    ) : PersonNameProvider, SessionProvider

    fun checkType(someClass: InterfacesDemo2) {
        if (someClass is SessionProvider) {
            println("$someClass is SessionProvider")
//            println((someClass as SessionProvider).sessionId)
        } else {
            println("$someClass is PersonNameProvider")
        }
    }
}
