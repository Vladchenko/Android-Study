package com.example.vladislav.androidstudy.kotlin.demo

import java.io.File

/**
 * Check https://kotlinlang.org/docs/scope-functions.html?_gl=1%2Acumodz%2A_gcl_au%2AODU4MTE0NDU2LjE3NTc2ODk2MDk.%2A_ga%2ANzI4NDY5NTQwLjE3NTc2ODk2MTE.%2A_ga_9J976DJZ68%2AczE3NTc2OTMxNzIkbzIkZzEkdDE3NTc3MDAzMjUkajIxJGwwJGgw&_cl=MTsxOzE7Y1ZoeldHWThQZlM4VDlhU3FuNUxTa2JLdllWUW9ZbVcyZ3Jadmg5Y2kxZVNMVkVVQVJPUExOTDg3Y1RZNExqNTs%3D#scope-functions.md
 * Все скоуп-функции в Kotlin являются inline. Это значит что исчезают накладные расходы на лямбды.
 * Без inline каждая лямбда создавала бы анонимный класс или захватывала переменные,
 * создавая лишние объекты в heap'е. inline "вклеивает" код лямбды прямо в место вызова.
 * Реальные плюсы
 * ✅ Нет аллокации объектов для лямбд
 * ✅ Меньше нагрузки на GC
 * ✅ Возможность использовать non-local return (выход из внешней функции)
 * ✅ Лучшая производительность в циклах/часто вызываемом коде
 * Потенциальные минусы (редкие)
 * ⚠️ Увеличение размера байткода (копирование кода в каждое место вызова)
 * ⚠️ Может негативно сказаться на производительности, если огромная лямбда инлайнится в сотни мест (но это редкость)
 * Когда инлайнинг не даёт выгоды?
 * Если лямбда не захватывает переменные и не создаёт объекты сама по себе, разница может быть незаметна. Но Kotlin всё равно делает их inline для единообразия и поддержки non-local return.
 *
 * apply - настройка, also - побочные эффекты
 */
class ScopeFunctionsDemo {

