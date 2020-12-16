package ru.nse.alumniReunion2020.paymentService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis


@Service
class PaymentService(
        var client: KassaClient,
        var redisClient: Jedis
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

        logger.debug("Header $authorizationHeader")

        val paymentResponse: PaymentResponse = client.sendPayment(paymentObject = paymentObject, authorization = authorizationHeader)
        redisClient.set(paymentResponse.id, paymentResponse.amount.value)
        return paymentResponse
    }

    fun getAllKeys(): String {
        return redisClient.keys("*").joinToString(separator = ",\n")
    }

    fun getStatus(paymentId: String): PaymentStatus {
        val paymentStatus = client.getPaymentStatus(authorization = authorizationHeader, paymentId = paymentId)
        if(redisClient.exists(paymentStatus.id)) {

            if (paymentStatus.paid && (paymentStatus.status == "waiting_for_capture" || paymentStatus.status == "succeeded")) {
                redisClient.incrBy("donations_amount", paymentStatus.amount.value.substringBefore(".").toLong())
                redisClient.incrBy("donations_number", 1L)
                redisClient.del(paymentStatus.id)
            }

        }

        return paymentStatus
    }

    fun getCounterStatus(): CounterStatus {
        return CounterStatus(redisClient.get("donations_amount").toLong(), redisClient.get("donations_number").toLong(), )
    }


}