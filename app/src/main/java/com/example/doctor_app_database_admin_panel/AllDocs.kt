package com.example.doctor_app_database_admin_panel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctor_app_database_admin_panel.databinding.ActivityAllDocsBinding
import com.google.firebase.firestore.FirebaseFirestore

class AllDocs : AppCompatActivity() {
    lateinit var allDocsBinding: ActivityAllDocsBinding
    lateinit var firebaseDB: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allDocsBinding = ActivityAllDocsBinding.inflate(layoutInflater)
        setContentView(allDocsBinding.root)

        val department = intent.getStringExtra("dep").toString()
        val depId = intent.getStringExtra("id").toString()

        allDocsBinding.doctor.text = department
        firebaseDB = FirebaseFirestore.getInstance()

        firebaseDB.collection("Department").document(depId)
            .collection("Doctors").addSnapshotListener{ value, _ ->
                var doc = arrayListOf<DoctorModel>()

                val data = value?.toObjects(DoctorModel::class.java)
                Log.d("@data", "onCreate: $data \n $depId")
                doc.addAll(data!!)
                allDocsBinding.allRec.layoutManager = LinearLayoutManager(this)
                allDocsBinding.allRec.adapter = AllDocAdapter(doc,firebaseDB,depId)
        }



        allDocsBinding.back.setOnClickListener {
            finish()
        }


    }
}