    fun withDemo2() {    // Calling multiple methods on an object instance (with)
        class Turtle {
            fun penDown() {
                /** Empty for demo */
            }

            fun penUp() {
                /** Empty for demo */
            }

            fun turn(degrees: Double) {
                /** Empty for demo */
            }

            fun forward(pixels: Double) {
                /** Empty for demo */
            }
        }

        val myTurtle = Turtle()
        with(myTurtle) {
            //draw a 100 pix square
            penDown()
            for (i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }
    }

    fun applyDemo() {
        data class Car(var brand: String, var model: String, var year: Int)

        fun main() {
            val car = Car("", "", 0)

            // Улучшите с apply:
            car.brand = "Toyota"
            car.model = "Camry"
            car.year = 2020
            println(car)
        }

        fun fixApplyExample1() {
            val car = Car("", "", 0).apply {
                brand = "Toyota"
                model = "Camry"
                year = 2020
                println(this)
            }
        }

        /*
        Минусы:
        apply предназначен для настройки объекта, а вывод в консоль - это побочное действие
        Смешивает конфигурацию и логирование
        Может запутать при чтении кода
         */
        fun fixApplyExample2() {
            val car = Car("", "", 0).apply {
                brand = "Toyota"
                model = "Camry"
                year = 2020
            }.also {
                println(this)
            }
        }
        /*
        Плюсы:
        Разделение ответственности: apply - настройка, also - побочные эффекты
        Код более читаемый и следует принципу единственной ответственности
        also даёт доступ через it, явно показывая, что объект не изменяется
         */
        //Идеальный вариант с именованным параметром:
        val car = Car("", "", 0).apply {
            brand = "Toyota"
            model = "Camry"
            year = 2020
        }.also { configuredCar ->
            println(configuredCar)  // явно видно, что это уже настроенный объект
        }
        /*
        Вывод: В вашем примере лучше использовать also для println, потому что:
        apply должен заниматься только настройкой объекта
        also специально создан для побочных действий
        Код становится самодокументируемым
        Так что ваша интуиция верна - держать println внутри apply можно, но лучше вынести в also! 👌
         */
    }

    fun alsoDemo() {
        fun main() {
            val numbers = mutableListOf(1, 2, 3)

            // Улучшите с also:
            numbers.add(4)
            println("Список после добавления: $numbers")
            numbers.add(5)
            println("Список после ещё одного добавления: $numbers")
        }

        fun fixAlsoExample() {
            mutableListOf(1, 2, 3)
                .apply { add(4) }
                .also { println("Список после добавления: $it") }
                .apply { add(5) }
                .also { println("Список после ещё одного добавления: $it") }
        }
    }

    fun letDemo(): Unit {
        fun main() {
            val name: String? = "Анна"

            // Улучшите с let:
            if (name != null) {
                println("Имя: $name")
                println("Длина имени: ${name.length}")
            }
        }

        fun mainFixed() {
            val name: String? = "Анна"
            name?.let {
                println("Имя: $it")
                println("Длина имени: ${it.length}")
            }
        }
    }

    fun runDemo() {
        data class Rectangle(val width: Int, val height: Int)

        fun main() {
            val rect = Rectangle(5, 3)

            // Улучшите с run:
            val area = rect.width * rect.height
            val perimeter = 2 * (rect.width + rect.height)
            println("Площадь: $area, Периметр: $perimeter")
        }

        fun mainFixed() {
            Rectangle(5, 3).run {
                println("Площадь: ${width * height}, Периметр: ${2 * (width + height)}")
            }
        }
        /*
        ✅ run здесь идеально подходит, потому что вы:
        Работаете с объектом (прямой доступ к свойствам)
        Выполняете несколько действий
        Не нуждаетесь в возврате объекта (возвращается последнее выражение, но вы его не используете)
         */
    }

    fun withDemo() {
        data class StringBuilderWrapper(val builder: StringBuilder)

        fun main() {
            val wrapper = StringBuilderWrapper(StringBuilder())

            // Улучшите с with:
            wrapper.builder.append("Hello")
            wrapper.builder.append(" ")
            wrapper.builder.append("World")
            wrapper.builder.append("!")
            println(wrapper.builder.toString())
        }

        fun fixedMain() {
            StringBuilderWrapper(StringBuilder()).apply {
                with(builder) {
                    append("Hello")
                    append(" ")
                    append("World")
                    append("!")
                }
            }.also {
                println(it.builder.toString())
            }
            // Здесь apply не очень уместен, потому что вы ничего не настраиваете в самом wrapper. Можно проще:
            StringBuilderWrapper(StringBuilder()).also { wrapper ->
                with(wrapper.builder) {
                    append("Hello")
                    append(" ")
                    append("World")
                    append("!")
                }
            }.also {
                println(it.builder.toString())
            }
            // Но ваш код работает! 👍 Просто apply здесь лишний, так как wrapper не требует настройки.
        }
    }

    fun takeIfDemo() {
        fun main() {
            val number = 42

            // Улучшите с takeIf:
            if (number > 0) {
                println("$number - положительное число")
            }
        }

        fun fixedMain() {
            val number = 42
            number.takeIf { value -> value > 0 }
                ?.let { value -> println("$value - положительное число") }
        }
    }

    fun takeUnlessDemo() {
        fun main() {
            val text = ""

            // Улучшите с takeUnless:
            if (text.isEmpty()) {
                println("Текст пустой")
            }
        }

        fun fixedMain() {
            val text = ""

            text
                .takeUnless { it.isEmpty() }
                ?: run { println("Текст пустой") }
        }
    }

    fun repeatDemo() {
        fun main() {
            // Улучшите с repeat:
            for (i in 1..5) {
                println("Шаг $i")
            }
        }

        fun fixedMain() {
            repeat(5) { i ->
                println("Шаг $i")
            }
        }
    }

    fun applyAlsoDemo() {
        data class User(var name: String, var age: Int)

        fun main() {
            val user = User("", 0)

            // Установите имя "Дима" и возраст 30
            // И выведите результат в формате "Создан пользователь: User(name=Дима, age=30)"
            // Используйте apply и also
        }

        fun fixedMain() {
            val user = User("", 0)

            user.apply {
                name = "Дима"
                age = 30
            }.also {
                println("Создан пользователь: $it")
            }
            /*
            Почему это идеально:
            apply - настраивает объект (устанавливает свойства)
            also - выполняет побочное действие (вывод на печать)
            Чёткое разделение ответственности
            Прямой доступ к свойствам внутри apply
            it внутри also даёт доступ к настроенному объекту
            */
        }
    }

    fun letRunDemo() {
        data class Book(val title: String?, val pages: Int)

        fun main() {
            val book = Book("Война и мир", 1200)

            // Если title не null, выведите:
            // "Книга: [title], страниц: [pages]"
            // Используйте let и run
        }

        fun fixedMain() {
            val book = Book("Война и мир", 1200)
            book.title?.let {
                println("Книга: ${it}, страниц: ${book.pages}")
            }
            // в этом примере run действительно не нужен.
            // Или более сложный пример, где run действительно нужен:
            book.title?.let { title ->
                book.run {
                    val summary = "$title ($pages стр.)"
                    println(summary)
                    summary.length  // run может возвращать значение
                }
            } ?: println("Книга без названия")
        }
    }

    fun takeIfLetDemo() {
        fun main() {
            val numbers = listOf(1, 2, 3, 4, 5)

            // Если список не пустой, выведите:
            // "Первый элемент: [первый], всего элементов: [размер]"
            // Используйте takeIf и let
        }

        fun mainFixed() {
            val numbers = listOf(1, 2, 3, 4, 5)
            numbers.takeIf { list -> list.isNotEmpty() }
                ?.let { list ->
                    println("Первый элемент: ${list.first()}, всего элементов: ${list.size}")
                }
        }
    }

    fun withAlsoDemo() {
        data class Message(val text: String, val timestamp: Long)

        fun main() {
            val message = Message("Привет!", System.currentTimeMillis())

            // Выведите текст сообщения и время в формате:
            // "Текст: [text]"
            // "Время: [timestamp]"
            // И также выведите сам объект message
            // Используйте with и also
        }

        fun mainFixed() {
            val message = Message("Привет!", System.currentTimeMillis())
            with(message) {
                println("Текст: $text")
                println("Время: $timestamp")
            }.also {
                println(it)
            }
            /*
            Проблема: with возвращает последнее выражение в блоке. Здесь последнее выражение
            - это println("Время: $timestamp"), который возвращает Unit.
            Поэтому it в also будет Unit, а не message.
            Исправленный вариант:
             */
            with(message) {
                println("Текст: $text")
                println("Время: $timestamp")
                this  // явно возвращаем message
            }.also {
                println(it)
            }
        }
    }

    data class Person(var name: String, var age: Int, var city: String)

    fun exampleToFix1() {
        val person = Person("", 0, "")

        // Устанавливаем свойства
        person.name = "Анна"
        person.age = 25
        person.city = "Москва"

        // Выводим информацию
        println("Имя: ${person.name}")
        println("Возраст: ${person.age}")
        println("Город: ${person.city}")

        // Увеличиваем возраст
        person.age = person.age + 1

        // Снова выводим
        println("Обновленный возраст: ${person.age}")
    }

    fun exampleFixed1() {
        val person = Person("", 0, "").apply {
            name = "Анна"
            age = 25
            city = "Москва"
        }.also {
            println("Имя: ${it.name}")
            println("Возраст: ${it.age}")
            println("Город: ${it.city}")
        }.apply {
            age++
        }.also {
            println("Обновленный возраст: ${it.age}")
        }
    }

    fun exampleToFix2() {
        data class Address(val street: String?, val city: String?, val zipCode: String?)
        data class User(val name: String, val address: Address?)

        fun printUserInfo(user: User?) {
            if (user != null) {
                val name = user.name
                println("Имя пользователя: $name")

                if (user.address != null) {
                    val street = user.address.street
                    if (street != null) {
                        println("Улица: $street")
                    } else {
                        println("Улица не указана")
                    }

                    val city = user.address.city
                    if (city != null) {
                        println("Город: $city")
                    }

                    val zip = user.address.zipCode
                    if (zip != null) {
                        println("Индекс: $zip")
                    }
                } else {
                    println("Адрес не указан")
                }
            } else {
                println("Пользователь не найден")
            }
        }

        fun main() {
            val user1 = User("Иван", Address("Ленина 10", "Москва", "101000"))
            val user2 = User("Петр", null)
            val user3 = null

            printUserInfo(user1)
            printUserInfo(user2)
            printUserInfo(user3)
        }
    }

    fun exampleFixed2() {
        data class Address(val street: String?, val city: String?, val zipCode: String?)
        data class User(val name: String, val address: Address?)

        fun printUserInfo(user: User?) {
            user?.let { user ->
                println(user.name)
                user.address?.let { address ->
                    address.street?.let { street ->
                        println("Улица: $street")
                    } ?: run {
                        println("Улица не указана")
                    }
                    address.city?.let { city ->
                        println("Город: $city")
                    }
                    address.zipCode?.let { code ->
                        println("Индекс: $code")
                    }
                } ?: run {
                    println("Адрес не указан")
                }
            } ?: run {
                println("Пользователь не найден")
            }
        }

        fun check() {
            val user1 = User("Иван", Address("Ленина 10", "Москва", "101000"))
            val user2 = User("Петр", null)
            val user3 = null

            printUserInfo(user1)
            printUserInfo(user2)
            printUserInfo(user3)
        }

        check() // Runs to check code

        fun printUserInfoFixFromDeepseek(user: User?) {
            user ?: run {
                println("Пользователь не найден")
                return
            }

            println("Имя пользователя: ${user.name}")

            user.address?.run {
                street?.let { println("Улица: $it") } ?: println("Улица не указана")
                city?.let { println("Город: $it") }
                zipCode?.let { println("Индекс: $it") }
            } ?: println("Адрес не указан")

            /*
             * Преимущества моего варианта:
             *      Меньше вложенности
             *      run позволяет работать с address напрямую, без лишней переменной
             *      Ранний возврат через return делает код плоским
             *      Более читаемо для сложной логики
             */
        }
    }

    fun exampleToBeFixed3() {
        data class Product(val name: String, val price: Double, val quantity: Int)

        fun processOrder(products: List<Product>): Map<String, Any> {
            val result = mutableMapOf<String, Any>()

            // Фильтруем товары с количеством > 0
            val availableProducts = mutableListOf<Product>()
            for (product in products) {
                if (product.quantity > 0) {
                    availableProducts.add(product)
                }
            }

            // Считаем общую стоимость
            var totalCost = 0.0
            for (product in availableProducts) {
                totalCost += product.price * product.quantity
            }

            // Формируем список названий
            val names = mutableListOf<String>()
            for (product in availableProducts) {
                names.add(product.name)
            }

            result["products"] = names
            result["total"] = totalCost
            result["count"] = availableProducts.size

            // Применяем скидку если больше 3 товаров
            if (availableProducts.size > 3) {
                totalCost *= 0.9
                result["total_with_discount"] = totalCost
                result["discount_applied"] = true
            } else {
                result["discount_applied"] = false
            }

            // Логируем результат
            println("Обработано товаров: ${availableProducts.size}")
            println("Общая стоимость: $totalCost")

            return result
        }

        fun main() {
            val products = listOf(
                Product("Ноутбук", 50000.0, 1),
                Product("Мышь", 1000.0, 2),
                Product("Клавиатура", 3000.0, 0),
                Product("Монитор", 15000.0, 1)
            )

            println(processOrder(products))
        }
        main()
    }

    fun exampleFixed3() {
        data class Product(val name: String, val price: Double, val quantity: Int)

        fun processOrder(products: List<Product>): Map<String, Any> {
            val result = mutableMapOf<String, Any>()
            val availableProducts = products.filter {
                it.quantity > 0
            }

            result.putAll(
                mapOf(
                    "products" to availableProducts.map(Product::name),
                    "total" to availableProducts.sumOf {
                        it.price * it.quantity
                    },
                    "count" to availableProducts.size
                )
            ).also {
                if (availableProducts.size > 3) {
                    result["total_with_discount"] = (result["total"] as Double) * 0.9
                    result["discount_applied"] = true
                } else {
                    result["discount_applied"] = false
                }
            }.also {
                println("Обработано товаров: ${availableProducts.size}")
                println("Общая стоимость: ${result["total"]}")
            }
            return result   //.toMutableMap() needed
        }

        fun main() {
            val products = listOf(
                Product("Ноутбук", 50000.0, 1),
                Product("Мышь", 1000.0, 2),
                Product("Клавиатура", 3000.0, 0),
                Product("Монитор", 15000.0, 1)
            )

            println(processOrder(products))
        }
        main()
        /*
        Анализ вашего кода:
        ✅ filter - правильно для отбора доступных товаров
        ✅ map(Product::name) - элегантное преобразование в список названий
        ✅ sumOf - идеально для подсчёта общей стоимости
        ✅ putAll с mapOf - хороший способ добавить несколько значений сразу
        ✅ also - правильно используете для побочных эффектов (логирование)

        Но есть небольшие недочёты:
            also вызывается на Map<*, *>? (возвращаемом putAll), что может сбивать с толку
            Можно объединить логику скидки и логирования
            result всё ещё мутабельный - можно сделать иммутабельным
         */

        fun processOrderFromDeepseek(products: List<Product>): Map<String, Any> {
            val availableProducts = products.filter { it.quantity > 0 }

            val total = availableProducts.sumOf { it.price * it.quantity }

            return buildMap<String, Any> {
                put("products", availableProducts.map(Product::name))
                put("total", total)
                put("count", availableProducts.size)

                if (availableProducts.size > 3) {
                    put("total_with_discount", total * 0.9)
                    put("discount_applied", true)
                } else {
                    put("discount_applied", false)
                }
            }.also { result ->
                println("Обработано товаров: ${availableProducts.size}")
                println("Общая стоимость: ${result["total"]}")
            }
        }
        /*
        Преимущества:
        buildMap - создаёт иммутабельную Map и позволяет строить её в скоупе
        Нет мутабельной переменной result вне скоупа
        Логика скидки внутри buildMap (там, где формируются данные)
        also вызывается на финальной Map, что логичнее
        Но ваш код тоже очень хорош и показывает понимание scope-функций!
         */
    }

    fun exampleToBeFixed4() {
        fun saveUserData(userId: String, data: String): Boolean {
            val directory = File("users/$userId")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, "profile.txt")
            var success = false

            try {
                file.writeText(data)
                success = true

                // Логируем успех
                println("Данные пользователя $userId сохранены")
                println("Размер файла: ${file.length()} байт")

                // Создаем бэкап
                val backupFile = File(directory, "profile_backup.txt")
                file.copyTo(backupFile, overwrite = true)

            } catch (e: Exception) {
                println("Ошибка при сохранении: ${e.message}")
                success = false
            }

            return success
        }

        fun main() {
            saveUserData("123", "Привет, мир!")
        }
        main()
    }

