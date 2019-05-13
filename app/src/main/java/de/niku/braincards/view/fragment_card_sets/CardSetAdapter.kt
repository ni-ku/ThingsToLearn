package de.niku.braincards.view.fragment_card_sets

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.niku.braincards.R
import de.niku.braincards.common.adapter.BaseAdapter
import de.niku.braincards.model.CardSet

class CardSetAdapter(listItems: MutableList<CardSet>) : BaseAdapter<CardSet>(listItems) {

    override fun getLayoutId(position: Int, obj: CardSet): Int {
        return R.layout.list_item_card_set
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: CardSet) {

        var viewHolder: ViewHolder = (holder as ViewHolder)
        var cardSet: CardSet = obj

        viewHolder.title.text = cardSet.name
        viewHolder.cardCnt.text = cardSet.cardCnt.toString()
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var title: TextView
        var cardCnt: TextView

        constructor(view: View) : super(view) {
            title = view.findViewById(R.id.tv_title)
            cardCnt = view.findViewById(R.id.tv_cards_cnt)
        }



    }
}