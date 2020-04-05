package com.kihyeon.kotlin.ch3

class CreditCard {
    fun charge(price: Int) {
        TODO("Not yet implemented")
    }
}

//class Payment(val creditCard: CreditCard, val amount: Int) {
//    // 여러 지급을 하나로 묶기
//    fun combine(payment: Payment): Payment =
//        if (creditCard == payment.creditCard) { // << 내부 creditCard 를 쓰고 있으므로 밖으로 빼낼수 없다
//            Payment(creditCard, amount + payment.amount)
//        } else {
//            throw IllegalStateException("Cards don't match.")
//        }
//
//    // 1-5. 신용카드에 따라 지급 그룹화하기
//    companion object {
//        fun groupByCard(payments: List<Payment>): List<Payment> =
//            payments.groupBy { it.creditCard }
//                .values
//                .map { it.reduce(Payment::combine) }
//    }
//}

//class Payment(val creditCard: CreditCard, val amount: Int) {
//    // 파라미터를 명시적으로
//    fun combine(payment1: Payment, payment2: Payment): Payment =
//        if (payment1.creditCard == payment2.creditCard) {
//            Payment(payment1.creditCard, payment1.amount + payment2.amount)
//        } else {
//            throw IllegalStateException("Cards don't match.")
//        }
//
//    // 1-5. 신용카드에 따라 지급 그룹화하기
//    companion object {
//        fun groupByCard(payments: List<Payment>): List<Payment> =
//            payments.groupBy { it.creditCard }
//                .values
//                .map { it.reduce(Payment::combine) }
//    }
//}

fun double(x: Int): Int = x * 2

// doubleFunc 는 이름이 아니다. 타입을 참조하고 있고 나중에 쓰이는곳에 같은 타입의 참조에 대입하게된다.
val doubleFunc: (Int) -> Int = { x -> x * 2 }

val doubleThenIncrement: (Int) -> Int = { x ->
    val double = x * 2
    double + 1
}

val add: (Int, Int) -> (Int) = { a, b -> a + b }

val doubleOne: (Int) -> (Int) = { it * 2 }

val multiplyBy2: (Int) -> Int = { n -> double(n) }

val multiplyBy2_it: (Int) -> Int = { n -> double(n) }

val multiplyBy2_refer: (Int) -> Int = ::double


class MyClass {
    fun double(n: Int): Int = n * 2
}

val foo = MyClass()
val multiplyBy2_class: (Int) -> (Int) = foo::double

//val multiplyBy2_class_name: (MyClass, Int) -> (Int) = MyClass::double
val multiplyBy2_class_name: (MyClass, Int) -> (Int) = { obj: MyClass, n: Int -> (obj::double)(n) }

class MyClass2 {
    companion object {
        fun double(n: Int): Int = n * 2
    }
}

val multiplyBy2_companion: (Int) -> Int = (MyClass2)::double
val multiplyBy2_companion_class: (Int) -> Int = MyClass2.Companion::double

//fun compose(a: (Int) -> Int, b: (Int) -> (Int)): (Int) -> (Int) = { x -> a(b(x)) }
fun compose(a: (Int) -> Int, b: (Int) -> (Int)): (Int) -> (Int) = { a(b(it)) }

fun square(n: Int) = n * n
fun triple(n: Int) = n * 3

internal class Test {
    fun test() {
        println(square(triple(2)))

        println(compose(::square, ::triple)(2))
        println(compose(::triple, ::square)(2))
    }
}

fun <T, U, V> composeType(f: (U) -> V, g: (T) -> (U)): (T) -> (V) = { f(g(it)) }