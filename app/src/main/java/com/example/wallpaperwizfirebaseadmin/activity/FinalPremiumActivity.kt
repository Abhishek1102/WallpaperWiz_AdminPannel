package com.example.wallpaperwizfirebaseadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.adapter.FinalCategoryAdapter
import com.example.wallpaperwizfirebaseadmin.adapter.FinalPremiumAdapter
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityFinalCategoryBinding
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityFinalPremiumBinding
import com.example.wallpaperwizfirebaseadmin.helper.Utils
import com.example.wallpaperwizfirebaseadmin.model.BomModel
import com.google.firebase.firestore.FirebaseFirestore

class FinalPremiumActivity : AppCompatActivity() {

    lateinit var binding: ActivityFinalPremiumBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.black)
        initView()

    }

    private fun initView() {
        db = FirebaseFirestore.getInstance()

        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")


        db.collection("premium").document(uid!!).collection("wallpaper").addSnapshotListener { value, error ->
            val list = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            list.addAll(data!!)

            binding.tvCategoryName.text = name

            binding.rvFinalCategory.setHasFixedSize(true)
            binding.rvFinalCategory.layoutManager = GridLayoutManager(this,2)
            binding.rvFinalCategory.adapter = FinalPremiumAdapter(this,list,uid)

            binding.tvSubmit.setOnClickListener {
                if (binding.edtLink.text.isEmpty()){
                    Toast.makeText(applicationContext, "Link cannot be empty", Toast.LENGTH_SHORT).show()
                }
                else{

                    val final_uid = db.collection("premium").document().id

                    val finalData = BomModel(final_uid, binding.edtLink.text.toString().trim())

                    db.collection("premium").document(uid).collection("wallpaper").document(final_uid).set(finalData).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(applicationContext, "Image added to database", Toast.LENGTH_SHORT).show()
                            binding.edtLink.clearFocus()
                            binding.edtLink.text.clear()
                        }
                        else{
                            Toast.makeText(applicationContext, "Error in adding image to database", Toast.LENGTH_SHORT).show()
                            Log.d("TAG", "Error ${it.exception!!.localizedMessage}")
                        }
                    }

                }
            }

        }
    }
}