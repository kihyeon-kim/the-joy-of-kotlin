package com.kihyeon.kotlin.ch1

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Ch1 {
    @Test
    fun `치환 모델 설명`() {
        fun log(m: String) = println(m)

        fun mult(a: Int, b: Int) = a * b

        fun add(a: Int, b: Int): Int {
            log("Returning ${a + b}")
            return a + b
        }

        fun main() {
            val x = add(mult(2, 3), mult(4, 5))
            // val x = add(6, 20)
            println(x)
        }
    }

    @Test
    fun testByDonuts() {
        val creditCard = CreditCard()
        val purchase: Purchase = buyDonut(5, creditCard)
        assertEquals(Donut.price * 5, purchase.payment.amount)
        assertEquals(creditCard, purchase.payment.creditCard)
    }
}