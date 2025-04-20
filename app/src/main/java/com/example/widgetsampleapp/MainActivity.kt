package com.example.widgetsampleapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.widget_button)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val textView = findViewById<AutoCompleteTextView>(R.id.tv_auto)
//        val countries: Array<String> = resources.getStringArray(R.array.countries_array)
//        ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries).also { adapter ->
//            textView.setAdapter(adapter);
//        }
    }
}