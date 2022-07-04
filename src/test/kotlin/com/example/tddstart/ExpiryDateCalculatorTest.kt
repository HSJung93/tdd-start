package com.example.tddstart

import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ExpiryDateCalculatorTest {

    @Test
    fun `만원을 납부하면 한달 뒤가 만료일이 된다`() {
        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 4, 1)
        )
        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 2, 28)
        )
    }

    @Test
    fun `납부일과 한 달 뒤 일자가 같지 않을 경우`() {
        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 2, 28)
        )
    }

    @Test
    fun `첫 납부일과 만료일 일자가 다를 때 만원 납부`() {

        val payData = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 31))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(10_000)
            .build()

        assertExpiryDate(payData, LocalDate.of(2019, 3, 31))

        val payData2 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 30))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(10_000)
            .build()

        assertExpiryDate(payData2, LocalDate.of(2019, 3, 30))

        val payData3 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 5, 31))
            .billingDate(LocalDate.of(2019, 6, 30))
            .payAmount(10_000)
            .build()

        assertExpiryDate(payData3, LocalDate.of(2019, 7, 31))
    }

    @Test
    fun `이만원 이상 납부하면 비례해서 만료일 계산`() {
        val payData = PayData.builder()
            .billingDate(LocalDate.of(2019, 3, 1))
            .payAmount(20_000)
            .build()
        assertExpiryDate(
            payData,
            LocalDate.of(2019, 5, 1)
        )

        val payData2 = PayData.builder()
            .billingDate(LocalDate.of(2019, 3, 1))
            .payAmount(30_000)
            .build()
        assertExpiryDate(
            payData2,
            LocalDate.of(2019, 6, 1)
        )
    }

    @Test
    fun `첫 납부일과 만료일 일자가 다를때 이만원 이상 납부`() {
        val payData = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 31))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(40_000)
            .build()

        assertExpiryDate(
            payData,
            LocalDate.of(2019, 6, 30)
        )

        val payData2 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 3, 31))
            .billingDate(LocalDate.of(2019, 4, 28))
            .payAmount(30_000)
            .build()

        assertExpiryDate(
            payData2,
            LocalDate.of(2019, 7, 31)
        )
    }

    @Test
    fun `십만원을 납부하면 1년 제공`() {
        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 28))
                .payAmount(100_000)
                .build(),
            LocalDate.of(2020, 1, 28)
        )
    }

    private fun assertExpiryDate(
        payData: PayData,
        expectedExpiryDate: LocalDate
    ){
        val cal = ExpiryDateCalculator()
        val expiryDate = cal.calculateExpiryDate(payData)
        assertEquals(expectedExpiryDate, expiryDate)
    }
}