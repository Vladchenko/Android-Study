package com.example.vladislav.androidstudy.kotlin

import android.content.Context
import com.example.vladislav.androidstudy.kotlin.demo.CoroutinesBasics
import com.example.vladislav.androidstudy.kotlin.study.leetcode.Solution

/**
 * Main class
 * Some of a sources - https://www.youtube.com/watch?v=F9UC9DY-vIU&t=1416s	Important stuff at 1:29:35
 *
 * @author Yanchenko Vladislav on 17.08.2020.
 */
class Main {

    fun main(
        context: Context,
        callback: (String) -> Unit
    ) {    // An entry point of a Kotlin application is the main function
//        InitOrderDemo("Vlad")
//         ArraysListsDemo().arraysDemo()
//        Basics().equalityDemo()
//        Basics().dataClassCopyDemo()
//        Basics().typesDemo()
        // Basics().destructuringDeclaration()
        // extensionFunctionDemo()
        // Basics().idiomsDemo(context)
//         Basics().listDemo()
//         loopsDemo()
//         Basics().rangesDemo()
        // Basics().javaRelatedDemo()
//        Basics().otherDemo()
//         Basics().setDemo()
//        Basics().whenDemo()
//       Basics().temp()
//        Basics().testIsPalindromeMethod()
//         Basics().someExample()
//        LambdaDemo().lambdaDemo()
//        Basics().repeatNInvocation()
//        Basics().varArgsDemo(1, 2.0, 'c', "arg1")
//         Basics().varArgsDemo2()
//         Basics().answerUsageExample()
//        Basics().instantiationDemo()
//        Basics().sayHello(mapOf("Hi" to "Vlad", "Hello" to "Vladchenko", "Greetings" to "Vladon"))
//        Basics().sayHello("Zdarova", listOf("Vlad", "Vladchenko", "Vladon"))
//        Car(listOf(Car.Wheel())).demo()
//         println(whenDemo4(5))   // x is in the range
//         CoroutinesBasics(callback).coroutineDemo()
//        CoroutinesBasics(callback).coroutineDemoScope()
//         CoroutinesBasics(callback).coroutineDemo1()
//         CoroutinesBasics().coroutineDemo1_1()
//         CoroutinesBasics().coroutineDemo1_2()
//         CoroutinesBasics().coroutineDemo1_3()
//         CoroutinesBasics(callback).temp()
//         CoroutinesBasics().coroutineDemo2()
//        CoroutinesBasics(callback).simpleCoroutineDemo8()
//         CoroutinesBasics(callback).deferredDemo()
//        CoroutinesBasics().coroutineDemoUnconfined()
//         CoroutinesBasics().coroutineDemoUnconfined2()
//         CoroutinesBasics(callback).coroutineDemo3()
        // CoroutinesBasics().simpleCoroutineDemo()
//        CoroutinesBasics(callback).simpleCoroutineDemo2()
//        CoroutinesBasics(callback).simpleCoroutineDemo10()
//        CoroutinesBasics().simpleCoroutineDemo9()
//         CoroutinesBasics().cancelDemo()
//         CoroutinesBasics(callback).cancelNotWorkingDemo()
         CoroutinesBasics(callback).cancelWorkingDemo()
//         CoroutinesBasics().twoNetworkCallsSequentially()
        // CoroutinesBasics().demoDispatchersAndThreads2()
        //  CoroutinesBasics().simpleCoroutineDemo11()
//        CoroutinesBasics(callback).flowDemo1Print()
//         testIsEmptyOrNull()
//         MapDemo.mapDemo()
//        println(CompanionObject.NAME)
//         println("DemoSmartCasts " + DemoSmartCasts().evaluate())
//        Derived("Vlad", "Yan")
//         ExceptionsDemo().demonstrateException()
//        Interfaces().checkType(Interfaces.InterfacesDemo2(","));
//         MapDemo.mapDemo()
        // MapDemo.peopleDemo()
//        NumbersDemo().numbersDemo()
//        println(Stepik().toJSON(listOf(1, 2, 3, 42, 555)))
//        println(Stepik().joinOptions(listOf("a","b","c")))
//        println(Stepik().containsEven(listOf(1,2,3,4,5)))
//         println(Pair(Stepik.RationalNumber(1,2),Stepik.RationalNumber(1,2)))
//        println(KotlinKoans().joinOptions(listOf("1", "2", "3")))
//        println(KotlinKoans().getPattern())
//        Utils.printArray(arrayOf(1,2,3,4))
//         println("dfd fgbyuk uyksef sfd vgv".retrievePalindromesNumber())
//         println("xcv vvbn fgh asd yui iop".replaceString("asd", "VFD"))
//         println("чя ва ми ва as ва hj".distinctStrings())
        // println("null 123 123.3".retrieveIntegerNumbers()) //2
//         println("485 23 0-".replaceDigitsWithSymbolicRepresentation())
//         println("  sd    fg   df vb к        ео   ".removeExcessiveSpaces2())
//         println("вап 567 см 23.3 5 апр 6пр 34,5 вап 345678".distinctChars())
        // println("0 5 8 10 13 19 200 400 305 514 685".replaceNumbersWithSymbolicRepresentation())
//         println(evaluateGuess2().evaluateGuess("ABCD", "ABCD"))
//         println(evaluateGuess2().main(arrayOf("")))
//         mapVsFlatmap()
//         example1()
//         FilesDemo(context).filesDemo()
//         AdventOfCode2020().task1()
//         println("wio4efnkjscjhvkjfipo2qkwdl".toDescendingOrderChars())
//         println("jdeгвав".toBackwardsOrder())
//         println("vc8s6cz16c".charAppearanceNumber('c'))
//         println("as df см ап".toSortedOrderStrings())
//         println("ыва апр ыва sd укеннг вмро s ыа asdf".findShortestStringMap())
//         println("dv u erth sdvty df ".getDistanceBetween2Strings("erth", "dv"))
//         println("sfghhr vcse s fbrythm ыва аиптпрт вы".toLenghtSortedStrings())
//         println("Java java 4 Java X Java0 Java 0 Java 0.1 Java 1 Java 1.0 Java 1.1 Java   5 Java 1.8 Java 10 Java 11".getAllJavaVersions())
//         println(retrieveNumberOfLuckyTickets())
//        println("      4    5 3   6664   783      ".toAlphabetSortedStrings())
//        println("make this words begin with capital letters".capitalizeFirstLetters())
//        StringsDemo().stringsDemo()
//        println("q".isEveryCharFrequencyUnique())
//        println("qq".isEveryCharFrequencyUnique())
//        println("qqwwe".isEveryCharFrequencyUnique())
//        println("qqqwwe".isEveryCharFrequencyUnique())
//        println("  zzzzsssee".isEveryCharFrequencyUnique())

        // val mineField = MineField()
        // mineField.initializeMineField()
        // mineField.makeRobotPath(20, 8)
        // mineField.printInitialField()
        // mineField.printOptimizedField()

        // ParametrizationDemo().showT()

//        println(Solution().twoSum(intArrayOf(1,2,3,4,5,3,1,2,4), 6).contentToString())

//        println(Solution().isPalindrome2(1))
//        println(Solution().isPalindrome2(12))
//        println(Solution().isPalindrome2(121))
//        println(Solution().isPalindrome2(1212))
//        println(Solution().isPalindrome2(1221))
//        println(Solution().isPalindrome2(12121))

        val solution = Solution()
//        println(solution.romanToInt("I"))
//        println(solution.romanToInt("II"))
//        println(solution.romanToInt("III"))
//        println(solution.romanToInt("IV"))
//        println(solution.romanToInt("V"))
//        println(solution.romanToInt("VI"))
//        println(solution.romanToInt("VII"))
//        println(solution.romanToInt("VIII"))
//        println(solution.romanToInt("IX"))
//        println(solution.romanToInt("X"))
//        println(solution.romanToInt("XI"))
//        println(solution.romanToInt("XII"))
//        println(solution.romanToInt("XIII"))
//        println(solution.romanToInt("XIV"))
//        println(solution.romanToInt("XV"))
//        println(solution.romanToInt("XVI"))
//        println(solution.romanToInt("XVII"))
//        println(solution.romanToInt("XVIII"))
//        println(solution.romanToInt("XIX"))
//        println(solution.romanToInt("XX"))
//        println(solution.romanToInt("XC"))  //40
//        println(solution.romanToInt("CX"))  //60
//        println(solution.romanToInt("CDXC"))  //490
//        println(solution.romanToInt("DXC"))  //540
//        println(solution.romanToInt("DD"))  //1000
//        println(solution.romanToInt("MMXC"))  //2040

//        println(solution.longestCommonPrefix(arrayOf("")))    //""
//        println(solution.longestCommonPrefix(arrayOf("1", "2", "3")))    //""
//        println(solution.longestCommonPrefix(arrayOf("1", "1", "1")))    //"1"
//        println(solution.longestCommonPrefix(arrayOf("cir", "car")))    //"1"
//        println(solution.longestCommonPrefix(arrayOf("123_", "123@", "123#")))    //"123"
//        println(solution.longestCommonPrefix(arrayOf("vd_fgt_", "vdfgt", "vdfgt")))    //"vd"
//        println(solution.longestCommonPrefix(arrayOf("Vlad123", "Vlad_123", "Vlad#456")))    //"Vlad"
//        println(solution.longestCommonPrefix(arrayOf("flower","flow","flight")))    //"fl"

//        println(solution.isValid("()"))
//        println(solution.isValid("{}"))
//        println(solution.isValid("[]"))
//        println(solution.isValid("[{]"))
//        println(solution.isValid("[{]}"))
//        println(solution.isValid("[{}]"))
//        println(solution.isValid("[]{}()"))
//        println(solution.isPalindrome(".,"))

//        println(solution.removeDuplicates(intArrayOf()))
//        println(solution.removeDuplicates(intArrayOf(1)))
//        println(solution.removeDuplicates(intArrayOf(1,1)))
//        println(solution.removeDuplicates(intArrayOf(1,2)))
//        println(solution.removeDuplicates(intArrayOf(1,1,2)))
//        println(solution.removeDuplicates(intArrayOf(1,2,3)))
//        println(solution.removeDuplicates(intArrayOf(1,2,3,4,5)))
//        println(solution.removeDuplicates(intArrayOf(5,4,3,2,1)))
//        println(solution.removeDuplicates(intArrayOf(1,2,3,4,0)))
//        println(solution.removeDuplicates(intArrayOf(1,2,1,4,0)))
//        println(solution.removeDuplicates(intArrayOf(1,2,1,2,0)))
//
//        println(solution.removeDuplicates2(intArrayOf(0,0,1,1,1,2,2,3,3,4)))
//        println(solution.removeElement(intArrayOf(), 3))
//        println(solution.removeElement(intArrayOf(3), 3))
//        println(solution.removeElement(intArrayOf(3,3), 3))
//        println(solution.removeElement(intArrayOf(3,2,2,3), 3))
//        println(solution.removeElement(intArrayOf(3,2,4,2,3), 3))
//        println(solution.removeElement(intArrayOf(3,3,3,2,4,5,2,3), 3))
//        println(solution.removeElement(intArrayOf(3,1,3,2,1,1,1,4,5,2,1,3,1,1,1), 1))

//        println(solution.strStr("", ""))
//        println(solution.strStr("", "s"))
//        println(solution.strStr("s", "s"))
//        println(solution.strStr("s", ""))
//        println(solution.strStr("ss", "s"))
//        println(solution.strStr("_s", "s"))
//        println(solution.strStr("sadbutsad", "sad"))
//        println(solution.strStr("sqdbutsad", "sad"))
//        println(solution.searchInsert(intArrayOf(), 1))
//        println(solution.searchInsert(intArrayOf(2), 1))
//        println(solution.searchInsert(intArrayOf(3), 6))
//        println(solution.searchInsert(intArrayOf(1,2,3,4,5,6,7), 1))
//        println(solution.searchInsert(intArrayOf(1,2,3,4,5,6,7), 4))
//        println(solution.searchInsert(intArrayOf(1,2,3,4,5,6,7), 7))
//        println(solution.searchInsert(intArrayOf(1,5,6,7), 4))

//        println(solution.lengthOfLastWord(""))
//        println(solution.lengthOfLastWord(" "))
//        println(solution.lengthOfLastWord(" w"))
//        println(solution.lengthOfLastWord("q "))
//        println(solution.lengthOfLastWord("as"))
//        println(solution.lengthOfLastWord(" gb"))
//        println(solution.lengthOfLastWord("bn "))
//        println(solution.lengthOfLastWord("v za"))
//        println(solution.lengthOfLastWord("v gb bvn sdfdfghghjk"))

//        solution.plusOne(intArrayOf()).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(0)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(1)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(9)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(1, 0)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(1, 9)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(1, 0, 9)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(1, 9, 9)).forEach { print(it) }
//        println()
//        solution.plusOne(intArrayOf(9, 9, 9)).forEach { print(it) }

//         val node0 = null
//         traverseListNode(
//             Solution().deleteDuplicates(node0)  // null
//         )   // null
//
//         val node1 = Solution.ListNode(1)
//         traverseListNode(
//             Solution().deleteDuplicates(node1)  // 1
//         )   // 1
//
//         val node2 = Solution.ListNode(1)
//         node2.next = Solution.ListNode(2)
//         traverseListNode(
//             Solution().deleteDuplicates(node2)  // 1 2
//         )   // 1 2
//
//         val node2_1 = Solution.ListNode(1)
//         node2_1.next = Solution.ListNode(1)
//         traverseListNode(
//             Solution().deleteDuplicates(node2_1)    // 1 1
//         )   // 1
//
//         val node3 = Solution.ListNode(1)
//         node3.next = Solution.ListNode(2)
//         node3.next?.next = Solution.ListNode(3)
//         traverseListNode(
//             Solution().deleteDuplicates(node3)      // 1 2 3
//         )   // 1 2 3
//
//         val node4 = Solution.ListNode(1)
//         node4.next = Solution.ListNode(1)
//         node4.next?.next = Solution.ListNode(2)
//         node4.next?.next?.next = Solution.ListNode(3)
//         traverseListNode(
//             Solution().deleteDuplicates(node4)      // 1 1 2 3
//         )   // 1 2 3
//
//         val node4_1 = Solution.ListNode(1)
//         node4_1.next = Solution.ListNode(1)
//         node4_1.next?.next = Solution.ListNode(2)
//         traverseListNode(
//             Solution().deleteDuplicates(node4_1)// 1 1 2
//         ) // 1 2
//
//         val node5 = Solution.ListNode(1)
//         node5.next = Solution.ListNode(1)
//         node5.next?.next = Solution.ListNode(1)
//         node5.next?.next?.next = Solution.ListNode(1)
//         traverseListNode(
//             Solution().deleteDuplicates(node5)      // 1 1 1 1
//         ) // 1
//
//         val node6 = Solution.ListNode(1)
//         node6.next = Solution.ListNode(1)
//         node6.next?.next = Solution.ListNode(2)
//         node6.next?.next?.next = Solution.ListNode(2)
//         traverseListNode(
//             Solution().deleteDuplicates(node6)      // 1 1 2 2
//         )   // 1 2
//
//         val node7 = Solution.ListNode(2)
//         node7.next = Solution.ListNode(2)
//         node7.next?.next = Solution.ListNode(2)
//         node7.next?.next?.next = Solution.ListNode(3)
//         traverseListNode(
//             Solution().deleteDuplicates(node7)      // 2 2 2 3
//         )   // 2 3
//
//         val node8 = Solution.ListNode(2)
//         node8.next = Solution.ListNode(2)
//         node8.next?.next = Solution.ListNode(2)
//         node8.next?.next?.next = Solution.ListNode(3)
//         node8.next?.next?.next?.next = Solution.ListNode(3)
//         node8.next?.next?.next?.next?.next = Solution.ListNode(4)
//         node8.next?.next?.next?.next?.next = Solution.ListNode(4)
//         traverseListNode(
//             Solution().deleteDuplicates(node8)      // 2 2 2 3 3 4 4
//         )   // 2 3 4

        // var array = intArrayOf(-1, 0, 0, 3, 3, 3, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(1, 2, 2), 3)
        // printArray(array)
        //
        // array = intArrayOf(0, 0, 0, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(1, 2, 3, 4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 3, 4, 5, 6)
        // Solution().merge(array, 3, intArrayOf(0, 0, 0, 0, 0, 0), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 0, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 3, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 4, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 5, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 7, 0, 0, 0)
        // Solution().merge(array, 3, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 2, 5, 10, 0, 0, 0, 0)
        // Solution().merge(array, 8, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 4, 4, 5, 5, 10, 0, 0, 0, 0, 0, 0)
        // Solution().merge(array, 8, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        // Solution().merge(array, 8, intArrayOf(4, 5, 6), 3)
        // printArray(array)
        //
        // array = intArrayOf(1, 5, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0)
        // Solution().merge(array, 8, intArrayOf(4, 8, 16, 21), 4)
        // printArray(array)
        //
        // array = intArrayOf(1, 5, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0)
        // Solution().merge(array, 8, intArrayOf(4, 8, 16, 21, 25, 32), 6)
        // printArray(array)
        //
        // array = intArrayOf(1, 5, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0)
        // Solution().merge(array, 8, intArrayOf(3, 5, 5, 8, 9, 12, 15, 21), 8)
        // printArray(array)

        // val array = intArrayOf(1,2,3,0,0,0)
        // Solution().merge(array,3,intArrayOf(4,5,6), 3)
        // println(array)

//        println(solution.addBinary("1010","10101"))
//        println(solution.addBinary("0","1"))

//        println(solution.mySqrt(0))
//        println(solution.mySqrt(1))
//        println(solution.mySqrt(2))
//        println(solution.mySqrt(3))
//        println(solution.mySqrt(8))
//        println(solution.mySqrt(9))
//        println(solution.mySqrt(100))

//        println(solution.singleNumber(intArrayOf()))
//        println(solution.singleNumber(intArrayOf(0)))
//        println(solution.singleNumber(intArrayOf(0,1)))
//        println(solution.singleNumber(intArrayOf(0,0,12)))
//        println(solution.singleNumber(intArrayOf(4,1,2,1,2)))
//        println(solution.singleNumber(intArrayOf(1,2,3,1,3,7,2)))

//        println(solution.generate(0))
//        println(solution.generate(1))
//        println(solution.generate(2))
//        println(solution.generate(3))
//        println(solution.generate(4))
//        println(solution.generate(5))
//        println(solution.generate(6))
//        println(solution.generate(7))

//        println(solution.getRow(0))
//        println(solution.getRow(1))
//        println(solution.getRow(2))
//        println(solution.getRow(3))
//        println(solution.getRow(4))
//        println(solution.getRow(5))
//        println(solution.getRow(6))
//        println(solution.getRow(7))

//        var list = solution.generateListNodes()
//        while (list.next != null) {
//            println(list.`val`)
//            list = list.next!!
//        }

        // generateListNodes() check
//        val list1 = solution.generateListNodes()
//        var list11: Solution.ListNode? = list1
//        print("List1: ")
//        while (list11 != null) {
//            print("${list11.`val`} ")
//            list11 = list11.next
//        }
//        println()
//        print("List2: ")
//        val list2 = solution.generateListNodes()
//        list11 = list2
//        while (list11 != null) {
//            print("${list11.`val`} ")
//            list11 = list11.next
//        }
//        println()
//
//        print("Result List: ")
//        var list = solution.mergeTwoLists(list1, list2)
//        var resultList = list
//        while (resultList != null) {
//            print("${resultList.`val`} ")
//            resultList = resultList.next
//        }
//        println()

//        solution.hammingWeight(-1_000_000)
//        solution.hammingWeight(-1_000)
//        solution.hammingWeight(-1)
//        solution.hammingWeight(0)
//        solution.hammingWeight(1)
//        solution.hammingWeight(1000)
//        solution.hammingWeight(Int.MAX_VALUE)

//        println(solution.frequencySort("dddGhhhhMMMMMxxSSSzz"))
//        println(solution.firstUniqueChar("i"))  // 0
//        println(solution.firstUniqueChar("ii"))  // -1
//        println(solution.firstUniqueChar("iis"))  // 2
//        println(solution.firstUniqueChar("iiss"))  // -1
//        println(solution.firstUniqueChar(" iiss"))  // 0
//        println(solution.firstUniqueChar("iiss "))  // 4
//        println(solution.firstUniqueChar("qcqwwwerttyuii")) // 1
//        println(solution.firstUniqueChar("qqwwwerttyuii"))  // 5
//        println(solution.firstUniqueChar("qqewwwerttyuii"))  // 7
//        println(solution.firstUniqueChar("ewwwrttyuii"))  // 0
//        println(solution.firstUniqueChar("eewrttyuii"))  // 2

//        println(solution.isIsomorphic("egg", "add"))    // true
//        println(solution.isIsomorphic("", ""))    // true
//        println(solution.isIsomorphic("1", "1"))    // true
//        println(solution.isIsomorphic("1", "2"))    // true
//        println(solution.isIsomorphic("13", "24"))    // true
//        println(solution.isIsomorphic("131", "242"))    // true
//        println(solution.isIsomorphic("111", "222"))    // true
//        println(solution.isIsomorphic("111", "2222"))    // false

//        println(solution.containsDuplicate2(intArrayOf(1)))    // false
//        println(solution.containsDuplicate2(intArrayOf(1,2,3,4)))    // false
//        println(solution.containsDuplicate2(intArrayOf(1,1,3,4)))    // true
//        println(solution.containsDuplicate2(intArrayOf(1,2,2,4)))    // true
//        println(solution.containsDuplicate2(intArrayOf(1,2,3,3)))    // true
//        println(solution.containsDuplicate2(intArrayOf(1,2,3,1)))    // true
//        println(solution.containsDuplicate2(intArrayOf(1,2,3,4)))    // false
//        println(solution.containsDuplicate2(intArrayOf(4,2,2,4)))    // true
//        println(solution.containsDuplicate2(intArrayOf(1,2,3,4,1)))    // true

//        println(solution.majorityElement(intArrayOf(1)))
//        println(solution.majorityElement(intArrayOf(1, 1, 2)))
//        println(solution.majorityElement(intArrayOf(1, 2, 2)))
//        println(solution.majorityElement(intArrayOf(2, 2, 2)))
//        println(solution.majorityElement(intArrayOf(2, 2, 2, 3)))
//        println(solution.majorityElement(intArrayOf(2, 2, 2, 3, 4)))
//        println(solution.majorityElement(intArrayOf(4, 4, 2, 3, 4)))

//        println(solution.addDigits(1))
//        println(solution.addDigits(11))
//        println(solution.addDigits(111))
//        println(solution.addDigits(12))
//        println(solution.addDigits(123))    // 6
//        println(solution.addDigits(1234))   // 1
//        println(solution.addDigits(12345))  // 6

//        var array = intArrayOf(0, 1, 0, 1)
//        solution.moveZeroes(array)
//        println(array.joinToString())
//
//        array = intArrayOf(0, 1, 0, 1, 0)
//        solution.moveZeroes(array)
//        println(array.joinToString())
//
//        array = intArrayOf(0, 0, 1, 0, 1)
//        solution.moveZeroes(array)
//        println(array.joinToString())
//
//        array = intArrayOf(0,0,1,0,0,1,0)
//        solution.moveZeroes(array)
//        println(array.joinToString())
//
//        array = intArrayOf(0,0,0,0,0,1,1)
//        solution.moveZeroes(array)
//        println(array.joinToString())
//
//        array = intArrayOf(1,0,0,0,0,0,0)
//        solution.moveZeroes(array)
//        println(array.joinToString())
//
//        array = intArrayOf(0,0,0,0,0,0,1)
//        solution.moveZeroes(array)
//        println(array.joinToString())

//        CharRange('1','9').forEach(System.out::println)

//        println(Solution().wordPattern("abba", "dog cat cat dog"))   // true
//        println(Solution().wordPattern("abba", "dog dog dog dog"))   // false
//        println(Solution().wordPattern("abba", "dog cat cat fish"))   // false
//        println(Solution().wordPattern("aaaa", "dog cat cat fish"))   // false
//        println(Solution().wordPattern("aaaa", "dog dog dog dog"))   // true
//        println(Solution().wordPattern("aaaa", "cat dog dog dog"))   // false
//        println(Solution().wordPattern("abba", "cat dog dog dog"))   // false
//        println(Solution().wordPattern("abbb", "cat dog dog dog"))   // true
    }
}
