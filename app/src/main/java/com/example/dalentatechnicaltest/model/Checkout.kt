package com.example.dalentatechnicaltest.model

class Checkout {
    private var isDelivery: Boolean? = null
    private var productList: List<ProductDetail?>? = null
    private var subTotalPrice = 0
    private var totalTax = 0
    private var totalPrice = 0

    fun getDelivery(): Boolean? {
        return isDelivery
    }

    fun setDelivery(delivery: Boolean?) {
        isDelivery = delivery
    }

    fun getProductList(): List<ProductDetail?>? {
        return productList
    }

    fun setProductList(productList: List<ProductDetail?>?) {
        this.productList = productList
    }

    fun getSubTotalPrice(): Int {
        return subTotalPrice
    }

    fun setSubTotalPrice(subTotalPrice: Int) {
        this.subTotalPrice = subTotalPrice
    }

    fun getTotalTax(): Int {
        return totalTax
    }

    fun setTotalTax(totalTax: Int) {
        this.totalTax = totalTax
    }

    fun getTotalPrice(): Int {
        return totalPrice
    }

    fun setTotalPrice(totalPrice: Int) {
        this.totalPrice = totalPrice
    }
}