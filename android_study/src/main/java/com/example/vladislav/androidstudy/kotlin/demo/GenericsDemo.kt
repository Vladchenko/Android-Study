package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Generics demo - демонстрация работы с обобщениями
 * https://www.youtube.com/watch?v=yRxIZD14Q_o
 * https://www.youtube.com/watch?v=bDlZeOhZnEE, https://www.youtube.com/watch?v=CiqxMmfuEj8
 */
class GenericsDemo {

    val item = Item(2)
    val items = listOf(Item(1), Item(2), Item(3))

    fun showT() = items.map { it.printT() }     // print(items) will also do

    class Item<T>(val t: T) {
        var value = t
        fun printT() = println(t)
    }

    fun print(t: Any) = println(t)

    //    fun <T> print(t: T) = println(t)  // Almost same to previous line. Ask LLM the difference.
    // This one preferred over previous one.
    fun <T> print(t: List<T>) = println(t)


    // From the Kotlin-Programming-The-Big-Nerd-Ranch-Guide-25.10.2021.pdf book

    abstract class Loot() {
        abstract val name: String
    }
    interface Sellable {
        val value: Int
    }
    class Fedora(
        override val name: String,
        override val value: Int
    ): Loot(), Sellable

    class Gemstones(
        override val name: String,
        override val value: Int
    ): Loot(), Sellable

    class Key(
        override val name: String
    ): Loot()

    class LootBox<T: Loot>(var contents: T) {
        var isOpen = false
            private set

        fun takeLoot() = contents
            .takeIf { !isOpen }
            .also { isOpen = true }
    }

    val lootBoxOne: LootBox<Fedora> = LootBox(Fedora("a generic-looking fedora", 15))
    val lootBoxTwo: LootBox<Gemstones> = LootBox(Gemstones("a generic-looking gemstone", 150))

    fun checkLootBox() {
        println(lootBoxOne.takeLoot()?.let {"This is your loot: $it"} ?: run { "Loot was taken before" })
        println(lootBoxOne.takeLoot()?.let {"This is your loot: $it"} ?: run { "Loot was taken before" })
    }

    // Unsing multiple generic constraints
    class DropOffBox<T> where T : Loot, T : Sellable {
        fun sellLoot(sellableLoot: T): Int {
            return (sellableLoot.value * 0.7).toInt()
        }
    }
}