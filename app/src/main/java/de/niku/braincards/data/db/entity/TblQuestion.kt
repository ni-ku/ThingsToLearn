package de.niku.braincards.data.db.entity

import androidx.room.*

@Entity(
    tableName = "questions",
    indices = [Index("id"), Index("card_set_id")],
    foreignKeys = [
        ForeignKey(
            entity = TblCardSet::class,
            parentColumns = ["id"],
            childColumns = ["card_set_id"]
        )
    ]

)
class TblQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val question: String,
    @ColumnInfo(name = "card_set_id")
    val cardSetId: Long
) {
}