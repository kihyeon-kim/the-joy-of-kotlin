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

fun main() {
    println(sum(10))
    println(sumTail(10))
}

class `exercise-4-1` {
    fun inc(n: Int) = n + 1
    fun dec(n: Int) = n - 1

    // a를 b번 만큼 +1
}

