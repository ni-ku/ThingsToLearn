package de.niku.ttl.view.fragment_card_set_create

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.niku.ttl.R
import de.niku.ttl.BR
import de.niku.ttl.common.base.BaseFragment
import de.niku.ttl.common.resources.ResourceHelper
import de.niku.ttl.databinding.FragmentCardSetCreateBinding
import de.niku.ttl.view.dialog_question_create.QuestionCreateDialog
import de.niku.ttl.view.dialog_question_create.QuestionCreateResult
import de.niku.ttl.view.dialog_question_create.QuestionEditParams
import de.niku.ttl.view.fragment_card_create.*

class CardSetCreateFragment : BaseFragment<FragmentCardSetCreateBinding, CardSetCreateViewModel>(),
    ResultReceiver, de.niku.ttl.view.dialog_question_create.ResultReceiver {

    private val args: CardSetCreateFragmentArgs by navArgs()
    private lateinit var cardsAdapter: CardAdapter
    private lateinit var questionsAdapter: QuestionAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_card_set_create
    override fun getViewBindingId(): Int = BR.viewmodel

    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()
        createLifecycleObservers()

        if (!this::cardsAdapter.isInitialized) {
            cardsAdapter = CardAdapter(mViewModel)
        }
        if (!this::questionsAdapter.isInitialized) {
            questionsAdapter = QuestionAdapter(mViewModel)
        }

        mDataBinding.rvCards.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvCards.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mDataBinding.rvCards.adapter = cardsAdapter

        mDataBinding.rvQuestions.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvQuestions.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mDataBinding.rvQuestions.adapter = questionsAdapter

        val idToEdit = args.cardSetId
        if (idToEdit != 0L) {
            mViewModel.fetchCardSetById(idToEdit)
        }
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    private fun createLifecycleObservers() {
        mViewModel.mResHelper = ResourceHelper(context)
        lifecycle.addObserver(mViewModel.mResHelper)
    }

    private fun connectObservables() {

        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when(evt) {
                    is CardSetCreateEvents.ShowCreateCardDialog -> {
                        val dialog = CardCreateDialog(this)
                        dialog.show(fragmentManager!!, CardCreateDialog.TAG)
                    }
                    is CardSetCreateEvents.CardSetCreateSuccess -> {
                        findNavController().popBackStack()
                    }
                    is CardSetCreateEvents.ShowEditCardDialog -> {
                        val dialog = CardCreateDialog(this, evt.params)
                        dialog.show(fragmentManager!!, CardCreateDialog.TAG)
                    }
                    is CardSetCreateEvents.ShowCreateQuestionDialog -> {
                        val dialog = QuestionCreateDialog(this)
                        dialog.show(fragmentManager!!, QuestionCreateDialog.TAG)
                    }
                    is CardSetCreateEvents.ShowEditQuestionDialog -> {
                        val dialog = QuestionCreateDialog(this, evt.params)
                        dialog.show(fragmentManager!!, QuestionCreateDialog.TAG)
                    }
                }
            }
        })

        mViewModel.cards.observe(this, Observer { list ->
            run {
                if (this::cardsAdapter.isInitialized) {
                    cardsAdapter.listItems = list
                    cardsAdapter.notifyDataSetChanged()
                }
            }
        })

        mViewModel.questions.observe(this, Observer { list ->
            run {
                if (this::questionsAdapter.isInitialized) {
                    questionsAdapter.listItems = list
                    questionsAdapter.notifyDataSetChanged()
                }
            }
        })

    }

    private fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }

    override fun onCreateCard(result: CardCreateResultData) {
        mViewModel.addCard(result.frontValue, result.backValue)
    }

    override fun onEditCard(result: CardEditResultData) {
        mViewModel.onCardEdited(result.position, result.frontValue, result.backValue)
    }

    override fun onCreateQuestion(result: QuestionCreateResult) {
        mViewModel.addQuestion(result.qestion)
    }

    override fun onEditQuestion(result: QuestionEditParams) {
        mViewModel.onQuestionEdited(result.position, result.question)
    }
}
