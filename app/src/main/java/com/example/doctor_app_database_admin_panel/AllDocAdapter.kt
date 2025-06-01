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
            val mMorning = view.findViewById<EditText>(R.id.mMorning)
            val mAfternoon = view.findViewById<EditText>(R.id.mAfternoon)
            val mEvening = view.findViewById<EditText>(R.id.mEvening)

            val tMorning = view.findViewById<EditText>(R.id.tMorning)
            val tAfternoon = view.findViewById<EditText>(R.id.tAfternoon)
            val tEvening = view.findViewById<EditText>(R.id.tEvening)

            val wMorning = view.findViewById<EditText>(R.id.wMorning)
            val wAfternoon = view.findViewById<EditText>(R.id.wAfternoon)
            val wEvening = view.findViewById<EditText>(R.id.wEvening)

            val thMorning = view.findViewById<EditText>(R.id.thMorning)
            val thAfternoon = view.findViewById<EditText>(R.id.thAfternoon)
            val thEvening = view.findViewById<EditText>(R.id.thEvening)

            val fMorning = view.findViewById<EditText>(R.id.fMorning)
            val fAfternoon = view.findViewById<EditText>(R.id.fAfternoon)
            val fEvening = view.findViewById<EditText>(R.id.fEvening)

            val saMorning = view.findViewById<EditText>(R.id.saMorning)
            val saAfternoon = view.findViewById<EditText>(R.id.saAfternoon)
            val saEvening = view.findViewById<EditText>(R.id.saEvening)

            val sMorning = view.findViewById<EditText>(R.id.sMorning)
            val sAfternoon = view.findViewById<EditText>(R.id.sAfternoon)
            val sEvening = view.findViewById<EditText>(R.id.sEvening)


            MaterialAlertDialogBuilder(holder.itemView.context)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Add") { dialog, which ->
                    val schedule = mapOf(
                        "monday" to listOf(
                            TimeSlot("morning", mMorning.text.toString().toInt()),
                            TimeSlot("afternoon", mAfternoon.text.toString().toInt()),
                            TimeSlot("evening", mEvening.text.toString().toInt())
                        ),
                        "tuesday" to listOf(
                            TimeSlot("morning", tMorning.text.toString().toInt()),
                            TimeSlot("afternoon", tAfternoon.text.toString().toInt()),
                            TimeSlot("evening", tEvening.text.toString().toInt())
                        ),
                        "wednesday" to listOf(
                            TimeSlot("morning", wMorning.text.toString().toInt()),
                            TimeSlot("afternoon", wAfternoon.text.toString().toInt()),
                            TimeSlot("evening", wEvening.text.toString().toInt())
                        ),
                        "thursday" to listOf(
                            TimeSlot("morning", thMorning.text.toString().toInt()),
                            TimeSlot("afternoon", thAfternoon.text.toString().toInt()),
                            TimeSlot("evening", thEvening.text.toString().toInt())
                        ),
                        "friday" to listOf(
                            TimeSlot("morning", fMorning.text.toString().toInt()),
                            TimeSlot("afternoon", fAfternoon.text.toString().toInt()),
                            TimeSlot("evening", fEvening.text.toString().toInt())
                        ),
                        "saturday" to listOf(
                            TimeSlot("morning", saMorning.text.toString().toInt()),
                            TimeSlot("afternoon", saAfternoon.text.toString().toInt()),
                            TimeSlot("evening", saEvening.text.toString().toInt())
                        ),
                        "sunday" to listOf(
                            TimeSlot("morning", sMorning.text.toString().toInt()),
                            TimeSlot("afternoon", sAfternoon.text.toString().toInt()),
                            TimeSlot("evening", sEvening.text.toString().toInt())
                        ),
                    )
                    firebaseDB.collection("Department")
                        .document(depId).collection("Doctors")
                        .document(docList[position].did)
                        .update("schedule", schedule)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Schedule is added", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    dialog.dismiss()
                }
                .setNegativeButton("cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }


}
