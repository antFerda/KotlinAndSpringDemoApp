package ru.nse.alumniReunion2020.paymentService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import redis.clients.jedis.JedisPool


@Service
class PaymentService(
        var client: KassaClient,
        var jedisPool: JedisPool
) {
    @Value("\${authorizationHeader}")
    lateinit var authorizationHeader: String

    var logger: Logger = LoggerFactory.getLogger(this::class.java)


    fun makePayment(form: Form): PaymentResponse {
        val paymentObject = PaymentObject(
                amount = Amount(value = form.amount.toString(), "RUB"),
                capture = true,
                confirmation = Confirmation(type = "embedded"),
                description = "Donation from " + form.email
        )

        val paymentResponse: PaymentResponse = client.sendPayment(paymentObject = paymentObject, authorization = authorizationHeader)
        val redisClient = jedisPool.resource
        redisClient.use {
            redisClient.setex(paymentResponse.id, 600 , paymentResponse.amount.value)
        }

        return paymentResponse
    }

    fun getAllKeys(): String {
        val redisClient = jedisPool.resource
        redisClient.use {
            return redisClient.keys("*").joinToString(separator = ",\n")
        }
    }

    fun getStatus(paymentId: String): PaymentStatus {
        val paymentStatus = client.getPaymentStatus(authorization = authorizationHeader, paymentId = paymentId)
        jedisPool.resource.use {
            if(it.exists(paymentStatus.id)) {

                if (paymentStatus.paid && (paymentStatus.status == "waiting_for_capture" || paymentStatus.status == "succeeded")) {
                    it.incrBy("donations_amount", paymentStatus.amount.value.substringBefore(".").toLong())
                    it.incrBy("donations_number", 1L)
                    it.del(paymentStatus.id)
                }
            }
        }

        return paymentStatus
    }

    fun getCounterStatus(): CounterStatus {
        jedisPool.resource.use {
            return CounterStatus(it.get("donations_amount").toLong(), it.get("donations_number").toLong(), )
        }
    }


}