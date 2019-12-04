package com.example.kill

/**
 *描述：
 *<p/>作者：wu
 *<br/>创建时间：2019/12/4 11:08
 */
interface OrderService {
    fun createWrongOrder(sid: Int): Int
    /**
     * 乐观锁
     */
    fun createOptimisticOrder(sid: Int): Int

    fun createOptimisticOrderUseRedis(sid: Int): Int
    fun createOptimisticOrderUseRedisRabbit(sid: Int): Int
}