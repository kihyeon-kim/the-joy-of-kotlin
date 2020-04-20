package com.kihyeon.kotlin.ch4

// 4.1.1
fun append(s: String, c: Char): String = "$s$c"

//fun toString(list: List<Char>, s: String): String =
//    if (list.isEmpty())
//        s
//    else
//        toString(list.subList(1, list.size), append(s, list[0]))

object Ch4_1_1 {
    @JvmStatic
    fun main(args: Array<String>) {
        println(toString(listOf('1', '2', '3'), "STRING"))
    }
}

fun toString(list: List<Char>, s: String = ""): String =
    if (list.isEmpty())
        s
    else
        toString(list.drop(1), append(s, list.first()))

// 4.1.2
fun prepend(c: Char, s: String): String = "$c$s"

fun toString2(list: List<Char>): String =
    if (list.isEmpty())
        ""
    else
        prepend(list[0], toString2(list.subList(1, list.size))) // 문제는 중간 단계를 저장한다는 점

// 4.2.1. 꼬리 호출 제거 사용하기
class Ch4_2_1 {
    fun toStringTail(list: List<Char>): String {
        tailrec fun toStringTail(list: List<Char>, s: String): String {
            return if (list.isEmpty())
                s
            else
                toStringTail(list.subList(1, list.size), append(s, list[0]))
        }
        return toStringTail(list, "")
    }
}

// 4.2.2. 루프를 공재귀로 변환하기
fun sum(n: Int): Int = if (n < 1) 0 else n + sum(n - 1)

fun sumTail(n: Int): Int {
    fun sumTail(s: Int, i: Int): Int = if (i > n) s else sumTail(s + i, i + 1)

    return sumTail(0, 0)
    //    return sumTail(n, 0)
}

//fun main() {
//    println(sum(10))
//    println(sumTail(10))
//}

class Exercise_4_1 {
    fun inc(n: Int) = n + 1
    fun dec(n: Int) = n - 1

    // a를 b번 만큼 +1
}

// 4.2.3. 재귀 함수 값 사용하기

//fun factorial(n: Int): Int = if (n == 0) 1 else n * factorial(n - 1)

object Exercise_4_2 {
    // to BigInteger
    val factorial: (Int) -> Int by lazy {
        { n: Int ->
            if (n == 0) {
                1
            } else {
                n * factorial(n - 1)
            }
        }
    }

    //    val factorial2: (Int) -> Int =
    //        { n: Int ->
    //            if (n == 0) {
    //                1
    //            } else {
    //                n * factorial2(n - 1)
    //            }
    //        }
    //    }

    //    object Factorial {
    //        lateinit var factorial: (Int) -> Int
    //
    //        init {
    //            factorial = { n -> if (n <= 1) 1 else n * factorial(n - 1) }
    //        }
    //    }

    object Factorial {
        private lateinit var fact: (Int) -> Int

        init {
            fact = { n -> if (n <= 1) 1 else n * fact(n - 1) }
        }

        val factorial = fact
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(factorial(100))
        //com.kihyeon.kotlin.ch4.factorial(40000) // SO
    }
}

// 4.3. 재귀 함수와 리스트
//fun <T> head(list: List<T>): T =
//    if (list.isEmpty()) {
//        throw IllegalArgumentException()
//    } else {
//        list[0]
//    }
//
//fun <T> tail(list: List<T>): List<T> =
//    if (list.isEmpty()) {
//        throw IllegalArgumentException()
//    } else {
//        list.drop(1)
//    }
//
//fun sum(list: List<Int>): Int =
//    if (list.isEmpty()) {
//        0
//    } else {
//        head(list) + sum(tail(list))
//    }

fun <T> List<T>.head(): T =
    if (this.isEmpty()) {
        throw IllegalArgumentException()
    } else {
        this[0]
    }

fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty()) {
        throw IllegalArgumentException()
    } else {
        this.drop(1)
    }

//fun sum(list: List<Int>): Int =
//    if (list.isEmpty()) {
//        0
//    } else {
//        list.head() + sum(list.tail())
//    }

fun sum(list: List<Int>): Int {
    tailrec fun sumTail(list: List<Int>, acc: Int): Int {
        return if (list.isEmpty()) {
            0
        } else {
            sumTail(list.tail(), acc + list.head())
        }
    }

    return sumTail(list, 0)
}


