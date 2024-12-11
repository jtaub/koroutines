package dev.jtkt.koroutines

import java.util.concurrent.Future

/**
 * Applies the given [transform] function to each element of the [Iterable] asynchronously and
 * collects the results into a list.
 *
 * @param T the type of elements in the iterable.
 * @param R the type of the result elements after applying [transform].
 * @param transform the function to transform each element.
 * @return a list containing the results of the transformation.
 */
fun <T, R> Iterable<T>.mapAsync(transform: (T) -> R) = map { go { transform(it) } }.joinAll()

/**
 * Waits for all the [Future] instances in the iterable to complete and collects their results in a
 * list.
 *
 * @param T the type of result the futures resolve to.
 * @return a list containing the resolved values of all futures.
 */
fun <T> Collection<Future<T>>.joinAll() = map { it.get() }
