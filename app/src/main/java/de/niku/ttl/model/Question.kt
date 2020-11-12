package de.niku.ttl.model

class Question(
    val id: Long?,
    val text: String
) {
    companion object {
        const val QUESTION_FRONT_PLACEHOLDER = "{front}"
        const val QUESTION_BACK_PLACEHOLDER = "{back}"
    }
}