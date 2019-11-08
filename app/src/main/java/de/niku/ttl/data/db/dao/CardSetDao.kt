package de.niku.ttl.data.db.dao

import androidx.room.*
import de.niku.ttl.data.db.entity.TblCardSet

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cardset: TblCardSet): Long

    @Update
    fun update(cardset: TblCardSet)

    @Delete
    fun delete(cardset: TblCardSet): Int

    @Query("DELETE FROM cardsets WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM cardsets")
    fun getCardSets(): List<TblCardSet>

    @Query("UPDATE cardsets SET started = :value WHERE id = :id")
    fun updateStartedColumn(id: Long, value: Int)

    @Query("UPDATE cardsets SET completed = :value WHERE id = :id")
    fun updateCompletedColumn(id: Long, value: Int)



}