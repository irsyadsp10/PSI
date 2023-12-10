package com.example.carcare2

import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.lang.Exception
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates



class Servis : AppCompatActivity(){
    private lateinit var selectedDate: Date
    private var tanggalButton: Button? = null // Reference to the button
    private var continueButton:Button? = null
    private var hour = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_servis)
        val fragmenService = findViewById<FrameLayout>(R.id.fragmentContainer2)
        fragmenService.bringToFront()


        // Retrieve the selected date from the Intent
        try {
            selectedDate = intent.getSerializableExtra("selectedDate") as Date
            hour = intent.getSerializableExtra("hour") as Int
            val dateFormat = DateFormat.getDateInstance()
            val selectedDateString = dateFormat.format(selectedDate)
            updateButtonText(selectedDateString)
        }catch (e:Exception){

        }


        tanggalButton =findViewById(R.id.tanggalButton) // Initialize the button reference
        tanggalButton?.setOnClickListener{ // Show the DatePickerFragment
            replaceFragment(ServiceFragment())
        }
        continueButton = findViewById(R.id.paketServis)
        continueButton?.setOnClickListener(View.OnClickListener{
            try {
                val intent=Intent(this,PaketServisFragment::class.java)
                intent.putExtra("selectedDate",selectedDate)
                intent.putExtra("hour",hour)
                startActivity(intent)
            }catch(e:Exception){
                Toast.makeText(applicationContext,"silahkan masukkan data",Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the new one
        transaction.replace(R.id.fragmentContainer2, fragment)

        // Add the transaction to the back stack (optional)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }

    // Method to update the button text
    private fun updateButtonText(text: String) {
        tanggalButton?.text = "Chosen Date: $text" ?: "Default Value"
    }

}
