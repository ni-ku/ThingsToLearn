package de.niku.braincards.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cardsets"
)
data class TblCardSet(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val cardCnt: Int
)