// 4.3.1. 이중 재귀 함수 사용하기
fun fibonacci(n: Int): Int {
    return if (n == 0 || n == 1) {
        1
    } else {
        fibonacci(n - 1) + fibonacci(n - 2)
    }
}

class Exercise_4_3 // 책으로 확인!


// 4.3.2. 리스트에 대한 재귀 추상하기
//fun sum(list: List<Int>): Int =
//    if (list.isEmpty()) {
//        0
//    } else {
//        list.head() + sum(list.tail())
//    }

fun <T> makeString(list: List<T>, delim: String): String =
    when {
        list.isEmpty() -> ""
        list.tail().isEmpty() -> "${list.head()}${makeString(list.tail(), delim)}"
        else -> "${list.head()}$delim${makeString(list.tail(), delim)}"
    }

class Exercise_4_4 {
    // makeString 을 꼬리재귀로 만들기
    // 내가한것
    //    fun <T> makeString(list: List<T>, delim: String): String {
    //        tailrec fun <T> makeString(list: List<T>, acc: String): String =
    //            when {
    //                list.isEmpty() -> ""
    //                list.tail().isEmpty() -> makeString(list.tail(), "")
    //                else -> makeString(list.subList(0, list.size - 1), "$delim${list.last()}$acc")
    //            }
    //
    //        return makeString(list, "")
    //    }
    fun <T> makeString(list: List<T>, delim: String): String {
        tailrec fun <T> makeString(list: List<T>, acc: String): String =
            when {
                list.isEmpty() -> acc
                acc.isEmpty() -> makeString(list.tail(), "${list.head()}")
                else -> makeString(list.tail(), "$acc$delim${list.head()}")
            }

        return makeString(list, "")
    }
}

// foldLeft
/*
* - 원소 타입이 정해진 리스트에 대해 작용하는 함수가 있다. 이 함수는 다른 타입의 값을 하나 반환한다.
*   이 두 타입을 각각 T와 U라는 타입으로 추상화 할 수 있다.
* - 원소 타입 T와 결과 타입 U 사이에 작용하는 연산이 있다. 이 연산은 U 타입을 돌려줘야 한다.
*   이 연산은 (U, T) 쌍에서 U로 가는 함수라는 점을 알 수 있다.
*/
fun <T, U> foldLeft(list: List<T>, initial: U, f: (U, T) -> U): U {
    fun foldLeft(list: List<T>, acc: U): U =
        when {
            list.isEmpty() -> acc
            else -> foldLeft(list.tail(), f(acc, list.head()))
        }

    return foldLeft(list, initial)
}

fun main() {
    println(foldLeft(listOf("1", "2", "3", "4", "5"), "", String::plus))
    val a = listOf<Int>()
    a.fold(0, Int::plus)
}

// foldRight

// 4.3.3. 리스트 뒤집기

// exercise 4-7
fun <T> reverse(list: List<T>): List<T> {
    val prepend: (List<T>, T) -> List<T> =
        { aList: List<T>, t: T ->
            listOf(t) + aList
        }
    return foldLeft(list, listOf(), prepend)
}

// exercise 4-8
fun <T> reverse2(list: List<T>): List<T> {
    fun prepend(list: List<T>, elem: T): List<T> =
        foldLeft(list, listOf(elem)) { lst, elm -> lst + elm }

    return foldLeft(list, listOf(), ::prepend)
}

// 4.3.4. 공재귀 만들기
// exercise 4-9
fun range(start: Int, end: Int): List<Int> {
    val result = mutableListOf<Int>()

    var index = start

    while (index < end) {
        result.add(index)
        index++
    }

    return result
}

// exercise 4-10
fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    var elem = seed
    while (p(elem)) {
        result.add(elem)
        elem = f(elem)
    }

    return result
}

// exercise 4-11
fun range2(start: Int, end: Int): List<Int> {
    return unfold(start, { it + 1 }, { it < end })
}

// exercise 4-12
//fun rangeRec(start: Int, end: Int): List<Int> {
//  책으로!
//}

// exercise 4-13
fun <T> unfoldRec(elem: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    // 편의로
    fun prepend(list: List<T>, elem: T): List<T> =
        foldLeft(list, listOf(elem)) { lst, elm -> lst + elm }

    return if (p(elem)) {
        prepend(unfoldRec(f(elem), f, p), elem)
    } else {
        listOf()
    }
}

