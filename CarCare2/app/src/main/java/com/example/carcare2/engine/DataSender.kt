package com.example.carcare2.engine

import com.google.firebase.database.FirebaseDatabase
import entity.Schedule
import java.util.Date
import com.google.firebase.FirebaseApp

object DataSender {

    private val database = FirebaseDatabase.getInstance()
    private val userReverence = database.getReference("users")
    fun dataSender(start: Date? = null, name:String? ="", layanan:String? = "",jam:Int){
        val schedule = Schedule(start,name,layanan,jam)
        val scheduleId =userReverence.push().key
        userReverence.child(scheduleId!!).setValue(schedule)
    }
    fun dataReader(){
        
    }

}