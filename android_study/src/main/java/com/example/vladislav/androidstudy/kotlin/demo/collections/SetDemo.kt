package com.example.vladislav.androidstudy.kotlin.demo.collections

/**
 * https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-set/
 */
class SetDemo {

    fun setDemo() {
        println("Kotlin" in setOf("Java", "Scala")) //false
        println(setOf(4, 7, 2, 9, 12, 10, 11).maxOrNull()) //12
        // {[4, 7, 2, 9, 12, 10, 11]=[4, 7, 2, 9, 12, 10, 11]}
        println(mapOf(setOf(4, 7, 2, 9, 12, 10, 11) to setOf(4, 7, 2, 9, 12, 10, 11)))
        // [(4, 40), (7, 70), (2, 20), (9, 90), (12, 120), (10, 100), (11, 110)]
        println(setOf(4, 7, 2, 9, 12, 10, 11).zip(setOf(40, 70, 20, 90, 120, 100, 110)))
        // {4=40, 7=70, 2=20, 9=90, 12=120, 10=100, 11=110}
        println(setOf(4, 7, 2, 9, 12, 10, 11).zip(setOf(40, 70, 20, 90, 120, 100, 110)).toMap())
        val set: Set<Int> = emptySet()    // Empty integer set
        val set2: Set<Int> = setOf(1, 2, 3, 4, 5, 6)    // Integer set
        val map = set2.partition { it % 2 == 0 }    // Splitting lists which conclude pair
        println(map)    // ([2, 4, 6], [1, 3, 5])
        println(setOfNotNull(null, 5, "s", null, "fgh"))    // [5, s, fgh]
        println(3 in set2)  // true     // Tells if 3 present in set2
        val hashSet = hashSetOf(1, 2, 3)
        hashSet.add(4)
        val sortedSet = sortedSetOf(5, 20, 3, 1, 9) // Creates treeSet that has sorted set in it
        sortedSet.add(0)
        println(sortedSet)  // [0, 1, 3, 5, 9, 20]
        hashSet.addAll(sortedSet)   // Merging two sets, same as hashSet.union(sortedSet)
        println(hashSet)  //[0, 1, 2, 3, 4, 20, 5, 9]
    }
}