package de.niku.braincards.view.dialog_start_learning

sealed class StartLearningEvents {
    class Done(val result: StartLearningResultData): StartLearningEvents()
    class Cancel(): StartLearningEvents()
}