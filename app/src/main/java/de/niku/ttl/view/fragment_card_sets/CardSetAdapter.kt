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
    private val viewModel: CardSetsViewModel?,
    val context: Context?,
    listItems: MutableList<CardSet>
) : BaseAdapter<CardSet>(listItems) {

    private var isSelectionMode: Boolean = false
    private var selectedPositions: MutableList<Int> = mutableListOf()
    private var actionMode: ActionMode? = null

    constructor(viewModel: CardSetsViewModel, context: Context?) : this(viewModel, context, mutableListOf())

    override fun getLayoutId(position: Int, obj: CardSet): Int {
        return R.layout.list_item_card_set
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: CardSet) {

        val viewHolder: ViewHolder = (holder as ViewHolder)
        val cardSet: CardSet = obj

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
            selectedItems.add(listItems[i])
        }
        return selectedItems
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
                            viewModel?.onCardSetEditClick(position)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete -> {
                            viewModel?.onCardSetDeleteClick(position)
                            return@setOnMenuItemClickListener true
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
                popupMenu.show()
            }
        }
    }

    private fun getItemSelectionListener(position: Int) : View.OnClickListener {
        return View.OnClickListener {
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

    private fun getItemClickListener(position: Int) : View.OnClickListener {
        return View.OnClickListener {
            run {
                viewModel?.onShowCardSetDetailClick(position)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tv_title)
        var cardCnt: TextView = view.findViewById(R.id.tv_cards_cnt)
        var btnMore: AppCompatImageButton = view.findViewById(R.id.btn_menu)
        var itemClickView: ConstraintLayout = view.findViewById(R.id.cl_item_view)
        var cbSelected: AppCompatCheckBox = view.findViewById(R.id.cb_selected)
    }
}