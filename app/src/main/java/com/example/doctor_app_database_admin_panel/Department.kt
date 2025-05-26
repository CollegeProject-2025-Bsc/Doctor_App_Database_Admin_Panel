package com.example.doctor_app_database_admin_panel

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctor_app_database_admin_panel.databinding.ActivityDepartmentBinding
import com.google.firebase.firestore.FirebaseFirestore

class Department : AppCompatActivity() {
    private lateinit var depBinding: ActivityDepartmentBinding
    private lateinit var firebaseDb:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        depBinding = ActivityDepartmentBinding.inflate(layoutInflater)
        setContentView(depBinding.root)

        firebaseDb = FirebaseFirestore.getInstance()


        firebaseDb.collection("Department").addSnapshotListener{values,error ->

            var dep = arrayListOf<DepartmentModel>()
            val data = values?.toObjects(DepartmentModel::class.java)
            dep.addAll(data!!)
            depBinding.dep.adapter = DepAdapter(dep)
            depBinding.dep.layoutManager = LinearLayoutManager(this)

        }


        depBinding.depAdd.setOnClickListener{
            if (depBinding.depName.text.toString().isNotEmpty()) addDep(depBinding.depName.text.toString()) else Toast.makeText(applicationContext,"Enter Department Name",Toast.LENGTH_SHORT).show()
        }


    }

    private fun addDep(dName: String) {
        val id = firebaseDb.collection("Department").document().id

        val department = DepartmentModel(id,dName)
        firebaseDb.collection("Department").document(id).set(department).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(applicationContext,"Department Added",Toast.LENGTH_SHORT).show()
                depBinding.depName.setText("")
            }else{
                Toast.makeText(applicationContext,"failed Try again",Toast.LENGTH_SHORT).show()
            }
        }

    }
}