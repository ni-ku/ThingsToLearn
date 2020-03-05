package de.niku.ttl.view.dialog_start_learning

import dagger.Module
import dagger.Provides

@Module
class StartLearningModule {

    @Provides
    fun provideStartLearningViewModel(): StartLearningViewModel {
        return StartLearningViewModel()
    }
}