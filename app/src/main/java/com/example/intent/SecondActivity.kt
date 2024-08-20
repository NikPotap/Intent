package com.example.intent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var inputFirstET: EditText
    private lateinit var inputSecondET: EditText
    private lateinit var summBTN: Button
    private lateinit var diffBTN: Button
    private lateinit var compBTN: Button
    private lateinit var divBTN: Button
    private lateinit var returnToFirstBTN: Button
    private var returnedResult = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inputFirstET = findViewById(R.id.inputFirstET)
        inputSecondET = findViewById(R.id.inputSecondET)
        summBTN = findViewById(R.id.summBTN)
        diffBTN = findViewById(R.id.diffBTN)
        compBTN = findViewById(R.id.compBTN)
        divBTN = findViewById(R.id.divBTN)
        returnToFirstBTN = findViewById(R.id.returnToFirstBTN)
        summBTN.setOnClickListener(this)
        diffBTN.setOnClickListener(this)
        compBTN.setOnClickListener(this)
        divBTN.setOnClickListener(this)
        returnToFirstBTN.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val imm =
            p0?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(p0?.windowToken, 0)
        if (p0?.id == R.id.returnToFirstBTN) {
            val intent = Intent()
            intent.putExtra("res", returnedResult)
            if (returnedResult != "") setResult(RESULT_OK, intent)
            else setResult(RESULT_CANCELED, intent)
            finish()
        }
        if (inputFirstET.text.isEmpty() || inputSecondET.text.isEmpty()) {
            Snackbar.make(p0, resources.getString(R.string.error_null), Snackbar.LENGTH_SHORT)
                .show()
            return
        }
        if (inputFirstET.text.toString().toDoubleOrNull() == null || inputSecondET.text.toString()
                .toDoubleOrNull() == null
        ) {
            Snackbar.make(p0, resources.getString(R.string.error_not_digit), Snackbar.LENGTH_SHORT)
                .show()
            return
        }
        var result = 0.0
        var isOK = false
        var input1 = inputFirstET.text.toString().toDouble()
        var input2 = inputSecondET.text.toString().toDouble()
        when (p0?.id) {
            R.id.summBTN -> {
                result = input1 + input2
                isOK = true
            }

            R.id.diffBTN -> {
                result = input1 - input2
                isOK = true
            }

            R.id.compBTN -> {
                result = input1 * input2
                isOK = true
            }

            R.id.divBTN -> {
                if (input2 == 0.0) {
                    Snackbar.make(
                        p0,
                        resources.getString(R.string.error_div_by_zero),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    return
                } else {
                    result = input1 / input2
                    isOK = true
                }
            }
        }
        if (isOK) returnedResult = result.toString() else returnedResult = ""
    }
}