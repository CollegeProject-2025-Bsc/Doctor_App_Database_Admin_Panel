package com.example.doctor_app_database_admin_panel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doctor_app_database_admin_panel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.department.setOnClickListener{
            startActivity(Intent(this,Department::class.java))
        }

        mainBinding.appointment.setOnClickListener{
            Toast.makeText(applicationContext, "currently unavailable", Toast.LENGTH_SHORT).show()
        }
        mainBinding.user.setOnClickListener{
            Toast.makeText(applicationContext, "currently unavailable", Toast.LENGTH_SHORT).show()
        }



    }
}