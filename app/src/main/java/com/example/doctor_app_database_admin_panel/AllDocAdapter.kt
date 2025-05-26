package com.example.doctor_app_database_admin_panel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class AllDocAdapter(
    val docList: ArrayList<DoctorModel>,
    val firebaseDB: FirebaseFirestore,
    val depId: String
): RecyclerView.Adapter<AllDocAdapter.AllDocViewHolder>() {
    inner class AllDocViewHolder(item: View) : RecyclerView.ViewHolder(item)

    lateinit var dName: TextView
    lateinit var dId: TextView
    lateinit var visit: Button
    lateinit var delete: Button
    lateinit var update: Button

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDocViewHolder {
        return AllDocViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.doc_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return docList.size
    }

    override fun onBindViewHolder(holder: AllDocViewHolder, position: Int) {
        dId = holder.itemView.findViewById(R.id.dId)
        dName = holder.itemView.findViewById(R.id.dName)
        visit = holder.itemView.findViewById(R.id.visit)
        delete = holder.itemView.findViewById(R.id.delete)
        update = holder.itemView.findViewById(R.id.edit)
        dId.text = docList[position].did
        dName.text = docList[position].dname


        update.setOnClickListener {
            Toast.makeText(holder.itemView.context, "currently unavailable", Toast.LENGTH_SHORT).show()
        }

        delete.setOnClickListener{
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Delete Doctor")
                .setCancelable(false)
                .setPositiveButton("Delete") { dialog, which ->
                    firebaseDB.collection("Department")
                        .document(depId).collection("Doctors").document(docList[position].did).delete()
                    dialog.dismiss()

                }
                .setNegativeButton("cancel") { dialog, which ->
                    dialog.dismiss()
                }.show()


        }
        visit.setOnClickListener {
            val view = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.visit_dialog, null, false)
            val alert = MaterialAlertDialogBuilder(holder.itemView.context)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Add") { dialog, which ->
                    val morning = view.findViewById<EditText>(R.id.morning).text.toString()
                    val afternoon = view.findViewById<EditText>(R.id.afternoon).text.toString()
                    val evening = view.findViewById<EditText>(R.id.evening).text.toString()
                    if (morning.isNotEmpty() && afternoon.isNotEmpty() && evening.isNotEmpty()) {
                        createSlot(
                            morning,
                            afternoon,
                            evening,
                            docList[position].did,
                            depId,
                            holder.itemView.context
                        )
                    } else {
                        Toast.makeText(
                            holder.itemView.context,
                            "Please fill all the fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setPositiveButton
                    }
                    dialog.dismiss()

                }
                .setNegativeButton("cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun createSlot(
        morning: String,
        afternoon: String,
        evening: String,
        did: String,
        depId: String,
        context: Context
    ) {
        val sid = firebaseDB.collection("Department")
            .document(depId).collection("Doctors")
            .document(did).collection("VisitingSlot").document().id

        firebaseDB.collection("Department").document(depId).collection("Doctors")
            .document(did).collection("VisitingSlot").document(sid).set(VisitingSlot(
                id = sid,
                morning = morning,
                afternoon = afternoon,
                evening = evening
            ))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Slot Added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Slot not Added", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
