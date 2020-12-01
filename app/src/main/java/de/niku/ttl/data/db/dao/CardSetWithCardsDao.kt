package de.niku.ttl.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import de.niku.ttl.data.db.entity.CardSetWithCards

@Dao
interface CardSetWithCardsDao {

    @Query("SELECT * FROM cardsets")
    @Transaction
    fun getCardSets(): List<CardSetWithCards>

    @Query("SELECT * FROM cardsets WHERE id = :id")
    @Transaction
    fun getCardSetById(id: Long): CardSetWithCards
}