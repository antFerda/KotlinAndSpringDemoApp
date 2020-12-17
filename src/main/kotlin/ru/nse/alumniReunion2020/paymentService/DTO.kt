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
        val amount: Amount,
        var cancellation_details: CancellationDetails
)

class CancellationDetails(
        val party: String,
        private val reason: String
) {
    val detailedReason = details[reason]
}

var details = mapOf(
        Pair("3d_secure_failed", "Не пройдена аутентификация по 3-D Secure."),
        Pair("call_issuer", "Оплата данным платежным средством отклонена по неизвестным причинам."),
        Pair("canceled_by_merchant", "Платеж отменен получателем."),
        Pair("card_expired", "Истек срок действия банковской карты."),
        Pair("country_forbidden", "Вы не можете оплатить банковской картой из этой страны."),
        Pair("expired_on_capture", "Истек срок списания оплаты."),
        Pair("expired_on_confirmation", "Истек срок подтверждения оплаты."),
        Pair("fraud_suspected", "Платеж заблокирован."),
        Pair("general_decline", "Нет данных по детализации ошибки."),
        Pair("identification_required", "Превышены ограничения на платежи для кошелька ЮMoney."),
        Pair("insufficient_funds", "Недостаточно средств на счету."),
        Pair("internal_timeout", "Технические неполадке на стороне платежного шлюза."),
        Pair("invalid_card_number", "Неправильно указан номер карты."),
        Pair("invalid_csc", "Неправильно указан код CVV2 (CVC2, CID). "),
        Pair("issuer_unavailable", "Организация, выпустившая платежное средство, недоступна."),
        Pair("payment_method_limit_exceeded", "Исчерпан лимит платежей для данного платежного средства"),
        Pair("payment_method_restricted", "Операции данным платежным средством запрещены."),
        Pair("permission_revoked", "Нельзя провести безакцептное списание: пользователь отозвал разрешение.")
)

data class CounterStatus(
    val donationsAmount: Long,
    val donationsNumber: Long
)