package com.kihyeon.kotlin.ch2

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.time.Instant

//class Person constructor(name: String) {
//    val name: String
//
//    init {
//        this.name = name
//    }
//}

//class Person(val name: String)

//open class Person(
//    name: String
//) : Serializable,
//    Comparable<Person> {
//    override fun compareTo(other: Person): Int {
//        TODO("Not yet implemented")
//    }
//}
//
//class Member(name: String) : Person(name)

//class Name
//
//class Person(val name: String, val registered: Instant = Instant.now()) {
//    constructor(name: Name) : this(name.toString())
//}
//
//fun test() {
//    val person = Person("kkh")
//    println(person.name)
//}

data class Person(val name: String, val registered: Instant = Instant.now())

fun show(persons: List<Person>) {
    for (person in persons) {
        println("${person.component1()} ${person.component2()}")
    }
}

object MyWindowAdapter : WindowAdapter() {
    override fun windowClosed(e: WindowEvent?) {
        TODO("not implemented")
    }
}

fun test2() {
    val list = listOf<String>()
}

fun add(a: Int, b: Int) = a + b

// local function
fun sumOfPrimes(limit: Int): Long {
    val seq = sequenceOf(2L) + generateSequence(3L, {
        it + 2
    }).takeWhile {
        it < limit
    }

    fun isPrime(n: Long) = seq.takeWhile {
        it * it <= n //
    }.all {
        n % it != 0L
    }

    return seq.filter(::isPrime).sum()
}

fun <T> List<T>.length() = this.size

fun List<Int>.product() = this.fold(1) { a, b -> a * b }


fun useTest() {
    File("myFile.txt").forEachLine { println(it) }
    File("myFile.txt").useLines { println(it) }
}

fun variance() {
    fun sample() {
        val s = "A String"
        val a: Any = s

        //    val ls = mutableListOf("A String")
        //    val la = MutableList<Any> = ls <<<
        //    la.add(42)

        val ls = listOf("A String")
        val la = ls + 42
    }

    fun invariant() {
        fun <T> addAll(
            list1: MutableList<T>,
            list2: MutableList<T>
        ) {
            for (elem in list2) list1.add(elem)
        }

        val ls = mutableListOf("A String")
        val la = mutableListOf<Any>()
        // addAll(la, ls) // <<< compile error. allAll 인자에 두개의 T 는 서로 다른 타입으로 간주되기 때문
    }

    fun covariance() {
        fun <T> addAll(
            dest: MutableList<T>,
            src: MutableList<out T> // 가져오기만 하고(out), 값을 넣는 일은 결코 없기 때문(in)
        ) {
            for (elem in src) dest.add(elem)
        }

        val ls = mutableListOf("A String")
        val la: MutableList<Any> = mutableListOf()
        addAll(la, ls)
    }

    // 좀 더 자세한 설명
    // - https://umbum.dev/612
    // - https://s2choco.tistory.com/21

    // 공변성은 하위타입일때. Upper Bound Wildcard. out. (produce) (extends / java)
    // 상위제한이므로, 상위에대해 데이터를 넣을 수 없다. 데이터는 가져올수만 있다. read-only == producer 읽어(read) 와서 제공(producer).

    // 반공변성은 상위타입일때. Lower Bound Wildcard. in. (consume) (super / java)
    // 하위제한이므로, 하위에대해 데이터를 가져올 수 없다. 데이터는 넣울수 있다. write-only == consumer 써서(write) 소비(consumer).
}

open class MyClassParent

class MyClass : MyClassParent()

interface Bag<T> {
    fun get(): T
    fun use(t: T): Boolean
}

class BagImpl : Bag<MyClassParent> {
    override fun get(): MyClassParent = MyClassParent()
    override fun use(t: MyClassParent): Boolean = true
}

//fun useBag(bag: Bag<MyClass>): Boolean {
//    return true
//}

// 사용 지점에 타입 제한. 타입 프로젝션
fun useBag(bag: Bag<in MyClass>): Boolean {
    return true
}

val bag3 = useBag(BagImpl()) // Bag<MyClassParent> 를 넘겨서 반공변성이 필요함

//fun createBag(): Bag<MyClassParent> = BagImpl2()
fun createBag(): Bag<out MyClassParent> = BagImpl2()

class BagImpl2 : Bag<MyClass> {
    override fun get(): MyClass = MyClass()
    override fun use(t: MyClass): Boolean = true
}

val a = createBag()

fun test3() {
    a.get()
    // a.use(MyClass()) // ??
}
