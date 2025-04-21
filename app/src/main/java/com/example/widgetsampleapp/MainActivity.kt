package com.example.widgetsampleapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.widget_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListViewWidget()

    }

    private fun setTextWidget() {
        val textView = findViewById<AutoCompleteTextView>(R.id.tv_auto)
        val countries: Array<String> = resources.getStringArray(R.array.countries_array)
        ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries).also { adapter ->
            textView.setAdapter(adapter);
        }
    }

    private fun setSelectWidget() {
        val spinner = findViewById<Spinner>(R.id.sp_country)
        ArrayAdapter.createFromResource(
            this,
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setPickerWidget() {
        findViewById<Button>(R.id.btn_time).setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }

        findViewById<Button>(R.id.btn_date).setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "datePicker")
        }

        val numberPicker = findViewById<NumberPicker>(R.id.pk_number)
        numberPicker.maxValue = 100
        numberPicker.minValue = 0
        numberPicker.value = 5
    }

    private fun setListViewWidget() {
        val listView = findViewById<ListView>(R.id.listView)
        val itemList = mutableListOf<Int>()
        for (i in 1..100) {
            itemList.add(i)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter
    }

    private fun setRecyclerViewWidget() {
        val dataset = arrayOf("January", "February", "March")
        val sampleAdapter = SampleAdapter(dataset)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sampleAdapter
    }
}