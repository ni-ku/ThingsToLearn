package de.niku.braincards.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var listItems: MutableList<T>

    constructor(listItems: MutableList<T>) : super() {
        this.listItems = listItems
    }

    constructor() {
        listItems = mutableListOf()
    }

    fun setListItem(listItems: MutableList<T>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        listItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false), viewType)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindViewHolder(holder, position, listItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }

    protected abstract fun getLayoutId(position: Int, obj: T) : Int
    protected abstract fun getViewHolder(view: View, viewType: Int) : RecyclerView.ViewHolder
    protected abstract fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: T)
}