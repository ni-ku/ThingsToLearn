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
    private val viewModel: CardSetCreateViewModel?,
    list: MutableList<Card>
) : BaseAdapter<Card>(list) {

    constructor(viewModel: CardSetCreateViewModel) : this(viewModel, mutableListOf())

    override fun getLayoutId(position: Int, obj: Card): Int {
        return R.layout.list_item_card
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view = view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: Card) {
        val viewHolder = (holder as ViewHolder)
        val card: Card = obj
        viewHolder.run {
            tvFront.text = card.front
            tvBack.text = card.back
            btnMore.setOnClickListener(getMenuClickListener(position))
        }
    }

    private fun getMenuClickListener(position: Int) : View.OnClickListener {
        return View.OnClickListener { v ->
            run {
                val popupMenu = PopupMenu(v.context, v)
                val menuInflater = popupMenu.menuInflater
                menuInflater.inflate(R.menu.list_item_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_edit -> {
                            viewModel?.onCardEdit(position)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete -> {
                            viewModel?.onCardDelete(position)
                            return@setOnMenuItemClickListener true
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
                popupMenu.show()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvFront: TextView = view.findViewById(R.id.tv_front)
        var tvBack: TextView = view.findViewById(R.id.tv_back)
        var btnMore: AppCompatImageButton = view.findViewById(R.id.btn_menu)
    }
}