    fun exampleFixed4() {
        fun saveUserData(userId: String, data: String): Boolean {
            val directory = File("users/$userId")
            directory.mkdirs().takeUnless { directory.exists() }
            return File(directory, "profile.txt").runCatching {
                writeText(data)
                println("Данные пользователя $userId сохранены")
                println("Размер файла: ${length()} байт")
                copyTo(
                    File(directory, "profile_backup.txt"),
                    overwrite = true
                )
            }.isSuccess
        }

        fun main() {
            saveUserData("123", "Привет, мир!")
        }
        main()
        /*
        Анализ вашего кода:
        ✅ runCatching - отличная замена try-catch, возвращает Result
        ✅ directory.mkdirs().takeUnless { directory.exists() } - интересный функциональный подход
        ✅ Цепочка вызовов без лишних переменных
        ✅ length() вызывается прямо в контексте файла (благодаря скоупу runCatching)
        ✅ copyTo в той же цепочке

        Но есть небольшой нюанс:
            Строка directory.mkdirs().takeUnless { directory.exists() }:
            mkdirs() возвращает Boolean (успех создания)
            takeUnless отбрасывает это значение если директория существует
            Но результат никак не используется (просто игнорируется)

            Мой вариант с небольшим уточнением:
         */
        fun saveUserDataImproved(userId: String, data: String): Boolean {
            val directory = File("users/$userId").apply {
                if (!exists()) mkdirs()
            }

            return File(directory, "profile.txt").runCatching {
                writeText(data)
                println("Данные пользователя $userId сохранены")
                println("Размер файла: ${length()} байт")
                copyTo(File(directory, "profile_backup.txt"), overwrite = true)
            }.isSuccess
        }

        fun saveUserDataImproved2(userId: String, data: String): Boolean {
            return File("users/$userId").let { dir ->
                dir.apply { if (!exists()) mkdirs() }
                File(dir, "profile.txt")
            }.runCatching {
                writeText(data)
                println("Данные пользователя $userId сохранены")
                println("Размер файла: ${length()} байт")
                copyTo(File(parentFile, "profile_backup.txt"), overwrite = true)
            }.isSuccess
        }
    }

