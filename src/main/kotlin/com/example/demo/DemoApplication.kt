package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {

    System.setProperty("reactor.netty.ioWorkerCount", "2");

    runApplication<DemoApplication>(*args)
}
