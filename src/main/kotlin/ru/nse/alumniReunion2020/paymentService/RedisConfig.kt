package ru.nse.alumniReunion2020.paymentService

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.Jedis
import java.net.URI
import java.util.logging.Logger


@Configuration
@ComponentScan(basePackages = ["ru.nse.alumniReunion2020.paymentService.*"])
class RedisConfig {
    @Value("\${redis.uri}")
    lateinit var redisUriStr: String


    @Bean
    fun redisClient(): Jedis {
        LoggerFactory.getLogger(this.javaClass).info("Redis uri $redisUriStr")
        return Jedis(URI(redisUriStr))
    }

}