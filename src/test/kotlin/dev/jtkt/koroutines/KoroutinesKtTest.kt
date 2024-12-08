package dev.jtkt.koroutines

import kotlin.test.Test
import kotlin.test.assertTrue
import java.util.concurrent.atomic.AtomicBoolean

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
    fun testGo() {
    }
}