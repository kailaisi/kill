package com.example.kill

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KillApplication

fun main(args: Array<String>) {
    runApplication<KillApplication>(*args)
}
