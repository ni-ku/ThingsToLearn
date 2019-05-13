package de.niku.braincards.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.niku.braincards.data.db.dao.CardDao
import de.niku.braincards.data.db.dao.CardSetDao
import de.niku.braincards.data.db.dao.CardSetWithCardsDao
import de.niku.braincards.data.db.dao.QuestionsDao
import de.niku.braincards.data.db.entity.TblCard
import de.niku.braincards.data.db.entity.TblCardSet
import de.niku.braincards.data.db.entity.TblQuestion

@Database(
    version = 1,
    entities = [
        TblCardSet::class,
        TblCard::class,
        TblQuestion::class
    ]
)
abstract class BrainCardsDb : RoomDatabase() {

    companion object {
        const val APP_DB_NAME = "braindcardsdb"
        var INSTANCE: BrainCardsDb? = null

        fun getAppDb(context: Context): BrainCardsDb? {
            if (INSTANCE == null) {
                synchronized(BrainCardsDb::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, BrainCardsDb::class.java, APP_DB_NAME).build()
                }
            }
            return INSTANCE
        }

        fun destroyAppDb() {
            INSTANCE = null
        }
    }

    abstract fun CardSetDao(): CardSetDao
    abstract fun CardDao(): CardDao
    abstract fun QuestionsDao(): QuestionsDao

    abstract fun CardSetWithCardsDao(): CardSetWithCardsDao
}