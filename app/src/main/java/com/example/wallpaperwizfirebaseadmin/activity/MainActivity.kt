package com.example.wallpaperwizfirebaseadmin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityMainBinding
import com.example.wallpaperwizfirebaseadmin.helper.Utils

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.black)

        initView()

    }

    private fun initView() {
        binding.cardBom.setOnClickListener {
            startActivity(Intent(this,BomActivity::class.java))
        }
        binding.cardTct.setOnClickListener {
            startActivity(Intent(this,TctActivity::class.java))

        }
        binding.cardCategories.setOnClickListener {
            startActivity(Intent(this,CategoriesActivity::class.java))
        }
    }
}
