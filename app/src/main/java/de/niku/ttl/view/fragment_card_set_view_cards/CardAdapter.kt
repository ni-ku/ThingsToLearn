package de.niku.ttl.view.fragment_card_set_view_cards

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.common.adapter.BaseAdapter
import de.niku.ttl.model.Card

class CardAdapter(
    list: MutableList<Card>
) : BaseAdapter<Card>(list) {

    constructor() : this(mutableListOf())

    override fun getLayoutId(position: Int, obj: Card): Int {
        return R.layout.list_item_card_plain
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: Card) {

        val viewHolder: ViewHolder = (holder as ViewHolder)
        val card: Card = obj

        viewHolder.run {
            tvFront.text = card.front
            tvBack.text = card.back
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvFront: TextView = view.findViewById(R.id.tv_front)
        var tvBack: TextView = view.findViewById(R.id.tv_back)
    }
}
