package com.example.wallpaperwizfirebaseadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.adapter.BomAdapter
import com.example.wallpaperwizfirebaseadmin.databinding.ActivityBomBinding
import com.example.wallpaperwizfirebaseadmin.helper.Utils
import com.example.wallpaperwizfirebaseadmin.model.BomModel
import com.google.firebase.firestore.FirebaseFirestore

class BomActivity : AppCompatActivity() {

    lateinit var binding: ActivityBomBinding
    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.black)
        initView()

    }

    private fun initView() {

        db= FirebaseFirestore.getInstance()
        //snapshotlistener always recycle data few seconds that is the advantage of using it so no need to refresh
        db.collection("bestofthemonth").addSnapshotListener { value, error ->
            val list_bom = arrayListOf<BomModel>()
            val data = value!!.toObjects(BomModel::class.java)
            list_bom.addAll(data)

            binding.rvBom.layoutManager = GridLayoutManager(this,2)
            binding.rvBom.adapter = BomAdapter(this, list_bom)

        }

        binding.tvSubmit.setOnClickListener {
            if (binding.edtLink.text.isEmpty()){
                Toast.makeText(applicationContext, "Link cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                addLinkToDatabase(binding.edtLink.text.toString().trim())
            }
        }
    }

    private fun addLinkToDatabase(imageLink:String) {

        val uid = db.collection("bestofthemonth").document().id
        val finalData = BomModel(uid,imageLink)

        db.collection("bestofthemonth").document().set(finalData).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(applicationContext, "Image added to database", Toast.LENGTH_SHORT).show()
                binding.edtLink.clearFocus()
                binding.edtLink.text.clear()
            }else{
                Toast.makeText(applicationContext, "Error in adding image to database", Toast.LENGTH_SHORT).show()
                d("TAG","Error ${it.exception!!.localizedMessage}")
            }
        }

    }
}