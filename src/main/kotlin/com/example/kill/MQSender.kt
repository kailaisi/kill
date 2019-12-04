package com.example.kill

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *描述：
 *<p/>作者：wu
 *<br/>创建时间：2019/12/4 16:18
 */
@Component
class MQSender {
    val log= logger(this)
    @Autowired
    lateinit var amqpTemplate: AmqpTemplate
    fun send(queue: String, msg: String) {
        log.info("发送rabbit数据：$msg")
        amqpTemplate.convertAndSend(queue, msg)
    }
}