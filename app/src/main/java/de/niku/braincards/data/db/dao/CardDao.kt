package de.niku.braincards.data.db.dao

import androidx.room.*
import de.niku.braincards.data.db.entity.TblCard

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: TblCard): Long

    @Update
    fun update(card: TblCard)

    @Delete
    fun delete(card: TblCard)

    @Query("DELETE FROM cards WHERE card_set_id = :id")
    fun deleteAllCardsForCardSet(id: Long)

    @Query("SELECT * FROM cards")
    fun getCards(): List<TblCard>?
}