    fun exampleToBeFixed5() {
        data class OrderItem(val name: String, val price: Double, val quantity: Int)
        data class Customer(val name: String, val email: String?, val phone: String?)
        data class Order(
            val id: String,
            val items: List<OrderItem>,
            val customer: Customer?,
            var status: String = "NEW"
        )

        fun processComplexOrder(order: Order?): String {
            // Проверка на null
            if (order == null) {
                return "Заказ не существует"
            }

            // Обновляем статус
            order.status = "PROCESSING"

            // Проверяем наличие товаров
            if (order.items.isEmpty()) {
                order.status = "REJECTED"
                return "Заказ отклонён: нет товаров"
            }

            // Проверяем контактные данные клиента
            if (order.customer == null) {
                order.status = "REJECTED"
                return "Заказ отклонён: нет данных клиента"
            }

            // Логируем информацию о заказе
            println("Обработка заказа ${order.id}")
            println("Товаров: ${order.items.size}")

            // Рассчитываем сумму
            var total = 0.0
            for (item in order.items) {
                total += item.price * item.quantity
            }
            println("Сумма заказа: $total")

            // Проверяем email для отправки уведомления
            if (order.customer.email != null) {
                println("Отправляем уведомление на ${order.customer.email}")
            } else {
                println("Email не указан, уведомление не отправлено")
            }

            // Проверяем телефон
            if (order.customer.phone != null) {
                println("Телефон для связи: ${order.customer.phone}")
            }

            // Финальный статус
            order.status = "COMPLETED"
            return "Заказ ${order.id} успешно обработан"
        }

        fun main() {
            val order = Order(
                id = "ORD-001",
                items = listOf(
                    OrderItem("Книга", 500.0, 2),
                    OrderItem("Ручка", 50.0, 5)
                ),
                customer = Customer("Анна", "anna@mail.com", null)
            )

            println(processComplexOrder(order))
        }
        main()
    }

