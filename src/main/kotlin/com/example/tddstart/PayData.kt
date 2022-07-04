package com.example.tddstart

import java.time.LocalDate

class PayData {
    var firstBillingDate: LocalDate? = null
        private set
    var billingDate: LocalDate? = null
        private set
    var payAmount = 0
        private set

    private constructor() {}
    constructor(firstBillingDate: LocalDate?, billingDate: LocalDate?, payAmount: Int) {
        this.firstBillingDate = firstBillingDate
        this.billingDate = billingDate
        this.payAmount = payAmount
    }

    class Builder {
        private val data = PayData()
        fun firstBillingDate(firstBillingDate: LocalDate?): Builder {
            data.firstBillingDate = firstBillingDate
            return this
        }

        fun billingDate(billingDate: LocalDate?): Builder {
            data.billingDate = billingDate
            return this
        }

        fun payAmount(payAmount: Int): Builder {
            data.payAmount = payAmount
            return this
        }

        fun build(): PayData {
            return data
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}