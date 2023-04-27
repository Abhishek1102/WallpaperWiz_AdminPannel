package com.example.wallpaperwizfirebaseadmin.adapter

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperwizfirebaseadmin.R
import com.example.wallpaperwizfirebaseadmin.helper.AppConstant
import com.example.wallpaperwizfirebaseadmin.helper.AppConstant.Companion.dialog
import com.example.wallpaperwizfirebaseadmin.model.BomModel
import com.example.wallpaperwizfirebaseadmin.model.TctModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation

class TctAdapter(val context: Context, val list: ArrayList<TctModel>):RecyclerView.Adapter<TctAdapter.ViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_tct,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = list[position].color
        holder.rl_tct.setBackgroundColor(Color.parseColor(color))

        holder.card_remove.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.custom_dialog)
            val title = dialog.findViewById<TextView>(R.id.txt_dialog_title)
            val msg = dialog.findViewById<TextView>(R.id.txt_dialog_msg)
            val yes = dialog.findViewById<TextView>(R.id.txt_positive)
            val no = dialog.findViewById<TextView>(R.id.txt_negative)

            title.setText("Remove Color Tone")
            msg.setText("Are you sure to remove color tone?")
            yes.setText("yes")
            no.setText("no")



            yes.setOnClickListener {
                val db_coll=db.collection("thecolortone")

                val doc_query:Query = db_coll.whereEqualTo("id",list[position].id)

                doc_query.get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                    override fun onComplete(p0: Task<QuerySnapshot?>) {
                        if (p0.isSuccessful()){
                            for (document in p0.getResult()!!){
                                document.getReference().delete().addOnSuccessListener {
                                    dialog.dismiss()
                                    Toast.makeText(context, "Image deleted successfully", Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener {
                                    dialog.dismiss()
                                    Toast.makeText(context, "Error in deleting image", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else{
                            dialog.dismiss()
                            Log.d(TAG, "Error getting documents: ", p0.getException()) //Don't ignore potential errors!
                        }
                    }
                })
            }
            no.setOnClickListener {
                dialog.dismiss()
            }

            //Creating dialog box
            //Creating dialog box
            dialog.setCancelable(true)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val rl_tct:RelativeLayout = itemView.findViewById(R.id.rl_tct)
        val card_remove:CardView = itemView.findViewById(R.id.card_remove)
    }
}


//db.collection("bestofthemonth").document(list[position].id).delete().addOnCompleteListener {
//    if (it.isSuccessful){
////                        AppConstant.showProgressDialog(context)
//        Handler().postDelayed({
////                            AppConstant.hideProgressDialog()
//            Toast.makeText(context, "Image deleted successfully", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        },1000)
//    }
//    else{
////                        AppConstant.hideProgressDialog()
//        Toast.makeText(context, "Error in deleting image", Toast.LENGTH_SHORT).show()
//        d("TAG","Error ${it.exception!!.localizedMessage}")
//    }
//}
