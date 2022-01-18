package com.example.dalentatechnicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutPaymentActivity : AppCompatActivity(),
    View.OnClickListener {
    //    Toolbar toolbar;
    var btnClose: Button? = null
    var btnGo: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_payment)

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());
        btnGo = findViewById(R.id.btn_cash_1)
        btnGo.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_checkout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_close) {
            finish()
            return true
        } else {
        }
        return false
    }

    override fun onBackPressed() {}
    override fun onClick(view: View) {
        Toast.makeText(this, "Tested", Toast.LENGTH_SHORT).show()
    }
}