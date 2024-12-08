package dev.jtkt.koroutines

import java.util.concurrent.Executor
import java.util.concurrent.Executors

private val defaultExecutor = Executors.newVirtualThreadPerTaskExecutor()

fun go(task: Runnable) =
    go(defaultExecutor, task)

fun go(executor: Executor, task: Runnable) =
    executor.execute(task)
