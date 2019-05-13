package de.niku.braincards.model

import de.niku.braincards.data.db.entity.TblCardSet

data class CardSet(
    val id: Long?,
    val name: String,
    val cardCnt: Int,
    val cards: List<Card>,
    val questions: List<Question>
) {
    companion object {
        fun fromTblCardSet(item: TblCardSet): CardSet {
            var cs = CardSet(
                item.id,
                item.name,
                item.cardCnt,
                mutableListOf(),
                mutableListOf()
            )
            return cs
        }
    }
}