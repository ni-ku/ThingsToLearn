package de.niku.braincards.data.db.dao

import androidx.room.*
import de.niku.braincards.data.db.entity.TblQuestion

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(question: TblQuestion): Long

    @Update
    fun update(question: TblQuestion)

    @Delete
    fun delete(card: TblQuestion)

    @Query("SELECT * FROM questions")
    fun getCards(): List<TblQuestion>?
}