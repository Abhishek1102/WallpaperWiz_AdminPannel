package com.example.wallpaperwizfirebaseadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.adapter.CategoriesAdapter
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityCategoriesBinding
import com.example.wallpaperwizfirebaseadmin.helper.Utils
import com.example.wallpaperwizfirebaseadmin.model.CategoriesModel
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoriesBinding
    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.black)
        initView()

    }

    private fun initView() {
        db = FirebaseFirestore.getInstance()
        db.collection("categories").addSnapshotListener { value, error ->
            val list_categories = arrayListOf<CategoriesModel>()
            val data = value!!.toObjects(CategoriesModel::class.java)
            list_categories.addAll(data)

            for (i in list_categories)
            {
                Log.e("@","onCreateView: $i")
            }
            binding.rvCategories.layoutManager = GridLayoutManager(this,2)
            binding.rvCategories.adapter = CategoriesAdapter(this,list_categories)

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
        val uid = db.collection("categories").document().id

        val finalData = CategoriesModel(uid,categoryName,imageLink)

        db.collection("categories").document(uid).set(finalData).addOnCompleteListener {
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