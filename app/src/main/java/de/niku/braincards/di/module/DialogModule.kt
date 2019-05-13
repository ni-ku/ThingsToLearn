package de.niku.braincards.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.braincards.view.dialog_question_create.QuestionCreateDialog
import de.niku.braincards.view.dialog_question_create.QuestionCreateModule
import de.niku.braincards.view.fragment_card_create.CardCreateDialog
import de.niku.braincards.view.fragment_card_create.CardCreateModule

@Module
abstract class DialogModule {

    @ContributesAndroidInjector(modules = [
        QuestionCreateModule::class
    ])
    abstract fun contributeQuestionCreateDialog(): QuestionCreateDialog

    @ContributesAndroidInjector(modules = [
        CardCreateModule::class
    ])
    abstract fun contributeCardCreateDialog(): CardCreateDialog
}