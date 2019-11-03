package de.niku.ttl.model

import de.niku.ttl.data.db.entity.TblCardSet

data class CardSet(
    val id: Long?,
    val name: String,
    val cardCnt: Int,
    val cards: List<Card>
) {
    companion object {
        fun fromTblCardSet(item: TblCardSet): CardSet {
            var cs = CardSet(
                item.id,
                item.name,
                item.cardCnt,
                mutableListOf()
            )
            return cs
        }
    }
}