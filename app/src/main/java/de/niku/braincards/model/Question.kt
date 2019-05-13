package de.niku.braincards.model

data class Question(
    val id: Long?,
    val text: String
) {

    companion object {
        const val QUESTION_FRONT_IDENT = "{front}"
        const val QUESTION_BACK_IDENT = "{back}"
    }
}