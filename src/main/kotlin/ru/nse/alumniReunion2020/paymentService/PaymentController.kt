package ru.nse.alumniReunion2020.paymentService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam


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

    @GetMapping(path = ["/check"])
    fun getCheck(): String {
        return paymentService.getAllKeys()
    }

    @CrossOrigin(value = ["*"])
    @GetMapping(path = ["/payment/{paymentId}"])
    fun getPaymentStatus(@PathVariable("paymentId") paymentId: String): PaymentStatus {
        return paymentService.getStatus(paymentId)
    }

    @CrossOrigin(value = ["*"])
    @GetMapping(path = ["/counter"])
    fun getCounterStatus(): CounterStatus {
        return paymentService.getCounterStatus()
    }

}

