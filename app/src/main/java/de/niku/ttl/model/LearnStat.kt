package de.niku.ttl.model

import java.util.*

data class LearnStat(
    val id: Long?,
    val date: Date,
    var wrong: Int,
    var right: Int,
    var duration: Int
) {

    constructor(): this(null, Date(), 0, 0, 0)
}