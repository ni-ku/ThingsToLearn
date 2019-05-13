package de.niku.braincards.data.db.dao

import androidx.room.*
import de.niku.braincards.data.db.entity.TblCardSet

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cardset: TblCardSet): Long

    @Update
    fun update(cardset: TblCardSet)

    @Delete
    fun delete(cardset: TblCardSet)

    @Query("SELECT * FROM cardsets")
    fun getCardSets(): List<TblCardSet>

}