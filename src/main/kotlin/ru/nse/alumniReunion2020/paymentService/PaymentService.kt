package ru.nse.alumniReunion2020.paymentService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class PaymentService(
        var client: KassaClient
) {
    @Value("\${authorizationHeader}")
    lateinit var authorizationHeader: String

    var logger: Logger = LoggerFactory.getLogger(this::class.java)



    fun makePayment(form: Form): PaymentResponse {
        var paymentObject: PaymentObject = PaymentObject(
                amount = Amount(value = form.amount.toString(), "RUB"),
                capture = true,
                confirmation = Confirmation(type = "embedded"),
                description = "Donation from " + form.email
        )

        logger.debug("Header $authorizationHeader")

        return client.sendPayment(paymentObject = paymentObject, authorization = authorizationHeader)
    }
}