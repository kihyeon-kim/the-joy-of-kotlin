package com.kihyeon.kotlin.ch1

//fun buyDonut(creditCard: CreditCard): Donut {
//    val donut = Donut()
//    creditCard.charge(Donut.price) // 부수 효과로 신용 카드를 청구한다
//    return donut
//}

// 카드 지급에 대한 표현 넣기
//fun buyDonut(creditCard: CreditCard): Purchase {
//    val donut = Donut()
//    val payment = Payment(creditCard, Donut.price)
//    return Purchase(donut, payment)
//}

// 여러 도넛을 한꺼번에 사기
fun buyDonut(quantity: Int = 1, creditCard: CreditCard): Purchase =
    Purchase(
        List(quantity) {
            Donut()
        }, Payment(creditCard, Donut.price * quantity)
    )

class CreditCard {
    fun charge(price: Int) {
        TODO("Not yet implemented")
    }
}

class Donut {
    companion object {
        const val price = 10
    }
}

class Payment(val creditCard: CreditCard, val amount: Int) {
    // 여러 지급을 하나로 묶기
    fun combine(payment: Payment): Payment =
        if (creditCard == payment.creditCard) {
            Payment(creditCard, amount + payment.amount)
        } else {
            throw IllegalStateException("Cards don't match.")
        }

    // 1-5. 신용카드에 따라 지급 그룹화하기
    companion object {
        fun groupByCard(payments: List<Payment>): List<Payment> =
            payments.groupBy { it.creditCard }
                .values
                .map { it.reduce(Payment::combine) }
    }
}

//class Purchase(val donut: Donut, val payment: Payment)
// 여러 도넛 한꺼번에 사기
class Purchase(val donuts: List<Donut>, val payment: Payment)

