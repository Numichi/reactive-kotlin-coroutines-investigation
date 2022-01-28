package com.example.demo.controller

import com.example.demo.WebExchangeFilter
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient


@RestController
class MdcController {

    private val logger = LoggerFactory.getLogger(MdcController::class.java)

    @GetMapping("work/{delay}")
    suspend fun work(@PathVariable delay: Int) {
        logger.info("work - ${Thread.currentThread().name}")

        withContext(MDCContext()) {
            delay((delay * 1000).toLong())
            logger.info("test - ${Thread.currentThread().name}")
        }
    }

    @GetMapping("notwork/{delay}")
    suspend fun notwork(@PathVariable delay: Int) {
        logger.info("before - ${Thread.currentThread().name}")

//        val async = async {
//            logger.info("async - ${Thread.currentThread().name}")
//        }

        // A NodeJS from Java base investigate
        WebClient.builder().filter(WebExchangeFilter()).build()
            .get().uri("http://localhost:3000/?delay=$delay")
            .retrieve()
            .toBodilessEntity()
            .awaitSingleOrNull()

        WebClient.builder().filter(WebExchangeFilter()).build()
            .get().uri("http://localhost:3000/?delay=$delay")
            .retrieve()
            .toBodilessEntity()
            .awaitSingleOrNull()

//        delay((delay * 1000).toLong())

//        async.await()

        logger.info("after - ${Thread.currentThread().name}")
    }
}