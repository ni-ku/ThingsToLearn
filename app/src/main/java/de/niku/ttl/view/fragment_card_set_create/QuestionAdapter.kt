package de.niku.ttl.view.fragment_card_set_create

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.common.adapter.BaseAdapter
import de.niku.ttl.model.Question

class QuestionAdapter(
    val viewModel: CardSetCreateViewModel?,
    list: MutableList<Question>
) : BaseAdapter<Question>(list) {

    constructor() : this(null, mutableListOf()) {

    }

    constructor(viewModel: CardSetCreateViewModel) : this(viewModel, mutableListOf()) {

    }

    override fun getLayoutId(position: Int, obj: Question): Int {
        return R.layout.list_item_question
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: Question) {

        var viewHolder: ViewHolder = (holder as ViewHolder)
        var question: Question = obj

        viewHolder.tvText.text = question.text
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
                            viewModel?.onQuestionEdit(position)
                            true
                        }
                        R.id.menu_delete -> {
                            viewModel?.onQuestionDelete(position)
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

        var tvText: TextView
        var btnMore: AppCompatImageButton

        constructor(view: View) : super(view) {
            tvText = view.findViewById(R.id.tv_question)
            btnMore = view.findViewById(R.id.btn_menu)
        }
    }
}