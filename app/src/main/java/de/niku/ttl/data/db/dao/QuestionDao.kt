package de.niku.ttl.data.db.dao

import androidx.room.*
import de.niku.ttl.data.db.entity.TblQuestion

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(question: TblQuestion): Long

    @Update
    fun update(question: TblQuestion)

    @Delete
    fun  delete(question: TblQuestion)

    @Query("DELETE FROM questions WHERE card_set_id = :id")
    fun deleteAllQuestionsFromCardSet(id: Long)

    @Query("SELECT * FROM questions")
    fun getAll(): List<TblQuestion>?
}