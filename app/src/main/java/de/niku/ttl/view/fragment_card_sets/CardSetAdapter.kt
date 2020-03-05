package de.niku.ttl.view.fragment_card_sets

import android.content.Context
import android.view.ActionMode
import android.view.View
import android.widget.TextView

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.common.adapter.BaseAdapter
import de.niku.ttl.model.CardSet

class CardSetAdapter(
    val viewModel: CardSetsViewModel?,
    val context: Context?,
    listItems: MutableList<CardSet>
) : BaseAdapter<CardSet>(listItems) {

    var isSelectionMode: Boolean = false
    var selectedPositions: MutableList<Int> = mutableListOf()
    var actionMode: ActionMode? = null

    constructor(viewModel: CardSetsViewModel, context: Context?) : this(viewModel, context, mutableListOf())

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
        if (isSelectionMode) {
            viewHolder.btnMore.visibility = View.GONE
            viewHolder.cbSelected.visibility = View.VISIBLE

            viewHolder.cbSelected.isChecked = selectedPositions.contains(position)

            viewHolder.itemClickView.setOnClickListener(getItemSelectionListener(position))

        } else {
            viewHolder.btnMore.visibility = View.VISIBLE
            viewHolder.cbSelected.visibility = View.GONE

            viewHolder.btnMore.setOnClickListener(getMenuClickListener(position))
            viewHolder.itemClickView.setOnClickListener(getItemClickListener(position))
        }
    }

    fun startSelectionMode(am: ActionMode) {
        isSelectionMode = true
        selectedPositions = mutableListOf()
        actionMode = am
        notifyDataSetChanged()
    }

    fun stopSelectionMode() {
        isSelectionMode = false
        selectedPositions.clear()
        actionMode = null
        notifyDataSetChanged()
    }

    fun selectAll() {
        selectedPositions.clear()
        for (i in 0 until itemCount) {
            selectedPositions.add(i)
        }
        actionMode!!.title = context!!.getString(R.string.n_selected_items, selectedPositions.size)
        notifyDataSetChanged()
    }

    fun getSelectedItems() : List<CardSet> {
        val selectedItems: MutableList<CardSet> = mutableListOf()
        for (i in selectedPositions) {
            selectedItems.add(listItems.get(i))
        }
        return selectedItems
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

    fun getItemSelectionListener(position: Int) : View.OnClickListener {
        return View.OnClickListener { v ->
            run {
                if (selectedPositions.contains(position)) {
                    selectedPositions.remove(position)
                } else {
                    selectedPositions.add(position)
                }
                actionMode!!.title = context!!.getString(R.string.n_selected_items, selectedPositions.size)
                notifyItemChanged(position)
            }
        }
    }

    fun getItemClickListener(position: Int) : View.OnClickListener {
        return View.OnClickListener { v ->
            run {
                viewModel?.onShowCardSetDetailClick(position)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var title: TextView
        var cardCnt: TextView
        var btnMore: AppCompatImageButton
        var itemClickView: ConstraintLayout
        var cbSelected: AppCompatCheckBox

        constructor(view: View) : super(view) {
            title = view.findViewById(R.id.tv_title)
            cardCnt = view.findViewById(R.id.tv_cards_cnt)
            btnMore = view.findViewById(R.id.btn_menu)
            itemClickView = view.findViewById(R.id.cl_item_view)
            cbSelected = view.findViewById(R.id.cb_selected)
        }
    }
}