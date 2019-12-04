package com.example.kill

import com.alibaba.fastjson.JSON
import com.example.kill.mapper.StockMapper
import com.example.kill.mapper.StockOrderMapper
import com.example.kill.pojo.Stock
import com.example.kill.pojo.StockOrder
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

/**
 *描述：消息的消费者
 *<p/>作者：wu
 *<br/>创建时间：2019/12/4 16:20
 */
@Component
@RabbitListener(queues = arrayOf("data-kill-queue"))
class MQReceiver {
    val log = logger(this)
    @Autowired
    lateinit var stockMapper: StockMapper
    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate
    @Autowired
    lateinit var stockOrderMapper: StockOrderMapper

    @RabbitHandler
    fun process(msg: String) {
        log.info("接收到rabbit数据：$msg")
        val stockOrder = JSON.parseObject(msg, StockOrder::class.java)
        stockOrderMapper.insertSelective(stockOrder)
    }
}