package com.example.wallpaperwizfirebaseadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.adapter.CategoriesAdapter
import com.example.wallpaperwizfirebaseadmin.adapter.PremiumAdapter
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityCategoriesBinding
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityPremiumBinding
import com.example.wallpaperwizfirebaseadmin.helper.Utils
import com.example.wallpaperwizfirebaseadmin.model.CategoriesModel
import com.example.wallpaperwizfirebaseadmin.model.PremiumModel
import com.google.firebase.firestore.FirebaseFirestore

class PremiumActivity : AppCompatActivity() {

    lateinit var binding: ActivityPremiumBinding
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.black)
        initView()
    }

    private fun initView() {
        db = FirebaseFirestore.getInstance()
        db.collection("premium").addSnapshotListener { value, error ->
            val list_premium = arrayListOf<PremiumModel>()
            val data = value!!.toObjects(PremiumModel::class.java)
            list_premium.addAll(data)

            for (i in list_premium)
            {
                Log.e("@","onCreateView: $i")
            }
            binding.rvPremium.layoutManager = GridLayoutManager(this,2)
            binding.rvPremium.adapter = PremiumAdapter(this,list_premium)

            binding.tvSubmit.setOnClickListener {
                if (binding.edtName.text.isEmpty() && binding.edtLink.text.isEmpty()){
                    Toast.makeText(applicationContext, "Value cannot be empty", Toast.LENGTH_SHORT).show()
                }else{
                    addCategoryToDatabase(binding.edtLink.text.toString(),binding.edtName.text.toString())
                }
            }
        }
    }

    private fun addCategoryToDatabase(imageLink: String, categoryName: String) {
        val uid = db.collection("premium").document().id

        val finalData = PremiumModel(uid,categoryName,imageLink)

        db.collection("premium").document(uid).set(finalData).addOnCompleteListener {
            if (it.isSuccessful()){
                Toast.makeText(applicationContext, "Category added to database", Toast.LENGTH_SHORT).show()
                binding.edtLink.clearFocus()
                binding.edtLink.text.clear()
                binding.edtName.clearFocus()
                binding.edtName.text.clear()
            }
            else{
                Toast.makeText(applicationContext, "Error in adding category to database", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "Error ${it.exception!!.localizedMessage}")
            }
        }
    }
}