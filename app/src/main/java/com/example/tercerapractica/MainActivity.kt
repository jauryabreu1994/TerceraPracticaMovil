package com.example.tercerapractica

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var txtDate : EditText
    private lateinit var constraintLayout : ConstraintLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtUserName = findViewById<View>(R.id.editTextUserName) as EditText
        constraintLayout = findViewById<View>(R.id.constraintLayout) as ConstraintLayout
        txtDate = findViewById<View>(R.id.editTextDate) as EditText
        txtDate.setFocusable(false);
        txtDate.setClickable(true);

        val btnEvaluateDate = findViewById<View>(R.id.buttonEvaluateDate) as Button
        val calendar = Calendar.getInstance()

        txtDate.setOnClickListener()
        {
            v -> val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)

            val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day_of_month

                updateTextField(calendar)
            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            calendar.add(Calendar.YEAR, -150)
            dialog.datePicker.minDate = calendar.timeInMillis
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }

        btnEvaluateDate.setOnClickListener {

            val imagePlus18 = findViewById<View>(R.id.imagePlus18) as ImageView
            val imageMinus18 = findViewById<View>(R.id.imageMinus18) as ImageView

            val dateValue = SimpleDateFormat("dd/MM/yyyy").parse(txtDate.text.toString()).getTime()
            val dateCurrent = Date(System.currentTimeMillis()).getTime()
            val diff = dateCurrent - dateValue;
            val year = ((((((diff/1000)/60)/60)/24)-4)/365)

            if(year >= 18){
                imagePlus18.isVisible = true;
                imageMinus18.isVisible = false;
            }
            else{
                imagePlus18.isVisible = false;
                imageMinus18.isVisible = true;
            }


            Snackbar.make(constraintLayout,
                "La edad de "+ txtUserName.text +" es: " + year.toString() + " años",
                LENGTH_INDEFINITE).show();

        }
    }

    private fun updateTextField(myCalendar: Calendar) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        txtDate.setText(sdf.format(myCalendar.time));
    }

}