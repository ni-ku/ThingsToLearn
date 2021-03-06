package de.niku.ttl.view.fragment_card_set_detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.niku.ttl.BR

import de.niku.ttl.R
import de.niku.ttl.common.base.BaseFragment
import de.niku.ttl.common.resources.ResourceHelper
import de.niku.ttl.databinding.FragmentCardSetDetailBinding
import de.niku.ttl.view.fragment_card_set_detail.adapter.QuestionAdapter

class CardSetDetailFragment : BaseFragment<FragmentCardSetDetailBinding, CardSetDetailViewModel>() {

    private val args: CardSetDetailFragmentArgs by navArgs()
    private lateinit var questionAdapter: QuestionAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_card_set_detail
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        connectObservables()
        createLifecycleObservers()
        mViewModel.initFromStringRes()

        if (!this::questionAdapter.isInitialized) {
            questionAdapter = QuestionAdapter()
        }

        mDataBinding.rvQuestions.layoutManager = LinearLayoutManager(context)
        mDataBinding.rvQuestions.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mDataBinding.rvQuestions.setHasFixedSize(true)
        mDataBinding.rvQuestions.adapter = questionAdapter

        val cardSetId = args.cardSetId
        mViewModel.initById(cardSetId)
    }

    private fun createLifecycleObservers() {
        mViewModel.mResHelper = ResourceHelper(context)
        lifecycle.addObserver(mViewModel.mResHelper)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.card_set_detail_menu, menu)
        for (item in menu.iterator()) {
            if (item.icon != null) {
                item.icon.setTint(context!!.theme.resources.getColor(R.color.white, null))
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_view_cards -> {
                mViewModel.onViewCardsClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    private fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when (evt) {
                    is CardSetDetailEvents.NavigateToViewCards -> {
                        val action = CardSetDetailFragmentDirections.actionCardSetDetailToViewCards(evt.id, evt.title)
                        findNavController().navigate(action)
                    }
                    is CardSetDetailEvents.NavigateToLearnView -> {
                        val action = CardSetDetailFragmentDirections.actionCardSetDetailToLearnFragment(
                            evt.params.cardSetId,
                            evt.params.cardSetName,
                            evt.params.viceVersa,
                            evt.params.shuffle
                        )
                        findNavController().navigate(action)
                    }
                    is CardSetDetailEvents.NavigateToQuizView -> {
                        val action = CardSetDetailFragmentDirections.actionCardSetDetailToQuizFragment(
                            evt.params.cardSetId,
                            evt.params.cardSetName,
                            evt.params.viceVersa,
                            evt.params.shuffle
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        })

        mViewModel.vdQuestions.observe(this, Observer { list ->
            run {
                if (this::questionAdapter.isInitialized) {
                    questionAdapter.listItems = list.toMutableList()
                    questionAdapter.notifyDataSetChanged()
                }

        } })

    }

    private fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }

}
