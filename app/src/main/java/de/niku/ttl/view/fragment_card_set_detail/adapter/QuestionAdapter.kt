package de.niku.ttl.view.fragment_card_set_detail.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.common.adapter.BaseAdapter
import de.niku.ttl.model.Question

class QuestionAdapter(
    list: MutableList<Question>
) : BaseAdapter<Question>(list) {

    constructor() : this(mutableListOf()) {

    }

    override fun getLayoutId(position: Int, obj: Question): Int {
        return R.layout.list_item_question_plain
    }

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int, obj: Question) {
        var viewHolder: ViewHolder = (holder as ViewHolder)
        var question: Question = obj

        viewHolder.tvText.text = question.text
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var tvText: TextView

        constructor(view: View) : super(view) {
            tvText = view.findViewById(R.id.tv_question)
        }
    }
}
