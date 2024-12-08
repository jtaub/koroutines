package dev.jtkt.koroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.ArrayBlockingQueue
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
        go { fibonacci(20) }

        runBlocking {
            launch { fibonacci(20) }
        }
    }

    @Test
    fun `test go with cpu bound task`() {
        // Use this queue as a way to wait for all the goroutines to complete.
        val queue = ArrayBlockingQueue<Int>(numberOfTasks)

        repeat(numberOfTasks) { i ->
            go {
                val result = fibonacci(i % maxFibonacci)
                queue.put(result)
            }
        }

        while (queue.size != numberOfTasks) {
        }
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