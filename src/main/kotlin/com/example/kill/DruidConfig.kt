package com.example.kill

import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *描述：
 *<p/>作者：wu
 *<br/>创建时间：2019/12/3 10:48
 */
@Configuration
class DruidConfig {
    /**
     * 配置一个web监控的filter：filterRegistrationBean
     */
    @Bean
    fun webStateFilter(): FilterRegistrationBean<WebStatFilter> {
        val filterRegistrationBean = FilterRegistrationBean(WebStatFilter())
        var param = mapOf("exclusions" to "*.js,*.css,/druid/*")
        filterRegistrationBean.initParameters = param
        filterRegistrationBean.urlPatterns = arrayListOf("/*")
        return filterRegistrationBean
    }

    @Bean
    fun setStatViewServlet(): ServletRegistrationBean<StatViewServlet> {
        val beanServlet = ServletRegistrationBean(StatViewServlet(), "/druid/*")
        val initParams = hashMapOf<String, String>()
        initParams["loginUsername"] = "admin"
        initParams["loginPassword"] = "admin"
        initParams["allow"] = ""
        initParams["resetEnable"]="true"
        /**默认就是允许所有访问*/
        beanServlet.initParameters = initParams
        return beanServlet
    }
}