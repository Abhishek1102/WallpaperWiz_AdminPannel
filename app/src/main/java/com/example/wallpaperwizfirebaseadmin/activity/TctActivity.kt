package com.example.wallpaperwizfirebaseadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.adapter.TctAdapter
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityTctBinding
import com.example.wallpaperwizfirebaseadmin.model.TctModel
import com.google.firebase.firestore.FirebaseFirestore

class TctActivity : AppCompatActivity() {

    lateinit var binding: ActivityTctBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTctBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        db = FirebaseFirestore.getInstance()
        db.collection("thecolortone").addSnapshotListener { value, error ->
            val list_tct = arrayListOf<TctModel>()
            val data = value!!.toObjects(TctModel::class.java)
            list_tct.addAll(data)

            binding.rvTct.layoutManager = GridLayoutManager(this, 2)
            binding.rvTct.adapter = TctAdapter(this, list_tct)

        }

        binding.tvSubmit.setOnClickListener {
            if (binding.edtLink.text.isEmpty() && binding.edtColor.text.isEmpty()){
                Toast.makeText(applicationContext, "Value cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                addWallpaperToDatabase(binding.edtColor.text.toString().trim(),binding.edtLink.text.toString().trim())
            }
        }
    }

    private fun addWallpaperToDatabase(color: String,imageLink:String) {
        val uid = db.collection("thecolortone").document().id
        val finalData = TctModel(uid,imageLink,color)
        db.collection("thecolortone").document(uid).set(finalData).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(applicationContext, "Image added to database", Toast.LENGTH_SHORT).show()
                binding.edtLink.clearFocus()
                binding.edtLink.text.clear()
                binding.edtColor.clearFocus()
                binding.edtColor.text.clear()
            }else {
                Toast.makeText(
                    applicationContext,
                    "Error in adding image to database",
                    Toast.LENGTH_SHORT
                ).show()
                d("TAG", "Error ${it.exception!!.localizedMessage}")
            }
        }

    }
}