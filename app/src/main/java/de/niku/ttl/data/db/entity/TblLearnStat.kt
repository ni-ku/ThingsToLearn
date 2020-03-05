package de.niku.ttl.data.db.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    tableName = "learn_stats",
    indices = [Index("id"), Index("card_set_id")],
    foreignKeys = [
        ForeignKey(
            entity = TblCardSet::class,
            parentColumns = ["id"],
            childColumns = ["card_set_id"],
            onDelete = CASCADE
        )
    ]
)
data class TblLearnStat(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val date: Date,
    val wrong: Int,
    val right: Int,
    val duration: Int,
    @ColumnInfo(name ="card_set_id")
    val cardSetId: Long
)