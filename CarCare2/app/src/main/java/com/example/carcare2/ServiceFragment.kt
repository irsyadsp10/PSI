package com.example.carcare2

import android.annotation.SuppressLint
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.carcare.ui.datePicker.DatePickerFragment
import java.text.DateFormat
import java.util.Calendar
import java.util.Date


class ServiceFragment : Fragment(), OnDateSetListener {
    private var selectedDate: Date? = null
    private var openDatePickerButton: Button? = null // Reference to the button
    private var hour :Int?= null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service, container, false)
        val continueButton = view.findViewById<Button>(R.id.continueButton)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        continueButton.setOnClickListener {
            // Create an Intent to send the date to the next activity
            val intent = Intent(requireContext(), Servis::class.java)
            intent.putExtra("selectedDate", selectedDate)
            intent.putExtra("hour",hour)

            // Start the next activity
            startActivity(intent)
        }

        openDatePickerButton =
            view.findViewById(R.id.openDatePickerButton) // Initialize the button reference
        openDatePickerButton?.setOnClickListener(View.OnClickListener { // Show the DatePickerFragment
            val datePickerFragment: DialogFragment = DatePickerFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            datePickerFragment.show(fragmentManager, "datePicker")
        })

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Handle the selected RadioButton
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            val selectedOption = selectedRadioButton.text
            hour=when(selectedOption.toString()){
                "8:00" -> 8
                "9:00" -> 9
                "10:00" -> 10
                "11:00" -> 11
                "14:00" -> 14
                "15:00" -> 15
                else -> 0
            }
        }

        return view
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        // Handle the selected date
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        selectedDate = calendar.time // Save the selected date to the variable

        // Format the selected date as a string
        val dateFormat = DateFormat.getDateInstance()
        val selectedDateString = dateFormat.format(selectedDate)

        // Display the selected date (you can customize this part)
        Toast.makeText(requireContext(), "Selected Date: $selectedDateString", Toast.LENGTH_SHORT).show()

        // Update the button text with the chosen date
        updateButtonText(selectedDateString)
    }

    // Method to update the button text
    private fun updateButtonText(text: String) {
        openDatePickerButton?.text = "Chosen Date: $text" ?: "Default Value"
    }
    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        return calendar.time
    }

}