package de.niku.braincards.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.braincards.view.fragment_card_create.CardCreateModule
import de.niku.braincards.view.fragment_card_set_create.CardSetCreateFragment
import de.niku.braincards.view.fragment_card_set_create.CardSetCreateModule
import de.niku.braincards.view.fragment_card_set_detail.CardSetDetailFragment
import de.niku.braincards.view.fragment_card_set_detail.CardSetDetailModule
import de.niku.braincards.view.fragment_card_sets.CardSetsFragment
import de.niku.braincards.view.fragment_card_sets.CardSetsModule

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [
        CardSetsModule::class
    ])
    abstract fun contributeCardSetsFragment(): CardSetsFragment

    @ContributesAndroidInjector(modules = [
        CardSetCreateModule::class
    ])
    abstract fun contributeCardSetCreateFragment(): CardSetCreateFragment

    @ContributesAndroidInjector(modules = [
        CardSetDetailModule::class
    ])
    abstract fun contributeCardSetDetailFragment(): CardSetDetailFragment

}