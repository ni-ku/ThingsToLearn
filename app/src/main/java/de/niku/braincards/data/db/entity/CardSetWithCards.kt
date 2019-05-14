package de.niku.braincards.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

class CardSetWithCards(
    @Embedded
    val cardSet: TblCardSet,
    @Relation(parentColumn = "id", entityColumn = "card_set_id", entity = TblCard::class)
    val cards: List<TblCard>,
    @Relation(parentColumn = "id", entityColumn = "card_set_id", entity = TblQuestion::class)
    val questions: List<TblQuestion>
) {
}