package ru.nse.alumniReunion2020.paymentService

import org.springframework.stereotype.Service


@Service
class PaymentService(
        var client: KassaClient
) {




    fun makePayment(form: Form) {
        var paymentObject: PaymentObject = PaymentObject(
                amount = Amount(value = form.amount.toString(), "RUB"),
                capture = true,
                confirmation = Confirmation(type = "redirect", return_url = "https://alumnines2020.nes.ru/"),
                description = "Donation from " + form.email
        )
        client.sendPayment(paymentObject)
    }
}