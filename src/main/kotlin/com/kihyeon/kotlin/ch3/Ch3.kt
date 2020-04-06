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


// 3-2
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

// 3.3
// 다인자 함수
// (Int) -> ((Int) -> Int)

typealias IntBinOp = (Int) -> (Int) -> Int

val addFunc: IntBinOp = { x ->
    { y ->
        x + y
    }
}

fun addFuncTest() {
    println(addFunc(3)(5))
}

fun `exercise-3-4`() {
    val square: (Int) -> Int = { x -> x * x }
    val triple: (Int) -> Int = { x -> x * 3 }
    val squareTriple: (Int) -> Int = { x -> triple(square(x)) }

    val compose: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int =
        { x ->
            { y ->
                { z ->
                    x(y(z))
                }
            }
        }

    // (Int) -> Int. 함수의 타입.
    // T 라고 보면. (T) -> (T) -> T
}
typealias IntUnaryOp = (Int) -> Int

fun `exercise-3-4-typealias`() {
    val compose: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp =
        { x ->
            { y ->
                { z ->
                    x(y(z)) // y 다음 x !!
                }
            }
        }

    val square: IntUnaryOp = { it * it }
    val triple: IntUnaryOp = { it * 3 }

    val squareOfTriple = compose(square)(triple)

    println(squareOfTriple(2))
}

fun `exercise-3-5`() {
    fun <T, U, V> higherCompose(): ((U) -> V) -> ((T) -> U) -> ((T) -> V) =
        { f ->
            { g ->
                { x ->
                    f(g(x))
                }
            }
        }

    val square: IntUnaryOp = { it * it }
    val triple: IntUnaryOp = { it * 3 }

    val squareOfTriple = higherCompose<Int, Int, Int>()(square)(triple)
}

fun `exercise-3-6`() {
    fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> ((T) -> V) =
        { f ->
            { g ->
                { x ->
                    g(f(x))
                }
            }
        }
}

// 3.3.7
fun closure() {
    val taxRate = 0.09
    fun addTax(price: Double) = price + price * taxRate // addTax 함수는 taxRate 라는 변수에 대해 닫혀있다
    // 읽기 어렵고 유지보수 하기 어려운 프로그램이 생길 수 있다
    // 더 모듈화를 하면 읽기 쉽고 유지보수하기 쉬운 프로그램을 만들 수 있다

    fun addTax2(taxRate: Double, price: Double) = price + price * taxRate

    val addTax3 = { taxRateParam: Double, price: Double -> price + price * taxRateParam }

    val addTaxCurr: (Double) -> (Double) -> Double = { taxRateParam: Double ->
        { price: Double ->
            price + price * taxRateParam
        }
    }

    println(addTaxCurr(taxRate)(12.0))
}

// 3.3.8
class TaxComputer(private val rate: Double) {
    fun compute(price: Double) = price + price * rate

    fun test1() {
        val tc9 = TaxComputer(0.09)
        val price = tc9.compute(12.0)
    }

    val addTaxCurr: (Double) -> (Double) -> Double =
        { taxRateParam: Double ->
            { price: Double ->
                price + price * taxRateParam
            }
        }

    fun test2() {
        val tc9 = addTaxCurr(0.09)
        val price = tc9(12.0)
    }

    // 커링은 튜플을 인자로 받는 함수를 한 번에 하나씩 인자를 부분 적용할 수 있는 함수로 대치하는 과정
    // 커리한 함수를 사용하면 부분 적용이 아주 단순하다는 장점이 있다
}

fun `exercise-3-7`() {
    fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)
}

fun `exercise-3-8`() {
    fun <A, B, C> aaa(b: B, f: (A) -> (B) -> C): (A) -> C =
        { a: A ->
            f(a)(b)
        }
}

fun `exercise-3-9`() {
    fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"

    fun <A, B, C, D> funcCurr(): (A) -> (B) -> (C) -> (D) -> String =
        { a: A ->
            { b: B ->
                { c: C ->
                    { d: D ->
                        "$a, $b, $c, $d"
                    }
                }
            }
        }

    //    val a = { a: A ->  << XXX. 클래스나 인스턴스 또는 fun 함수를 정의해야한다
    //    }
}

fun `exercise-3-10`() {
    // ??
    fun <A, B, C> source(c: C): (A, B) -> C =
        { a: A, b: B ->
            c
        }

    fun <A, B, C> curr(f: (A, B) -> C): (A) -> (B) -> C =
        { a: A ->
            { b: B ->
                f(a, b)
            }
        }

    val a = curr<Int, Int, Int>(source(1))
}

fun `exercise-3-11`() {
    fun <A, B, C> swap(f: (A) -> (B) -> C): (B) -> (A) -> C =
        { b: B ->
            { a: A ->
                f(a)(b)
            }
        }
}

