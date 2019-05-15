package de.niku.braincards.view.fragment_card_set_detail

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.niku.braincards.BR

import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.databinding.FragmentCardSetDetailBinding

class CardSetDetailFragment : BaseFragment<FragmentCardSetDetailBinding, CardSetDetailViewModel>() {

    val args: CardSetDetailFragmentArgs by navArgs()

    override fun getLayoutResId(): Int = R.layout.fragment_card_set_detail
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        connectObservables()

        val cardSetId = args.cardSetId
        mViewModel.initById(cardSetId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.card_set_detail_menu, menu)
        for (item in menu.iterator()) {
            if (item.getIcon() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    item.icon.setTint(
                            context!!.theme.resources.getColor(R.color.white, null)
                    )
                } else {
                    item.icon.setTint(
                        context!!.theme.resources.getColor(R.color.white)
                    )
                }
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

    fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when (evt) {
                    is CardSetDetailEvents.ShowStartLearningDialog -> {
                        Toast.makeText(context, "Todo: Start learning", Toast.LENGTH_SHORT).show()
                    }
                    is CardSetDetailEvents.NavigateToViewCards -> {
                        val action = CardSetDetailFragmentDirections.actionCardSetDetailToViewCards(evt.id, evt.title)
                        findNavController().navigate(action)
                    }
                }
            }
        })

    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }

}
