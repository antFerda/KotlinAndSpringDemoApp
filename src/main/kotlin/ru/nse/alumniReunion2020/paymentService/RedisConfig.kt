package ru.nse.alumniReunion2020.paymentService

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.net.URI


@Configuration
@ComponentScan(basePackages = ["ru.nse.alumniReunion2020.paymentService.*"])
class RedisConfig {
    @Value("\${redis.uri}")
    lateinit var redisUriStr: String


    @Bean
    fun redisClient(): JedisPool {
        val poolConfig = JedisPoolConfig()
        poolConfig.maxTotal = 10
        poolConfig.maxIdle = 5
        poolConfig.minIdle = 1
        poolConfig.testOnBorrow = true
        poolConfig.testOnReturn = true
        poolConfig.testWhileIdle = true

        val redisURI = URI(System.getenv("REDIS_TLS_URL"))

        return JedisPool(poolConfig, redisURI)
    }

}