package ru.nse.alumniReunion2020.paymentService

import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
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
                    @RequestHeader(value = "Idempotence-Key", required = true) idempotancyKey: String = Instant.now().toEpochMilli().toString(),
                    paymentObject: PaymentObject): PaymentResponse
}

data class PaymentObject(
        var amount: Amount,
        var capture: Boolean,
        var confirmation: Confirmation,
        var description: String
)


class Amount(
        var value: String,
        var currency: String = "RUB"
)

class Confirmation(
        var type: String,
        var return_url: String? = "",
        var confirmation_token: String? = ""
)


data class PaymentResponse(
        var confirmation: Confirmation
)

