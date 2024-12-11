package dev.jtkt.koroutines

import java.util.concurrent.CompletableFuture
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.measureTime

class KoroutineUtilsKtTest {
    @Test
    fun `iterable joinAll`() {
        // Given
        val input =
            listOf(
                CompletableFuture.completedFuture(1),
                CompletableFuture.completedFuture(2),
                CompletableFuture.completedFuture(3),
            )

        // When
        val actual = input.joinAll()

        // Then
        val expected = listOf(1, 2, 3)
        assertEquals(expected, actual)
    }

    @Test
    fun `mapAsync should give expected results`() {
        // Given
        val input = listOf(1, 2, 3)

        // When
        val actual = input.mapAsync { it * it }

        // Then
        val expected = listOf(1, 4, 9)
        assertEquals(expected, actual)
    }

    @Test
    fun `mapAsync should run all transform concurrently`() {
        // Given a list with 100 elements
        val input = (0..100).toList()

        // When we make each task sleep for 100 milliseconds,
        val actual = measureTime { input.mapAsync { Thread.sleep(100) } }

        // Then the total time taken should not be much more than 100 milliseconds.
        assertTrue(actual.inWholeMilliseconds < 125)
    }
}
