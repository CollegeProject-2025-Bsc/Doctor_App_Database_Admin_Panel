package com.example.doctor_app_database_admin_panel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doctor_app_database_admin_panel.databinding.ActivityDoctorBinding
import com.example.doctor_app_database_admin_panel.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class Doctor : AppCompatActivity() {
    lateinit var docBinding: ActivityDoctorBinding
    private lateinit var firebaseDB: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        docBinding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(docBinding.root)

        val department = intent.getStringExtra("dep").toString()
        val depId = intent.getStringExtra("id").toString()
        firebaseDB = FirebaseFirestore.getInstance()
        docBinding.back.setOnClickListener {
            finish()
        }

        docBinding.doctor.text = department.toString()
        Toast.makeText(applicationContext,department, Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext,depId, Toast.LENGTH_SHORT).show()
        docBinding.submit.setOnClickListener {

            val id = firebaseDB.collection("Department").document(depId).collection("Doctors").document().id

            val dId : String = id
            val dName: String = docBinding.dName.text.toString()
            val specialization : String = docBinding.specialization.text.toString()
            val degree : String = docBinding.degree.text.toString()
            val rating : Int = Integer.parseInt(docBinding.rating.text.toString())
            val fee : Int = Integer.parseInt(docBinding.fee.text.toString())
            val profile_pic :String = docBinding.profilePic.text.toString()
            val about : String = docBinding.about.text.toString()
            val experience : Int = Integer.parseInt(docBinding.experience.text.toString())
            val cAddress : String = docBinding.cAddress.text.toString()

            val slot : Int = Integer.parseInt(docBinding.slot.text.toString())
            val cPhone : String = docBinding.cPhone.text.toString()
            val cEmail : String = docBinding.cEmail.text.toString()

            val doctor = DoctorModel(did = dId, dname = dName,specialization = specialization,degree = degree,rating = rating,fee = fee,profile_pic = profile_pic,about = about,experience = experience,caddress = cAddress,slot = slot,cphone = cPhone,cemail = cEmail)

            Log.d("@doctor", doctor.toString())
            addDoctor(doctor,depId)
        }

        docBinding.sDoc.setOnClickListener{
            startActivity(Intent(this,AllDocs::class.java).putExtra("dep",department).putExtra("id",depId))
        }

    }

    private fun addDoctor(doctor: DoctorModel, depId: String) {
            firebaseDB.collection("Department").document(depId).collection("Doctors").document(doctor.did).set(doctor).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"Doctor Added", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Doctor not added", Toast.LENGTH_SHORT).show()
                }
            }
    }
}