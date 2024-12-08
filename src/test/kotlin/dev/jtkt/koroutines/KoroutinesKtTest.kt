package dev.jtkt.koroutines

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KoroutinesKtTest {

    @Test
    fun go() {
        // Given a mutable variable,
        val bool = AtomicBoolean(false)

        // When I create a goroutine to modify it,
        go {
            bool.set(true)
        }

        // Then it should have been executed almost immediately.
        Thread.sleep(1)
        assertTrue(bool.get())
    }

    @Test
    fun `should return results in expected order with single thread executor`() {
        // Given
        val results = mutableListOf<Int>()
        val executor = Executors.newSingleThreadExecutor()

        // When
        for (i in 0 until 10) {
            go(executor) {
                results += i
            }
        }
        Thread.sleep(5)

        // Then
        val expected = (0 until 10).toList()
        assertEquals(expected, results)
    }
}
