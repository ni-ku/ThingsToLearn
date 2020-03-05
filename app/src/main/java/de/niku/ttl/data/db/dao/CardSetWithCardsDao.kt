package de.niku.ttl.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import de.niku.ttl.data.db.entity.CardSetWithCards

@Dao
interface CardSetWithCardsDao {

    @Query("SELECT * FROM cardsets")
    fun getCardSets(): List<CardSetWithCards>

    @Query("SELECT * FROM cardsets WHERE id = :id")
    fun getCardSetById(id: Long): CardSetWithCards
}