// 3.3.11. 올바른 타입 사용하기

data class Product(val name: String, val price: Double, val weight: Double)

data class OrderLine(val product: Product, val count: Int) {
    fun weight() = product.weight * count
    fun amount() = product.price * count
}

object Store {
    @JvmStatic
    fun main(args: Array<String>) {
        val toothPaste = Product("Tooth paste", 1.5, 0.5)
        val toothBrush = Product("Tooth brush", 3.5, 0.3)
        val orderLines = listOf(
            OrderLine(toothPaste, 2),
            OrderLine(toothBrush, 3)
        )

        val weight = orderLines.sumByDouble { it.amount() } // <<< z
        val price = orderLines.sumByDouble { it.weight() }

        println("Total price: $price")
        println("Total weight: $weight")

        // 오류는 당연하지만, 컴파일러가 경고 하지 않았다. 너무 흔히 쓰는 타입을 사용했음.
        // >> 값 타입(value type) 을 쓰자!. 값을 표현하는 타입.
    }
}

//data class Price(val value: Double)
//
//data class Weight(val value: Double)

val total = Price(1.0).value + Weight(1.0).value // << 이러면 문제. plus 를 정의.

data class Price(val value: Double) {
    operator fun plus(price: Price): Price = Price(this.value + price.value)

    operator fun times(num: Int): Price = Price(this.value * num)
}

data class Weight(val value: Double) {
    operator fun plus(weight: Weight): Weight = Weight(this.value + weight.value)

    operator fun times(num: Int): Weight = Weight(this.value * num)
}

val totalPrice: Price = Price(1.0) + Price(2.0)

// 컬렉션을 하나의 값으로 줄여 나가는 과정. fold. reduce.
// 시작하는 원소를 제공하느냐(fold), 제공하지 않느냐(reduce)
// 결과 타입이 컬렉션의 원소 타입과 동일하냐(reduce), 다르냐(fold)


class ValueType {
    data class Product(val name: String, val price: Price, val weight: Weight)

    data class OrderLine(val product: Product, val count: Int) {
        fun weight() = product.weight * count
        fun amount() = product.price * count
    }

    object Store {
        @JvmStatic
        fun main(args: Array<String>) {
            val zeroPrice = Price(0.0)
            val zeroWeight = Weight(0.0)
            //val priceAddition = { x, y -> x + y }

            val toothPaste = Product("Tooth paste", Price(1.5), Weight(0.5))
            val toothBrush = Product("Tooth brush", Price(3.5), Weight(0.3))

            @Suppress()
            val orderLines = listOf(
                OrderLine(toothPaste, 2),
                OrderLine(toothBrush, 3)
            )

            val weight: Weight = orderLines.fold(zeroWeight) { a, b -> a + b.weight() }
            val price: Price = orderLines.fold(zeroPrice) { a, b -> a + b.amount() }

            println("Total price: $price")
            println("Total weight: $weight")
        }
    }
}

class FactoryIdentity {
    data class Price private constructor(private val value: Double) {
        override fun toString(): String = value.toString()
        operator fun plus(price: Price) = Price(this.value + price.value)
        operator fun times(num: Int) = Price(this.value * num)

        companion object {
            val identity = Price(0.0)
            operator fun invoke(value: Double) =
                if (value > 0) {
                    Price(value)
                } else {
                    throw IllegalArgumentException("Price must be positive or null")
                }
        }
    }

    data class Weight private constructor(private val value: Double) {
        override fun toString(): String = value.toString()
        operator fun plus(weight: Weight) = Weight(this.value + weight.value)
        operator fun times(num: Int) = Weight(this.value * num)

        companion object {
            val identity = Weight(0.0)
            operator fun invoke(value: Double): Weight =
                if (value > 0) {
                    Weight(value)
                } else {
                    throw IllegalArgumentException("Weight must be positive or null")
                }
        }
    }

    data class Product(val name: String, val price: Price, val weight: Weight)

    data class OrderLine(val product: Product, val count: Int) {
        fun weight() = product.weight * count
        fun amount() = product.price * count
    }

    object Store {
        @JvmStatic
        fun main(args: Array<String>) {
            //val priceAddition = { x, y -> x + y }

            val toothPaste = Product("Tooth paste", Price(1.5), Weight(0.5))
            val toothBrush = Product("Tooth brush", Price(3.5), Weight(0.3))

            @Suppress()
            val orderLines = listOf(
                OrderLine(toothPaste, 2),
                OrderLine(toothBrush, 3)
            )

            val weight: Weight = orderLines.fold(Weight.identity) { a, b -> a + b.weight() }
            val price: Price = orderLines.fold(Price.identity) { a, b -> a + b.amount() }

            println("Total price: $price")
            println("Total weight: $weight")
        }
    }
}
