package de.niku.ttl.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.niku.ttl.view.dialog_question_create.QuestionCreateDialog
import de.niku.ttl.view.dialog_question_create.QuestionCreateModule
import de.niku.ttl.view.dialog_start_learning.StartLearningDialog
import de.niku.ttl.view.dialog_start_learning.StartLearningModule
import de.niku.ttl.view.fragment_card_create.CardCreateDialog
import de.niku.ttl.view.fragment_card_create.CardCreateModule

@Module
abstract class DialogModule {

    @ContributesAndroidInjector(modules = [
        CardCreateModule::class
    ])
    abstract fun contributeCardCreateDialog(): CardCreateDialog

    @ContributesAndroidInjector(modules = [
        QuestionCreateModule::class
    ])
    abstract fun contributeQuestionCreateDialog(): QuestionCreateDialog

    @ContributesAndroidInjector(modules = [
        StartLearningModule::class
    ])
    abstract fun contributeStartLearningDialog(): StartLearningDialog
}