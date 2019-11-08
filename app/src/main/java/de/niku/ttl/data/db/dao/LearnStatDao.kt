package de.niku.ttl.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.niku.ttl.data.db.entity.TblLearnStat

@Dao
interface LearnStatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stat: TblLearnStat): Long

    @Query("DELETE FROM learn_stats WHERE card_set_id = :id")
    fun deleteAllStatsForCardSet(id: Long)

    @Query("SELECT * FROM learn_stats")
    fun getAllStats(): List<TblLearnStat>?
}