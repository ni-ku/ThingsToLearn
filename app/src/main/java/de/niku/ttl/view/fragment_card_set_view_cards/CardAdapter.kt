package de.niku.ttl.view.fragment_card_set_view_cards

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.common.adapter.BaseAdapter
import de.niku.ttl.model.Card

class CardAdapter(
    val viewModel: ViewCardsViewModel?,
    list: MutableList<Card>
) : BaseAdapter<Card>(list) {

    constructor(viewModel: ViewCardsViewModel) : this(viewModel, mutableListOf())

    override fun getLayoutId(position: Int, obj: Card): Int {
        return R.layout.list_item_card_plain
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: Card) {

        var viewHolder: ViewHolder = (holder as ViewHolder)
        var card: Card = obj

        viewHolder.tvFront.text = card.front
        viewHolder.tvBack.text = card.back
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var tvFront: TextView
        var tvBack: TextView

        constructor(view: View) : super(view) {
            tvFront = view.findViewById(R.id.tv_front)
            tvBack = view.findViewById(R.id.tv_back)
        }
    }
}
