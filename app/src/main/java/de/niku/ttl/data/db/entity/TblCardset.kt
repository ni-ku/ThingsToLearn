package de.niku.ttl.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cardsets"
)
data class TblCardSet(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
    val cardCnt: Int
)