package com.example.kill

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *描述：
 *<p/>作者：wu
 *<br/>创建时间：2019/12/4 16:13
 */
@Configuration
class RabbitConfig {
    @Bean
    fun dealStock() = Queue("data-kill-queue")
}