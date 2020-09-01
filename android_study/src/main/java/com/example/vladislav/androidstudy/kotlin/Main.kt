package com.example.kotlinstudy

import android.content.Context
import com.example.kotlinstudy.demo.Stepik

/**
 * Main class
 * Some of a sources - https://www.youtube.com/watch?v=F9UC9DY-vIU&t=1416s	Important stuff at 1:29:35
 *
 * @author Yanchenko Vladislav on 17.08.2020.
 */
class Main {

    fun main(context: Context) {    // An entry point of a Kotlin application is the main function
//        Basics().numbersDemo()
//        Basics().stringsDemo()
//        Basics().arraysDemo()
//        Basics().mapDemo()
//        Basics().listDemo()
//        Basics().rangesDemo()
//        Basics().otherDemo()
//        Basics().whenDemo()
//        Basics().temp()
//        Basics().lambdaDemo()
//        Basics().varArgsDemo2(1, 2.0, 'c', "arg1")
//        Basics().instantiationDemo()
//        Basics().sayHello(mapOf("Hi" to "Vlad", "Hello" to "Vladchenko", "Greetings" to "Vladon"))
//        Basics().sayHello("Zdarova", listOf("Vlad", "Vladchenko", "Vladon"))
//        Car(listOf(Car.Wheel())).demo()
//        println(CompanionObject.NAME)
//        Derived("Vlad", "Yan")
//        Files(context).filesDemo()
//        Interfaces().checkType(Interfaces.InterfacesDemo2(","));
//        println(Stepik().toJSON(listOf(1, 2, 3, 42, 555)))
//        println(Stepik().joinOptions(listOf("a","b","c")))
//        println(Stepik().containsEven(listOf(1,2,3,4,5)))
        println(Pair(Stepik.RationalNumber(1,2),Stepik.RationalNumber(1,2)))
//        CoroutinesBasics().simpleCoroutineDemo3()
//        Coroutines().demoDispatchersAndThreads2()
//        println(KotlinKoans().joinOptions(listOf("1", "2", "3")))
//        Utils.printArray(arrayOf(1,2,3,4))
    }
}
