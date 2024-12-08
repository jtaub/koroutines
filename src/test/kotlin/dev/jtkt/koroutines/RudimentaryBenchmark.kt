package dev.jtkt.koroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.BeforeTest
import kotlin.test.Test

class RudimentaryBenchmark {

    val numberOfTasks = 1_000
    val maxFibonacci = 40

    private fun fibonacci(n: Int): Int =
        if (n < 2) n else fibonacci(n - 1) + fibonacci(n - 2)


    @BeforeTest
    fun warmup() = repeat(10) {
        go { fibonacci(10) }

        runBlocking {
            launch { fibonacci(10) }
        }
    }

    @Test
    fun `test go with cpu bound task`() {
        val latch = CountDownLatch(numberOfTasks)

        repeat(numberOfTasks) { i ->
            go {
                fibonacci(i % maxFibonacci)
                latch.countDown()
            }
        }

        latch.await()
    }

    @Test
    fun `test coroutine with cpu bound task`() = runBlocking {
        // Not necessary to wait for all results due to structured concurrency
        repeat(numberOfTasks) { i ->
            launch {
                val result = fibonacci(i % maxFibonacci)
            }
        }
    }

    @Test
    fun `test coroutine with cpu bound task and virtual thread dispatcher`() = runBlocking {
        val executor = Executors.newVirtualThreadPerTaskExecutor()

        executor.use {
            val dispatcher = executor.asCoroutineDispatcher()

            withContext(dispatcher) {
                repeat(numberOfTasks) { i ->
                    launch {
                        val result = fibonacci(i % maxFibonacci)
                    }
                }
            }
        }
    }
}