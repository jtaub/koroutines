package dev.jtkt.koroutines

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

private val defaultExecutor = Executors.newVirtualThreadPerTaskExecutor()

/** Executes the [task] on a virtual thread. */
fun <V> go(task: Callable<V>) = go(defaultExecutor, task)

/** Executes the [task] on the given [executor]. */
fun <V> go(executor: ExecutorService, task: Callable<V>): Future<V> = executor.submit(task)

/**
 * Executes the [task] on a virtual thread.
 *
 * This function effectively adds support for the Java language to call [go] with a lambda that
 * returns void.
 */
@JvmName("go") fun goRunnable(task: Runnable): Future<Unit> = go(defaultExecutor) { task.run() }
