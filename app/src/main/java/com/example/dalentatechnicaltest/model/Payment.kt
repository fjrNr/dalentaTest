package com.example.dalentatechnicaltest.model

class Payment {
    private var totalPrice = 0
    private var method: String? = null
    private var amount = 0

    fun getTotalPrice(): Int {
        return totalPrice
    }

    fun setTotalPrice(totalPrice: Int) {
        this.totalPrice = totalPrice
    }

    fun getMethod(): String? {
        return method
    }

    fun setMethod(method: String?) {
        this.method = method
    }

    fun getAmount(): Int {
        return amount
    }

    fun setAmount(amount: Int) {
        this.amount = amount
    }
}