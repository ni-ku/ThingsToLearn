package de.niku.ttl.model

import de.niku.ttl.data.db.entity.TblCardSet

data class CardSet(
    val id: Long?,
    val name: String,
    val cardCnt: Int,
    val cards: List<Card>,
    val stats: List<LearnStat>,
    var started: Int,
    var completed: Int
) {
    companion object {
        fun fromTblCardSet(item: TblCardSet): CardSet {
            return CardSet(
                item.id,
                item.name,
                item.cardCnt,
                mutableListOf(),
                mutableListOf(),
                0,
                0
            )
        }
    }
}