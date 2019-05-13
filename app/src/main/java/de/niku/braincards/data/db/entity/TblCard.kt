package de.niku.braincards.data.db.entity

import androidx.room.*

@Entity(
    tableName = "cards",
    indices = [Index("id"), Index("card_set_id")],
    foreignKeys = [
        ForeignKey(
            entity = TblCardSet::class,
            parentColumns = ["id"],
            childColumns = ["card_set_id"]
        )
    ]
)
class TblCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val front: String,
    val back: String,
    @ColumnInfo(name = "card_set_id")
    val cardSetId: Long
)
