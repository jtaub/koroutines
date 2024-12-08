package dev.ktkt.koroutines;

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