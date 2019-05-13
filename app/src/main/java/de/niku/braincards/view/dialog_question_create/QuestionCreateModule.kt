package de.niku.braincards.view.dialog_question_create

import dagger.Module
import dagger.Provides

@Module
class QuestionCreateModule {

    @Provides
    fun provideQuestionCreateDialogViewModel(): QuestionCreateViewModel {
        return QuestionCreateViewModel()
    }
}