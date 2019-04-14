package de.niku.braincards.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.braincards.view.fragment_card_sets.CardSetsFragment
import de.niku.braincards.view.fragment_card_sets.CardSetsModule

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [
        CardSetsModule::class
    ])
    abstract fun contributeCardSetsFragment(): CardSetsFragment
}