package com.example.dalentatechnicaltest

import android.app.Application;
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.dalentatechnicaltest.model.Product;
import com.example.dalentatechnicaltest.model.ProductDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type


class ItemViewModel {
    private val products = MutableLiveData<List<Product>>()
    private val productDetails = MutableLiveData<List<ProductDetail>?>()

    //sharedPreferences
    val spApp = "spApp"
    val spProDet = "spProductDetail"

    var sp: SharedPreferences = getApplication().getSharedPreferences(spApp, MODE_PRIVATE)
    var spEditor = sp.edit()

    fun ItemViewModel(application: Application) {
        super(application)
    }

    fun getProducts(): MutableLiveData<List<Product>>? {
        return products
    }

    fun getProductDetails(): MutableLiveData<List<ProductDetail>?>? {
        val gson = Gson()
        val json = sp.getString(spProDet, null)
        val type: Type = object : TypeToken<ArrayList<ProductDetail?>?>() {}.getType()
        val productDetail: List<ProductDetail> = gson.fromJson(json, type)
        productDetails.setValue(productDetail)
        return productDetails
    }

    fun setProducts() {
        val product: MutableList<Product> = ArrayList()
        product.add(Product("Unagi Rice Bowl", "", 45000, 20000))
        products.setValue(product)
    }

    fun setProductDetails(
        name: String,
        price: Int,
        quantity: Int,
        additionalName: String,
        additionalPrice: Int,
        notes: String,
        isGstTax: Boolean,
        isServiceTax: Boolean
    ) {
        val gson = Gson()
        var json = sp.getString(spProDet, null)
        val type: Type = object : TypeToken<ArrayList<ProductDetail?>?>() {}.getType()
        val productDetail: MutableList<ProductDetail> =
            gson.fromJson(json, type)
        var gstTax = 0
        var serviceTax = 0
        if (isGstTax) gstTax = price / 10
        if (isServiceTax) serviceTax = price / 20
        productDetail.add(
            ProductDetail(name,
                price,
                quantity,
                additionalName,
                additionalPrice,
                notes,
                gstTax,
                serviceTax
            )
        )
        json = gson.toJson(productDetail)
        spEditor.putString(spProDet, json)
        spEditor.apply()
        productDetails.setValue(productDetail)
    }
}