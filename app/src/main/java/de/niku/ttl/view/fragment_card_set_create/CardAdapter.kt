package de.niku.ttl.view.fragment_card_set_create

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.common.adapter.BaseAdapter
import de.niku.ttl.model.Card

class CardAdapter(
    val viewModel: CardSetCreateViewModel?,
    list: MutableList<Card>
) : BaseAdapter<Card>(list) {

    constructor(viewModel: CardSetCreateViewModel) : this(viewModel, mutableListOf())

    override fun getLayoutId(position: Int, obj: Card): Int {
        return R.layout.list_item_card
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: Card) {

        var viewHolder: ViewHolder = (holder as ViewHolder)
        var card: Card = obj

        viewHolder.tvFront.text = card.front
        viewHolder.tvBack.text = card.back
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
                            viewModel?.onCardEdit(position)
                            true
                        }
                        R.id.menu_delete -> {
                            viewModel?.onCardDelete(position)
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

        var tvFront: TextView
        var tvBack: TextView
        var btnMore: AppCompatImageButton

        constructor(view: View) : super(view) {
            tvFront = view.findViewById(R.id.tv_front)
            tvBack = view.findViewById(R.id.tv_back)
            btnMore = view.findViewById(R.id.btn_menu)
        }
    }
}