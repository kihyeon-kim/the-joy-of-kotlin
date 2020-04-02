package com.kihyeon.kotlin.ch2

import org.junit.Test

internal class Ch2 {
    @Test
    fun `2-1-3`() {
        fun getName(): String {
            println("이름 계산 중...")
            return "kkh"
        }

        fun main() {
            val name: String by lazy { getName() }
            val name2: String by lazy { name }
            println("안녕")
            println(name)
            println(name2)
            println(name)
            println(name2)
        }

        main()
    }

    // lateinit var name: String
}