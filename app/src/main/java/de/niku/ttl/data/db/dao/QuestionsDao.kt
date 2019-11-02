package de.niku.ttl.data.db.dao

import androidx.room.*
import de.niku.ttl.data.db.entity.TblQuestion

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(question: TblQuestion): Long

    @Update
    fun update(question: TblQuestion)

    @Delete
    fun delete(card: TblQuestion)

    @Query("DELETE FROM questions WHERE card_set_id = :id")
    fun deleteAllQuestionsForCardSet(id: Long)

    @Query("SELECT * FROM questions")
    fun getCards(): List<TblQuestion>?
}