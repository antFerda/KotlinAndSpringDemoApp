package ru.nse.alumniReunion2020.paymentService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class PaymentController {

    @Autowired
    lateinit var paymentService: PaymentService

    var logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @CrossOrigin(value = ["*"])
    @PostMapping(path = ["/donate"])
    fun handlePayment(form: Form): PaymentResponse {
        logger.debug("Posted form $form")
        return paymentService.makePayment(form)
    }

    @GetMapping(path = ["/donate"])
    fun getCheck(): String {
        return "health check completed"
    }

}


data class Form(var email: String, var amount: Int)