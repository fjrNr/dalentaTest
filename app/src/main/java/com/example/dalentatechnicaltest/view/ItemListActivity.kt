package com.example.dalentatechnicaltestjava

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.dalentatechnicaltest.ItemViewModel
import com.example.dalentatechnicaltest.R
import com.example.dalentatechnicaltest.model.Product
import java.text.NumberFormat
import java.util.*

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane = false
    var viewModel: ItemViewModel? = null
    var btnCharge: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
        val toolbar =
            findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title
        if (findViewById<View?>(R.id.checkout_detail) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        val recyclerView = findViewById<View>(R.id.food_list)!!
        btnCharge = findViewById(R.id.btn_charge)
        btnCharge.setOnClickListener(this)
        viewModel = ItemViewModel(application)
        viewModel.setProducts()
        viewModel.getProducts().observe(this,
                Observer<List<Any>> { products ->
                    setupRecyclerView(
                        recyclerView as RecyclerView,
                        products
                    )
                })
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        products: List<Product>
    ) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            this,
            products,
            mTwoPane
        )
    }

    inner class SimpleItemRecyclerViewAdapter internal constructor(
        parent: ItemListActivity,
        items: List<Product>,
        twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        var localeID = Locale("in", "ID")
        var rpFormat =
            NumberFormat.getCurrencyInstance(localeID)
        private val mParentActivity: ItemListActivity
        private val mValues: List<Product>
        private val mTwoPane: Boolean
        private val mOnClickListener =
            View.OnClickListener { view ->
                val product: Product = view.tag as Product
                Toast.makeText(baseContext, product.name, Toast.LENGTH_LONG).show()
                inputDialog(product)
            }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.food_list_content, parent, false)
            return ViewHolder(
                view
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            holder.mIdView.setText(mValues[position].name)
            holder.mContentView.setText(rpFormat.format(mValues[position].price))
            holder.itemView.tag = mValues[position]
            holder.itemView.setOnClickListener(mOnClickListener)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        internal inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
            val mIdView: TextView
            val mContentView: TextView

            init {
                mIdView = view.findViewById<View>(R.id.txt_name) as TextView
                mContentView = view.findViewById<View>(R.id.txt_price) as TextView
            }
        }

        init {
            mValues = items
            mParentActivity = parent
            mTwoPane = twoPane
        }
    }

    private fun inputDialog(product: Product) {
        val aDialog =
            AlertDialog.Builder(this)
        val dialogView: View =
            layoutInflater.inflate(R.layout.dialog_product_detail, null)
        dialogView.setPaddingRelative(10, 10, 10, 10)
        val btnClose =
            dialogView.findViewById<Button>(R.id.btn_close)
        val btnAddProduct =
            dialogView.findViewById<Button>(R.id.btn_add)
        val txtTitle = dialogView.findViewById<TextView>(R.id.txt_title)
        val btnMinus =
            dialogView.findViewById<Button>(R.id.btn_minus)
        val edTextQty = dialogView.findViewById<EditText>(R.id.edit_txt_qty)
        val btnPlus =
            dialogView.findViewById<Button>(R.id.btn_plus)
        edTextQty.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                var number = 0
                var txtNumber = edTextQty.text.toString().trim { it <= ' ' }
                if (!txtNumber.isEmpty()) {
                    if (txtNumber.length > 2) {
                        txtNumber = txtNumber.substring(0, txtNumber.length - 1)
                    }
                    number = txtNumber.toInt()
                }
                edTextQty.removeTextChangedListener(this)
                if (number != 0) {
                    try {
                        edTextQty.setText(Integer.toString(number))
                    } catch (nfe: NumberFormatException) {
                        nfe.printStackTrace()
                    }
                }
                edTextQty.addTextChangedListener(this)
            }
        })
        val edTextNotes = dialogView.findViewById<EditText>(R.id.edit_txt_notes)
        val switchServiceTax =
            dialogView.findViewById<Switch>(R.id.switchServiceTax)
        val switchGST =
            dialogView.findViewById<Switch>(R.id.switchGST)
        txtTitle.setText(product.name)
        aDialog.setView(dialogView)
        aDialog.show()
        btnPlus.setOnClickListener {
            var qty = 0
            val txtNumber = edTextQty.text.toString().trim { it <= ' ' }
            if (!txtNumber.isEmpty()) {
                qty = txtNumber.toInt()
            }
            qty++
            edTextQty.setText(Integer.toString(qty))
        }
        btnMinus.setOnClickListener {
            var qty = 0
            val txtNumber = edTextQty.text.toString().trim { it <= ' ' }
            if (!txtNumber.isEmpty()) {
                qty = txtNumber.toInt()
            }
            qty--
            if (qty < 0) qty = 0
            edTextQty.setText(Integer.toString(qty))
        }
        btnClose.setOnClickListener {
            Toast.makeText(baseContext, "Closing", Toast.LENGTH_LONG).show()
        }
        btnAddProduct.setOnClickListener {
            val qty = edTextQty.text.toString().trim { it <= ' ' }.toInt()
            viewModel.setProductDetails(
                product.name,
                product.price,
                qty,
                "Whole",
                product.addSizePrice,
                "",
                switchGST.isChecked,
                switchServiceTax.isChecked
            )
            Toast.makeText(
                baseContext,
                Integer.toString(qty),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onClick(view: View) {
        if (mTwoPane) {
            val i = Intent(this, CheckoutPaymentActivity::class.java)
            startActivity(i)
        } else {
            val i = Intent(this, ItemDetailActivity::class.java)
            i.putExtra("productDetail", 0)
        }
    }
}