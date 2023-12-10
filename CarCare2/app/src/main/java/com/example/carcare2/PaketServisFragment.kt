package com.example.carcare2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carcare2.engine.DataSender
import com.google.firebase.FirebaseApp
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaketServisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaketServisFragment : AppCompatActivity() {
    // TODO: Rename and change types of parameters
    private var jenis: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_paket_servis)
        FirebaseApp.initializeApp(this)
        val selectedDate = intent.getSerializableExtra("selectedDate") as Date
        val hour = intent.getSerializableExtra("hour") as Int

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val continueButton = findViewById<Button>(R.id.continueButton)
        radioGroup.setOnCheckedChangeListener {group,checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedAction = selectedRadioButton.text
            jenis = selectedAction.toString()
            
        }
        continueButton.setOnClickListener {
            FirebaseApp.initializeApp(this)
            // TODO: tulis fungsi buat masukin data ke server (write bufer)
            Toast.makeText(applicationContext,"data telah didaftarkan",Toast.LENGTH_SHORT).show()
            DataSender.dataSender(selectedDate,"this device",jenis,hour)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }


}