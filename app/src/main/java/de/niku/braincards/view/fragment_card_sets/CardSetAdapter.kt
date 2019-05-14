package de.niku.braincards.view.fragment_card_sets

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import de.niku.braincards.R
import de.niku.braincards.common.adapter.BaseAdapter
import de.niku.braincards.model.CardSet

class CardSetAdapter(
    val viewModel: CardSetsViewModel?,
    listItems: MutableList<CardSet>
) : BaseAdapter<CardSet>(listItems) {

    constructor(viewModel: CardSetsViewModel) : this(viewModel, mutableListOf())

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
        viewHolder.btnMore.setOnClickListener(getMenuClickListener(position))
    }

    fun getMenuClickListener(position: Int) : View.OnClickListener {
        return View.OnClickListener { v ->
            run {
                val popupMenu = PopupMenu(v.context, v)
                val menuInflater = popupMenu.menuInflater
                menuInflater.inflate(R.menu.list_item_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_edit -> {
                            viewModel?.onCardSetEditClick(position)
                            true
                        }
                        R.id.menu_delete -> {
                            viewModel?.onCardSetDeleteClick(position)
                            true
                        }
                    }
                    false
                }

                popupMenu.show()
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var title: TextView
        var cardCnt: TextView
        var btnMore: AppCompatImageButton

        constructor(view: View) : super(view) {
            title = view.findViewById(R.id.tv_title)
            cardCnt = view.findViewById(R.id.tv_cards_cnt)
            btnMore = view.findViewById(R.id.btn_menu)
        }
    }
}