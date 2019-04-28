package de.niku.braincards.data.db.dao

import androidx.room.*
import de.niku.braincards.data.db.entity.TblCardSet

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCardSet(cardset: TblCardSet): Long

    @Update
    fun updateCardSet(cardset: TblCardSet)

    @Delete
    fun deleteCardSet(cardset: TblCardSet)

    @Query("SELECT * FROM cardsets")
    fun getCardSets(): List<TblCardSet>

}