package com.example.tddstart

import java.time.LocalDate
import java.time.YearMonth

class ExpiryDateCalculator {
    fun calculateExpiryDate(payData: PayData): LocalDate? {
        val addedMonths = when (payData.payAmount) {
            100_000 -> 12
            else -> payData.payAmount/10_000L
        }
        return if (payData.firstBillingDate != null) {
            expiryDateUsingFirstBillingDate(payData, addedMonths)
        } else {
            payData.billingDate?.plusMonths(addedMonths)
        }
    }

    private fun expiryDateUsingFirstBillingDate(
        payData: PayData,
        addedMonths: Long
    ): LocalDate? {
        val candidateExp: LocalDate? = payData.billingDate?.plusMonths(addedMonths)
        val dayOfFirstBilling = payData.firstBillingDate!!.dayOfMonth
        if (dayOfFirstBilling != candidateExp?.dayOfMonth) {

            val dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth()
            if (dayLenOfCandiMon < payData.firstBillingDate!!.dayOfMonth) {
                return candidateExp?.withDayOfMonth(dayLenOfCandiMon)
            }

            return candidateExp?.withDayOfMonth(dayOfFirstBilling)
        } else {
            return candidateExp
        }
    }
}
