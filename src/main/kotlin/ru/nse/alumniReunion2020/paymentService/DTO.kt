package ru.nse.alumniReunion2020.paymentService



data class Form(var email: String, var amount: Int)

data class PaymentObject(
        var amount: Amount,
        var capture: Boolean,
        var confirmation: Confirmation,
        var description: String
)


class Amount(
        val value: String,
        val currency: String = "RUB"
)

class Confirmation(
        val type: String,
        var return_url: String? = "",
        var confirmation_token: String? = ""
)


data class PaymentResponse(
        val id: String,
        val amount: Amount,
        val confirmation: Confirmation
)


data class PaymentStatus(
        val id: String,
        val status: String,
        val paid: Boolean,
        val amount: Amount
)

data class CounterStatus(
    val donationsAmount: Long,
    val donationsNumber: Long
)