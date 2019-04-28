package de.niku.braincards.model

import de.niku.braincards.data.db.entity.TblCardSet

data class CardSet(
    val id: Int?,
    val name: String,
    val cardCnt: Int
) {
    companion object {
        fun fromTblCardSet(item: TblCardSet): CardSet {
            var cs = CardSet(
                item.id,
                item.name,
                item.cardCnt
            )
            return cs
        }
    }
}