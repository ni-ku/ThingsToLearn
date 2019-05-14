package de.niku.braincards.view.fragment_card_set_create

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import de.niku.braincards.R
import de.niku.braincards.BR
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.common.resources.ResourceHelper
import de.niku.braincards.databinding.FragmentCardSetCreateBinding
import de.niku.braincards.view.dialog_question_create.QuestionCreateDialog
import de.niku.braincards.view.dialog_question_create.QuestionCreateResult
import de.niku.braincards.view.dialog_question_create.QuestionEditResultData
import de.niku.braincards.view.fragment_card_create.*

class CardSetCreateFragment : BaseFragment<FragmentCardSetCreateBinding, CardSetCreateViewModel>(),
    ResultReceiver,
    de.niku.braincards.view.dialog_question_create.ResultReceiver {

    val args: CardSetCreateFragmentArgs by navArgs()

    lateinit var cardsAdapter: CardAdapter
    lateinit var questionsAdapter: QuestionAdapter

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
        mDataBinding.rvCards.adapter = cardsAdapter

        mDataBinding.rvQuestions.layoutManager = LinearLayoutManager(context)
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

    fun createLifecycleObservers() {
        mViewModel.mResHelper = ResourceHelper(context)
        lifecycle.addObserver(mViewModel.mResHelper)
    }

    fun connectObservables() {

        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when(evt) {
                    is CardSetCreateEvents.ShowCreateCardDialog -> {
                        var dialog = CardCreateDialog(this)
                        dialog.show(fragmentManager!!, CardCreateDialog.TAG)
                    }
                    is CardSetCreateEvents.ShowCreateQuestionDialog -> {
                        var dialog = QuestionCreateDialog(this)
                        dialog.show(fragmentManager!!, "tag")
                    }
                    is CardSetCreateEvents.CardSetCreateSuccess -> {
                        findNavController().navigateUp()
                    }
                    is CardSetCreateEvents.ShowEditCardDialog -> {
                        var dialog = CardCreateDialog(this, evt.params)
                        dialog.show(fragmentManager!!, CardCreateDialog.TAG)
                    }
                    is CardSetCreateEvents.ShowEditQuestionDialog -> {
                        var dialog = QuestionCreateDialog(this, evt.params)
                        dialog.show(fragmentManager!!, "tag")
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

    fun clearObservables() {
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

    override fun onEditQuestion(result: QuestionEditResultData) {
        mViewModel.onQuestionEdited(result.position, result.question)
    }
}
