package dev.jtkt.koroutines

import java.util.concurrent.CompletableFuture
import kotlin.test.Test
import kotlin.test.assertEquals

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
    fun `sequence JoinAll`() {
        // Given
        val input = sequence {
            yield(CompletableFuture.completedFuture(1))
            yield(CompletableFuture.completedFuture(2))
            yield(CompletableFuture.completedFuture(3))
        }

        // When
        val actual = input.joinAll()

        // Then
        val expected = listOf(1, 2, 3)
        assertEquals(expected, actual)
    }

    @Test
    fun mapAsync() {
        // Given
        val input = listOf(1, 2, 3)

        // When
        val actual = input.mapAsync { it * it }

        // Then
        val expected = listOf(1, 4, 9)
        assertEquals(expected, actual)
    }
}
