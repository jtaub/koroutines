# koroutines

A dead-simple implementation of Go's goroutines for Kotlin and Java.

Even with recent improvements, Java's concurrency API remains pretty cludgy to use.
Likewise, although Kotlin's coroutines are very powerful, there is a very steep learning curve, and you are forced
to [color your functions](https://journal.stuffwithstuff.com/2015/02/01/what-color-is-your-function/).

Both `java.util.concurrent` and `kotlinx-coroutines` clash with what my mindset usually is, represented by this haiku:
> Here is my function,
>
> Run it concurrently now,
>
> I do not care how.

## Examples

### Mimic the [Goroutines documentation](https://gobyexample.com/goroutines)

With this library, we can mimic the example from the official Go documentation:

```kotlin
import dev.jtkt.koroutines.go

fun f(from: String) {
    for (i in (0 until 3)) {
        println("$from : $i")
    }
}

fun main() {
    f("direct")

    go { f("koroutine") }

    go { println("going") }

    Thread.sleep(100)
    println("done")
}
```

```text
direct : 0
direct : 1
direct : 2
koroutine : 0
going
koroutine : 1
koroutine : 2
done
```

This can also be used from Java, where usage is pretty much the same:

```java
import static dev.jtkt.koroutines.KoroutinesKt.go;

class Example {
    static void f(String from) {
        for (int i = 0; i < 3; i++) {
            System.out.println(from + " : " + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        f("direct");

        go(() -> f("koroutine"));

        go(() -> System.out.println("going"));

        Thread.sleep(100);
        System.out.println("done");
    }
}
```

### Advanced Usage (sort of)

The most advanced thing you can do with this library is provide a custom `Executor` if for some reason you don't want
to use the
default [virtual thread per task executor](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/Executors.html#newVirtualThreadPerTaskExecutor()).

For example, maybe you want to run tasks one at a time in a predictable order:

```kotlin
val taskQueue = Executors.newSingleThreadExecutor()

for (i in (0..10)) {
    go(taskQueue) { print(i) }
}
```

## How does it work?

Every lambda function is immediately invoked on a
[virtual thread](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html).

That's it.

Unlike a real goroutine, the `go` from this library will return a `java.util.concurrent.CompletableFuture`.
This provides basic features like cancellation, and makes it easier to integrate with any existing code you may have
built around futures.

### Goals

- Simplicity!
- Ease of use.
- Java support.
- Kotlin support on JVM and Android.

### Non-goals

- Structured concurrency.
- Kotlin multiplatform support.

### What about Go's Channels?

Use one of the `BlockingQueue` implementations in the Java standard library.

### How is the performance?

You would expect performance to be roughly the same as Kotlin's coroutines with a virtual thread dispatcher.
My [extremely rudimentary benchmark](src/test/kotlin/dev/jtkt/koroutines/RudimentaryBenchmark.kt) shows this is the
case, at least for a CPU-bound task.

## Disclaimer

This project is not in any way affiliated with the official Go or kotlinx-coroutines projects.

This project was inspired by [Valbaca's "Gotlin" blog post](https://valbaca.com/code/2023/04/26/gotlin.html), which I am
also not affiliated with.
