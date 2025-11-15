package com.example.midterm

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midterm.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUp()
        listeners()
    }

    private fun setUp() = with(binding){
        oldPrice.paintFlags = oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun listeners() = with(binding){
        btnAdd.setOnClickListener {
            val price = newPrice.text.toString()
            val priceNumber = price.filter { it.isDigit() }

            val intent = Intent(this@ProductActivity, MyCartActivity::class.java)
            intent.putExtra("PRICE", priceNumber)
            startActivity(intent)
        }
    }
}