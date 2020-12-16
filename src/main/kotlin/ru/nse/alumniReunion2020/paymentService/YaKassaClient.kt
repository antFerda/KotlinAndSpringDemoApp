package ru.nse.alumniReunion2020.paymentService

import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.Instant
import java.util.*


@FeignClient("paymentClient", url = "https://api.yookassa.ru/v3")
interface KassaClient {

    @RequestMapping(method = [RequestMethod.POST], value = ["/payments"])
    @Headers("Content-Type: application/json")
    fun sendPayment(@RequestHeader(value = "Authorization", required = true) authorization: String,
                    @RequestHeader(value = "Idempotence-Key", required = true) idempotencyKey: String = Instant.now().toEpochMilli().toString(),
                    paymentObject: PaymentObject
    ): PaymentResponse



    @RequestMapping(method = [RequestMethod.GET], value = ["/payments/{paymentId}"])
    @Headers("Content-Type: application/json")
    fun getPaymentStatus(
            @RequestHeader(value = "Authorization", required = true) authorization: String,
            @RequestHeader(value = "Idempotence-Key", required = true) idempotencyKey: String = Instant.now().toEpochMilli().toString(),
            @PathVariable("paymentId") paymentId: String
    ): PaymentStatus
}



