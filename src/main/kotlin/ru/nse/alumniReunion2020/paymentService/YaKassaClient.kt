package ru.nse.alumniReunion2020.paymentService

import feign.Headers
import feign.RequestLine
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@FeignClient("paymentClient", url = "https://api.yookassa.ru/v3")
interface KassaClient {

    @RequestMapping(method = [RequestMethod.POST], value = ["/payments"])
    @Headers("Content-Type: application/json", "Authorization: Basic dGVzdDp0ZXN0XzEzbTZxNEpwUlN2TXludHVpQ09HSmhrQzF2X2RRNnEtaDBBcjMzTjZQMEk=")
    fun sendPayment(paymentObject: PaymentObject): String
}



data class PaymentObject(
        var amount: Amount,
        var capture: Boolean,
        var confirmation: Confirmation,
        var description: String
) {

//{
//    "amount": {
//        "value": "100.00",
//        "currency": "RUB"
//    },
//    "capture": true,
//    "confirmation": {
//        "type": "redirect",
//        "return_url": "https://www.merchant-website.com/return_url"
//    },
//    "description": "Заказ №1"
//}
}


class Amount(
        var value: String,
        var currency: String
)


class Confirmation(
        var type: String,
        var return_url: String,
)