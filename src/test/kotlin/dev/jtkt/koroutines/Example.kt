package dev.jtkt.koroutines

fun f(from: String) {
    for (i in (0 until 3)) {
        println("$from : $i")
        Thread.sleep(1)
    }
}

fun main() {
    f("direct")

    go { f("koroutine") }

    go {
        fun func(msg: String) = println(msg)

        func("going")
    }

    Thread.sleep(100)
    println("done")
}
