package com.example.kill

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.example.kill.mapper")
class KillApplication

fun main(args: Array<String>) {
    runApplication<KillApplication>(*args)
}
