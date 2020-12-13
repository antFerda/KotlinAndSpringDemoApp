package ru.nse.alumniReunion2020.paymentService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class PaymentController {

    @Autowired
    lateinit var paymentService: PaymentService


    @PostMapping(path = arrayOf("/donate"))
    fun handlePayment(form: Form): String {
        println(form)
        paymentService.makePayment(form)
        return "200"
    }

    @GetMapping(path = arrayOf("/donate"))
    fun getCheck(): String {
        return "Hello"
    }

}


data class Form(var email: String, var amount: Int)