package de.niku.ttl.view.dialog_start_learning

sealed class StartLearningEvents {
    class Done(val result: StartLearningResultData): StartLearningEvents()
    class Cancel(): StartLearningEvents()
}