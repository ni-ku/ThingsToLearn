package de.niku.ttl.data.db.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "questions",
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
class TblQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val question: String,
    @ColumnInfo(name = "card_set_id")
    val cardSetId: Long
)