package com.example.midterm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midterm.databinding.ActivityMyCartBinding

class MyCartActivity : AppCompatActivity() {
    private lateinit var price: String
    private var quantity: Int = 1
    private lateinit var binding: ActivityMyCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUp()
        listeners()
    }

    @SuppressLint("SetTextI18n")
    private fun listeners() = with(binding) {
        btnPlus.setOnClickListener {
            quantity++
            tvQuantity.text = quantity.toString()
            tvPrice.text = "${quantity * price.toInt()}$"
            tvTotal.text = "${quantity * price.toInt()} $"
        }

        btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                tvQuantity.text = quantity.toString()
                tvPrice.text = "${quantity * price.toInt()}$"
                tvTotal.text = "${quantity * price.toInt()} $"
            }
        }

        btnPlaceOrder.setOnClickListener {
            val intent = Intent(this@MyCartActivity, SuccessfulyPopActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUp() = with(binding){
        price = intent.getStringExtra("PRICE").toString()
        tvPrice.text = "$price$"
    }
}