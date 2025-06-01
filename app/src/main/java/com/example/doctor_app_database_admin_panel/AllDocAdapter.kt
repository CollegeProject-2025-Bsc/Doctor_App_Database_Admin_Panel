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
    val depId: String,
    val slot:Int
): RecyclerView.Adapter<AllDocAdapter.AllDocViewHolder>() {
    inner class AllDocViewHolder(item: View) : RecyclerView.ViewHolder(item)

    lateinit var context: Context
    lateinit var dName: TextView
    lateinit var dId: TextView
    lateinit var visit: Button
    lateinit var delete: Button
    lateinit var update: Button

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDocViewHolder {
        context = parent.context
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

            val slotCount = view.findViewById<TextView>(R.id.slotCount)
            slotCount.text = "Total slot - ${docList[position].slot}"

            val mMorning = view.findViewById<EditText>(R.id.mMorning)
            val mAfternoon = view.findViewById<EditText>(R.id.mAfternoon)
            val mEvening = view.findViewById<EditText>(R.id.mEvening)

            val mMorningHour = view.findViewById<EditText>(R.id.mMorningHour)
            val mAfternoonHour = view.findViewById<EditText>(R.id.mAfternoonHour)
            val mEveningHour = view.findViewById<EditText>(R.id.mEveningHour)

            val tMorning = view.findViewById<EditText>(R.id.tMorning)
            val tAfternoon = view.findViewById<EditText>(R.id.tAfternoon)
            val tEvening = view.findViewById<EditText>(R.id.tEvening)

            val tMorningHour = view.findViewById<EditText>(R.id.tMorningHour)
            val tAfternoonHour = view.findViewById<EditText>(R.id.tAfternoonHour)
            val tEveningHour = view.findViewById<EditText>(R.id.tEveningHour)

            val wMorning = view.findViewById<EditText>(R.id.wMorning)
            val wAfternoon = view.findViewById<EditText>(R.id.wAfternoon)
            val wEvening = view.findViewById<EditText>(R.id.wEvening)

            val wMorningHour = view.findViewById<EditText>(R.id.wMorningHour)
            val wAfternoonHour = view.findViewById<EditText>(R.id.wAfternoonHour)
            val wEveningHour = view.findViewById<EditText>(R.id.wEveningHour)

            val thMorning = view.findViewById<EditText>(R.id.thMorning)
            val thAfternoon = view.findViewById<EditText>(R.id.thAfternoon)
            val thEvening = view.findViewById<EditText>(R.id.thEvening)

            val thMorningHour = view.findViewById<EditText>(R.id.thMorningHour)
            val thAfternoonHour = view.findViewById<EditText>(R.id.thAfternoonHour)
            val thEveningHour = view.findViewById<EditText>(R.id.thEveningHour)

            val fMorning = view.findViewById<EditText>(R.id.fMorning)
            val fAfternoon = view.findViewById<EditText>(R.id.fAfternoon)
            val fEvening = view.findViewById<EditText>(R.id.fEvening)

            val fMorningHour = view.findViewById<EditText>(R.id.fMorningHour)
            val fAfternoonHour = view.findViewById<EditText>(R.id.fAfternoonHour)
            val fEveningHour = view.findViewById<EditText>(R.id.fEveningHour)

            val saMorning = view.findViewById<EditText>(R.id.saMorning)
            val saAfternoon = view.findViewById<EditText>(R.id.saAfternoon)
            val saEvening = view.findViewById<EditText>(R.id.saEvening)

            val saMorningHour = view.findViewById<EditText>(R.id.saMorningHour)
            val saAfternoonHour = view.findViewById<EditText>(R.id.saAfternoonHour)
            val saEveningHour = view.findViewById<EditText>(R.id.saEveningHour)

            val sMorning = view.findViewById<EditText>(R.id.sMorning)
            val sAfternoon = view.findViewById<EditText>(R.id.sAfternoon)
            val sEvening = view.findViewById<EditText>(R.id.sEvening)

            val sMorningHour = view.findViewById<EditText>(R.id.sMorningHour)
            val sAfternoonHour = view.findViewById<EditText>(R.id.sAfternoonHour)
            val sEveningHour = view.findViewById<EditText>(R.id.sEveningHour)


            MaterialAlertDialogBuilder(holder.itemView.context)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Add") { dialog, which ->
                    val schedule = mapOf(
                        "monday" to listOf(
                            TimeSlot("morning", mMorning.text.toString().toInt(),mMorningHour.text.toString()),
                            TimeSlot("afternoon", mAfternoon.text.toString().toInt(),mAfternoonHour.text.toString()),
                            TimeSlot("evening", mEvening.text.toString().toInt(),mEveningHour.text.toString())
                        ),
                        "tuesday" to listOf(
                            TimeSlot("morning", tMorning.text.toString().toInt(),tMorningHour.text.toString()),
                            TimeSlot("afternoon", tAfternoon.text.toString().toInt(),tAfternoonHour.text.toString()),
                            TimeSlot("evening", tEvening.text.toString().toInt(),tEveningHour.text.toString())
                        ),
                        "wednesday" to listOf(
                            TimeSlot("morning", wMorning.text.toString().toInt(),wMorningHour.text.toString()),
                            TimeSlot("afternoon", wAfternoon.text.toString().toInt(),wAfternoonHour.text.toString()),
                            TimeSlot("evening", wEvening.text.toString().toInt(),wEveningHour.text.toString())
                        ),
                        "thursday" to listOf(
                            TimeSlot("morning", thMorning.text.toString().toInt(),thMorningHour.text.toString()),
                            TimeSlot("afternoon", thAfternoon.text.toString().toInt(),thAfternoonHour.text.toString()),
                            TimeSlot("evening", thEvening.text.toString().toInt(),thEveningHour.text.toString())
                        ),
                        "friday" to listOf(
                            TimeSlot("morning", fMorning.text.toString().toInt(),fMorningHour.text.toString()),
                            TimeSlot("afternoon", fAfternoon.text.toString().toInt(),fAfternoonHour.text.toString()),
                            TimeSlot("evening", fEvening.text.toString().toInt(),fEveningHour.text.toString())
                        ),
                        "saturday" to listOf(
                            TimeSlot("morning", saMorning.text.toString().toInt(),saMorningHour.text.toString()),
                            TimeSlot("afternoon", saAfternoon.text.toString().toInt(),saAfternoonHour.text.toString()),
                            TimeSlot("evening", saEvening.text.toString().toInt(),saEveningHour.text.toString())
                        ),
                        "sunday" to listOf(
                            TimeSlot("morning", sMorning.text.toString().toInt(),sMorningHour.text.toString()),
                            TimeSlot("afternoon", sAfternoon.text.toString().toInt(),sAfternoonHour.text.toString()),
                            TimeSlot("evening", sEvening.text.toString().toInt(),sEveningHour.text.toString())
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

    data class TimeSlot(
        val time: String,
        val duration: Int,
        val hour: String
    )

}
