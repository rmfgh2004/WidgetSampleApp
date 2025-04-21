package com.example.widgetsampleapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ExpandableListView
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
        setContentView(R.layout.widget_nested_scroll_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setNestedScrollViewWidget()
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
        val dataset = arrayOf("January", "February", "March", "1", "1", "!", "1", "1", "1", "1", "2", "3", "4", "5")
        val sampleAdapter = SampleAdapter(dataset)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sampleAdapter
    }

    private fun setExpandableListViewWidget() {
        val parentList = mutableListOf<MenuTitle>(
            MenuTitle("Android", 0),
            MenuTitle("IOS", 1),
            MenuTitle("Server", 2),
        )
        val childList = mutableListOf<MutableList<MenuSpecific>>(
            mutableListOf(
                MenuSpecific("Kotlin", "use count", 20, R.drawable.ic_launcher_foreground),
                MenuSpecific("Java", "use count", 16, R.drawable.ic_launcher_foreground),
                MenuSpecific("Flutter", "use count", 20, R.drawable.ic_launcher_foreground),
            ),
            mutableListOf(
                MenuSpecific("Swift", "use count", 20, null),
                MenuSpecific("Object-C", "use count", 16, null),
                MenuSpecific("Flutter", "use count", 20, null),
            ),
            mutableListOf(
                MenuSpecific("Java", "use count", 16, R.drawable.ic_launcher_foreground),
            )
        )

        val adapter = ExpandableListAdapter(this, parentList, childList)
        val expandableListView : ExpandableListView = findViewById(R.id.expandableListView)
        expandableListView.setAdapter(adapter)
        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            setListViewHeight(parent, groupPosition)
            false
        }
    }

    private fun setListViewHeight(listView: ExpandableListView, group: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )

        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight

            if(listView.isGroupExpanded(i) && i != group || !listView.isGroupExpanded(i) && i == group) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem: View = listAdapter.getChildView(i, j, false, null, listView)
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
            val params = listView.layoutParams
            var height = (totalHeight + listView.dividerHeight * (listAdapter.groupCount - 1))
            if (height < 10) {
                height = 200
            }
            params.height = height
            listView.layoutParams = params
            listView.requestLayout()
        }
    }

    private fun setNestedScrollViewWidget() {
        setRecyclerViewWidget()
    }
}