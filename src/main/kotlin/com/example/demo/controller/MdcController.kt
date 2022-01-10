package com.example.demo.controller

import kotlinx.coroutines.delay
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class MdcController {

    @GetMapping("work/{delay}")
    suspend fun work(@PathVariable delay: Int): Map<String, String> {
        MDC.put("threadName", Thread.currentThread().name)

        return withContext(MDCContext()) {
            delay((delay * 1000).toLong())

            MDC.put("threadName-in-context", Thread.currentThread().name)
            MDC.getCopyOfContextMap()
        }
    }

    @GetMapping("notwork/{delay}")
    suspend fun notwork(@PathVariable delay: Int): Map<String, String> {
        MDC.put("threadName", Thread.currentThread().name)

        delay((delay * 1000).toLong())

        return withContext(MDCContext()) {
            MDC.put("threadName-in-context", Thread.currentThread().name)
            MDC.getCopyOfContextMap()
        }
    }
}