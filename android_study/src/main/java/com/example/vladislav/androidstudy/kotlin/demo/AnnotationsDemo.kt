package com.example.vladislav.androidstudy.kotlin.demo

/** https://medium.com/@anandgaur22/jvmstatic-jvmoverloads-jvmfield-annotations-in-kotlin-d856fe38fd92 */

class AnnotationsDemo {

    /** In Java:    DataBase.runSql ("SELECT * FROM Table"); */
    object DataBase {
        @JvmStatic
        fun runSql(query: String) {
        }
    }

    /** In Java:     new Event("ButtonClick") */
    data class Event @JvmOverloads constructor (
            val name: String, val screenName: String = "" )

    /**
     * In Java:
     * Event event = new Event ( "Photoclick", "Profile"):
     * System.out.println(event.name):
     */
    data class Event2 (@JvmField val name: String, val screenName: String = "")
}