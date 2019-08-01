package de.niku.braincards.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.braincards.view.fragment_card_set_create.CardSetCreateFragment
import de.niku.braincards.view.fragment_card_set_create.CardSetCreateModule
import de.niku.braincards.view.fragment_card_set_detail.CardSetDetailFragment
import de.niku.braincards.view.fragment_card_set_detail.CardSetDetailModule
import de.niku.braincards.view.fragment_card_set_learn.CardSetLearnFragment
import de.niku.braincards.view.fragment_card_set_learn.CardSetLearnModule
import de.niku.braincards.view.fragment_card_set_view_cards.ViewCardsFragment
import de.niku.braincards.view.fragment_card_set_view_cards.ViewCardsModule
import de.niku.braincards.view.fragment_card_sets.CardSetsFragment
import de.niku.braincards.view.fragment_card_sets.CardSetsModule
import de.niku.braincards.view.fragment_quiz.QuizFragment
import de.niku.braincards.view.fragment_quiz.QuizModule
import de.niku.braincards.view.fragment_settings.SettingsFragment
import de.niku.braincards.view.fragment_settings.SettingsModule

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

    @ContributesAndroidInjector(modules = [
        ViewCardsModule::class
    ])
    abstract fun contributeViewCardsFragment(): ViewCardsFragment

    @ContributesAndroidInjector(modules = [
        CardSetLearnModule::class
    ])
    abstract fun contributeCardSetLearnFragment(): CardSetLearnFragment

    @ContributesAndroidInjector(modules = [
        QuizModule::class
    ])
    abstract fun contributeQuizFragment(): QuizFragment

    @ContributesAndroidInjector(modules = [
        SettingsModule::class
    ])
    abstract fun contributeSettingsFragment(): SettingsFragment

}