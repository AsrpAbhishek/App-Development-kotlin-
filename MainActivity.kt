package asrp.example.dobcalc

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView?=null
    private var tvAgeInMutes: TextView?=null

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Hey! What is Your Age?"
        //sets the Title inside the app by replacing the app name

        val btndatepicker: Button = findViewById(R.id.btndatepicker)

        tvSelectedDate =findViewById((R.id.tvSelectedDate))
        tvAgeInMutes=findViewById(R.id.tvAgeInMinutes)

        btndatepicker.setOnClickListener {
            clickDatepicker()
        }
    }
    private fun clickDatepicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "Year was $selectedYear, Month was ${selectedMonth + 1}, " +
                    "Day of the Month was $selectedDayOfMonth",
                Toast.LENGTH_LONG).show()

            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                val selectedDateInMillis = theDate.time
                val currentDate = System.currentTimeMillis()
                val differenceInMillis = currentDate - selectedDateInMillis

                val days = differenceInMillis / (1000 * 60 * 60 * 24)
                val remainingHoursInMillis = (differenceInMillis % (1000 * 60 * 60 * 24)).toInt()
                val hours = remainingHoursInMillis / (1000 * 60 * 60)
                val remainingMinutesInMillis = (remainingHoursInMillis % (1000 * 60 * 60)).toInt()
                val minutes = remainingMinutesInMillis / (1000 * 60)
                val seconds = (remainingMinutesInMillis % (1000 * 60)) / 1000

                val dayText = if (days == 1L) "day" else "days"
                val hourText = if (hours.toLong() == 1L) "hour" else "hours"
                val minuteText = if (minutes.toLong() == 1L) "minute" else "minutes"
                val secondText = if (seconds.toLong() == 1L) "second" else "seconds"

                val ageText = "$days $dayText, $hours $hourText, $minutes $minuteText, and $seconds $secondText"
                tvAgeInMutes?.text = ageText
            }
        },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }


}