    fun exampleFixed5() {
        data class OrderItem(val name: String, val price: Double, val quantity: Int)
        data class Customer(val name: String, val email: String?, val phone: String?)
        data class Order(
            val id: String,
            val items: List<OrderItem>,
            val customer: Customer?,
            var status: String = "NEW"
        )

        fun processComplexOrder(order: Order?): String {
            order ?: run {  // if order == null
                return "Заказ не существует"
            }
            with(order) {
                status = "PROCESSING"
                if (items.isEmpty()) {
                    status = "REJECTED"
                    return "Заказ отклонён: нет товаров"
                }
                customer ?: run {
                    status = "REJECTED"
                    return "Заказ отклонён: нет данных клиента"
                }
                println("Обработка заказа ${id}")
                println("Товаров: ${items.size}")
                println("Сумма заказа: ${items.sumOf { item -> item.price * item.quantity }}")
                customer.email?.let { email ->
                    println("Отправляем уведомление на $email")
                } ?: run {
                    println("Email не указан, уведомление не отправлено")
                }
                customer.phone?.let { phone ->
                    println("Телефон для связи: $phone")
                }
                status = "COMPLETED"
                return "Заказ $id успешно обработан"
            }
        }

        fun main() {
            val order = Order(
                id = "ORD-001",
                items = listOf(
                    OrderItem("Книга", 500.0, 2),
                    OrderItem("Ручка", 50.0, 5)
                ),
                customer = Customer("Анна", "anna@mail.com", null)
            )

            println(processComplexOrder(order))
        }
        main()

        fun Order.logOrderInfo() {
            println("Обработка заказа $id")
            println("Товаров: ${items.size}")
            println("Сумма заказа: ${items.sumOf { it.price * it.quantity }}")
        }

        fun Order.notifyCustomer() {
            customer?.email?.let {
                println("Отправляем уведомление на $it")
            } ?: println("Email не указан, уведомление не отправлено")

            customer?.phone?.let {
                println("Телефон для связи: $it")
            }
        }

        fun processComplexOrderDeepseekV1(order: Order?): String = order
            ?.run {
                status = "PROCESSING"
                when {
                    items.isEmpty() -> {
                        status = "REJECTED"
                        return@run "Заказ отклонён: нет товаров"
                    }

                    customer == null -> {
                        status = "REJECTED"
                        return@run "Заказ отклонён: нет данных клиента"
                    }

                    else -> {
                        logOrderInfo()
                        notifyCustomer()
                        status = "COMPLETED"
                        "Заказ $id успешно обработан"
                    }
                }
            }
            ?: "Заказ не существует"

        fun processComplexOrderDeepseekV2(order: Order?): String = order
            ?.apply { status = "PROCESSING" }
            ?.takeUnless { it.items.isEmpty() }
            ?.takeUnless { it.customer == null }
            ?.run {
                logOrderInfo()
                notifyCustomer()
                status = "COMPLETED"
                "Заказ $id успешно обработан"
            }
            ?: when {
                order == null -> "Заказ не существует"
                order.items.isEmpty() -> {
                    order.status = "REJECTED"
                    "Заказ отклонён: нет товаров"
                }

                else -> {
                    order.status = "REJECTED"
                    "Заказ отклонён: нет данных клиента"
                }
            }
    }

    // returns Goodbye
    fun test(): String {
        val result = "Hello".apply {
//            return@apply "Goodbye"
        }
        return result
    }

    fun main() {
        val user: User = User("Alice", 25)

        val age = user.apply {
            println("Before: $name, $age")
            incrementAge()
            println("After: $name, $age")
        }.age

        println("Age: $age")
    }

    data class User(var name: String, var age: Int) {
        fun incrementAge() {
            age++
        }
    }
}