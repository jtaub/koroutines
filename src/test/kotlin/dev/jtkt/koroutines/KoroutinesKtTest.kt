package dev.jtkt.koroutines

import java.util.concurrent.Executors
import kotlin.test.Test
import kotlin.test.assertEquals

class KoroutinesKtTest {

    @Test
    fun go() {
        // Given go goroutine which returns hello world,
        val future = go { "hello world!" }

        // When I join the future
        val actual = future.get()

        // Then I should get the expected result
        assertEquals("hello world!", actual)
    }

    @Test
    fun `should return results in expected order with single thread executor`() {
        // Given a single threaded executor,
        val executor = Executors.newSingleThreadExecutor()

        // When I launch 100 tasks,
        val futures = (0 until 100).map { i -> go(executor) { i } }
        val actual = futures.map { it.get() }

        // Then I should get the results in the expected order.
        val expected = (0 until 100).toList()
        assertEquals(expected, actual)
    }
}
