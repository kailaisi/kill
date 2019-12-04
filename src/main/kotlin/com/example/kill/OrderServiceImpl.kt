package com.example.kill

import com.example.kill.mapper.StockMapper
import com.example.kill.mapper.StockOrderMapper
import com.example.kill.pojo.Stock
import com.example.kill.pojo.StockOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderServiceImpl : OrderService {
    @Autowired
    lateinit var stockMapper: StockMapper
    @Autowired
    lateinit var stockOrderMapper: StockOrderMapper
    @Autowired
    private lateinit var stringRedisTemplate: RedisTemplate<String, String>
    override fun createWrongOrder(sid: Int): Int {
        var stock = stockMapper.selectByPrimaryKey(sid)
        if (stock.sale == stock.count) {
            throw RuntimeException("库存不足")
        }
        stock.sale = stock.sale + 1
        stockMapper.updateByPrimaryKeySelective(stock)
        val stockOrder = StockOrder().apply {
            this.sid = stock.id
            name = stock.name
        }
        return stockOrderMapper.insertSelective(stockOrder)
    }

    override fun createOptimisticOrder(sid: Int): Int {
        var stock = stockMapper.selectByPrimaryKey(sid)
        if (stock.sale == stock.count) {
            throw RuntimeException("库存不足")
        }
        stock.sale = stock.sale + 1
        stockMapper.updateByOptimistic(stock)
        val stockOrder = StockOrder().apply {
            this.sid = stock.id
            name = stock.name
        }
        return stockOrderMapper.insertSelective(stockOrder)
    }

    override fun createOptimisticOrderUseRedis(sid: Int): Int {
        val count = stringRedisTemplate.opsForValue().get("${RedisKeysConstant.STOCK_COUNT}$sid")!!.toInt()
        val sale = stringRedisTemplate.opsForValue().get("${RedisKeysConstant.STOCK_SALE}$sid")!!.toInt()
        if(count<=sale){
            throw java.lang.RuntimeException("扣库存不足")
        }
        val version = stringRedisTemplate.opsForValue().get("${RedisKeysConstant.STOCK_VERSION}$sid")!!.toInt()
        val stock = Stock().apply {
            id = sid
            this.count = count
            this.sale = sale
            this.version = version
        }
        var c = stockMapper.updateByOptimistic(stock)
        if(c==0){
            throw java.lang.RuntimeException("并发更新失败")
        }    //自增
        stringRedisTemplate.opsForValue().increment(RedisKeysConstant.STOCK_SALE + stock.id,1)
        stringRedisTemplate.opsForValue().increment(RedisKeysConstant.STOCK_VERSION + stock.id,1)
        val stockOrder = StockOrder().apply {
            this.sid = stock.id
            name = stock.name
        }
        return stockOrderMapper.insertSelective(stockOrder)
    }

}