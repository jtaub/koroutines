package dev.jtkt.koroutines

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Supplier

private val defaultExecutor = Executors.newVirtualThreadPerTaskExecutor()

/** Executes the [task] on a virtual thread. */
fun <V> go(task: Supplier<V>) = go(defaultExecutor, task)

/** Executes the [task] on the given [executor]. */
fun <V> go(executor: ExecutorService, task: Supplier<V>): CompletableFuture<V> =
    CompletableFuture.supplyAsync(task, executor)

/**
 * Executes the [task] on a virtual thread.
 *
 * This function effectively adds support for the Java language to call [go] with a lambda that
 * returns void.
 */
@JvmName("go") fun goRunnable(task: Runnable) = go(defaultExecutor) { task.run() }
