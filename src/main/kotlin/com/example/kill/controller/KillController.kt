package com.example.kill.controller

import com.example.kill.OrderService
import com.example.kill.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *描述：
 *<p/>作者：wu
 *<br/>创建时间：2019/12/4 11:05
 */
@RestController
@RequestMapping("/kill")
class KillController {
    val log = logger(this)
    @Autowired
    lateinit var orderService: OrderService

    @RequestMapping("/createWrongOrder/{sid}")
    @GetMapping
    fun createWrongOrder(@PathVariable sid: Int): String {
        log.info("sid=[{}]", sid)
        var id = 0
        try {
            id = orderService.createWrongOrder(sid)
        } catch (e: Exception) {
            log.error("Exception", e)
        }
        return id.toString()
    }

    @RequestMapping("/createOptimisticOrder/{sid}")
    @GetMapping
    fun createOptimisticOrder(@PathVariable sid: Int): String {
        log.info("sid=[{}]", sid)
        var id = 0
        try {
            id = orderService.createOptimisticOrder(sid)
        } catch (e: Exception) {
            log.error("Exception", e)
        }
        return id.toString()
    }

    /**
     * 乐观锁更新库存 限流
     * @param sid
     * @return
     */
    @RequestMapping("/createOptimisticLimitOrder/{sid}")
    @GetMapping
    fun createOptimisticLimitOrder(@PathVariable sid: Int): String {
        log.info("sid=[{}]", sid)
        var id = 0
        try {
            id = orderService.createOptimisticOrder(sid)
        } catch (e: Exception) {
            log.error("Exception", e)
        }
        return id.toString()
    }

    /**
     * redis更新库存 限流
     * @param sid
     * @return
     */
    @RequestMapping("/createOptimisticOrderUseRedis/{sid}")
    @GetMapping
    fun createOptimisticOrderUseRedis(@PathVariable sid: Int): String {
        log.info("sid=[{}]", sid)
        var id = 0
        try {
            id = orderService.createOptimisticOrderUseRedis(sid)
        } catch (e: Exception) {
            log.error("Exception", e)
        }
        return id.toString()
    }

}