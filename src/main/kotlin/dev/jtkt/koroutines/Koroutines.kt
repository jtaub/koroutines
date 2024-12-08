package dev.jtkt.koroutines

import java.util.concurrent.Executor
import java.util.concurrent.Executors

private val defaultExecutor = Executors.newVirtualThreadPerTaskExecutor()

/**
 * Executes the [task] on a virtual thread.
 */
fun go(task: Runnable) =
    go(defaultExecutor, task)

/**
 * Executes the [task] on the given [executor].
 */
fun go(executor: Executor, task: Runnable) =
    executor.execute(task)
