package com.example.widgetsampleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView

data class MenuTitle(val title: String, val index: Int)

data class MenuSpecific(val title: String, val detail: String?, val from: Int, val img: Int?)

class ExpandableListAdapter(
    private val context: Context,
    private val parents: MutableList<MenuTitle>,
    private val childList: MutableList<MutableList<MenuSpecific>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount() = parents.size

    override fun getChildrenCount(groupPosition: Int) = childList[groupPosition].size

    override fun getGroup(groupPosition: Int) = parents[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = childList[groupPosition][childPosition]

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_title, parent, false)
        view.findViewById<TextView>(R.id.tv_title).text = parents[groupPosition].title
        setArrow(view, isExpanded)

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_child, parent, false)
        val item = childList[groupPosition][childPosition] as MenuSpecific

        view.findViewById<TextView>(R.id.tv_title).text = item.title
        view.findViewById<TextView>(R.id.tv_detail).text = item.detail
        view.findViewById<TextView>(R.id.tv_num).text = item.from.toString()
        if (item.img == null) {
            view.findViewById<ImageView>(R.id.iv_child).visibility = View.GONE
        } else {
            view.findViewById<ImageView>(R.id.iv_child).setImageResource(item.img)
        }

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    private fun setArrow(view: View, isExpanded: Boolean) {
        if (isExpanded) {
            view.findViewById<ImageView>(R.id.iv_expand).setImageResource(R.drawable.baseline_arrow_upward_24)
        } else {
            view.findViewById<ImageView>(R.id.iv_expand).setImageResource(R.drawable.baseline_expand_more_24)
        }
    }
}