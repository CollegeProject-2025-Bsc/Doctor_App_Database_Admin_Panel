package com.example.doctor_app_database_admin_panel

data class DoctorModel(
    val did : String = "",
    val dname: String = "",
    val specialization : String = "",
    val degree : String = "",
    val rating : Int = 0,
    val fee : Int = 0,
    val profile_pic :String = "",
    val about : String = "",
    val experience : Int = 0,
    val caddress : String = "",
    val clatitude : Int = 0,
    val clongitude : Int = 0,
    val slot : Int = 0,
    val cphone : String = "",
    val cemail : String = "",
)
