package de.niku.ttl.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import de.niku.ttl.common.executors.ioThread
import de.niku.ttl.data.db.dao.*
import de.niku.ttl.data.db.entity.*

@Database(
    version = 1,
    entities = [
        TblCardSet::class,
        TblCard::class,
        TblLearnStat::class,
        TblQuestion::class
    ]
)
@TypeConverters(DbTypeConverters::class)
abstract class BrainCardsDb : RoomDatabase() {

    companion object {
        private const val APP_DB_NAME = "braindcardsdb"
        @Volatile
        private var INSTANCE: BrainCardsDb? = null

        /*
            followed this snippets to pre-populate the database
            https://gist.github.com/florina-muntenescu/697e543652b03d3d2a06703f5d6b44b5
         */
        fun getInstance(context: Context): BrainCardsDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BrainCardsDb::class.java, APP_DB_NAME
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // run db insertions on seperate thread
                        ioThread {
                            val cardSetDao = getInstance(context).cardSetDao()
                            val cardDao = getInstance(context).cardDao()

                            val cardSetWithCards = createExampleCardSet()
                            val cardSetId = cardSetDao.insert(cardSetWithCards.cardSet)

                            for (c in cardSetWithCards.cards) {
                                val card = TblCard(
                                    null,
                                    c.front,
                                    c.back,
                                    cardSetId
                                )
                                cardDao.insert(card)
                            }
                        }
                    }
                })
                .build()

        private fun createExampleCardSet(): CardSetWithCards {
            val cards: MutableList<TblCard> = mutableListOf()
            val c0 = TblCard(
                null,
                "Wer war der 1. Bundespräsident ?",
                "Theodor Heuss",
                0
            )
            val c1 = TblCard(
                null,
                "Wer war der 2. Bundespräsident ?",
                "Heinrich Lübcke",
                0
            )
            val c2 = TblCard(
                null,
                "Wer war der 3. Bundespräsident ?",
                "Gustav Heinemann",
                0
            )
            val c3 = TblCard(
                null,
                "Wer war der 4. Bundespräsident ?",
                "Walter Scheel",
                0
            )
            val c4 = TblCard(
                null,
                "Wer war der 5. Bundespräsident ?",
                "Karl Carstens",
                0
            )
            val c5 = TblCard(
                null,
                "Wer war der 6. Bundespräsident ?",
                "Richard von Weizsäcker",
                0
            )
            val c6 = TblCard(
                null,
                "Wer war der 7. Bundespräsident ?",
                "Roman Herzog",
                0
            )
            val c7 = TblCard(
                null,
                "Wer war der 8. Bundespräsident ?",
                "Johannes Rau",
                0
            )
            val c8 = TblCard(
                null,
                "Wer war der 9. Bundespräsident ?",
                "Horst Köhler",
                0
            )
            val c9 = TblCard(
                null,
                "Wer war der 10. Bundespräsident ?",
                "Christian Wulff",
                0
            )
            val c10 = TblCard(
                null,
                "Wer war der 11. Bundespräsident ?",
                "Joachim Gauck",
                0
            )
            val c11 = TblCard(
                null,
                "Wer war der 12. Bundespräsident ?",
                "Franz-Walter Steinmeier",
                0
            )
            cards.add(c0)
            cards.add(c1)
            cards.add(c2)
            cards.add(c3)
            cards.add(c4)
            cards.add(c5)
            cards.add(c6)
            cards.add(c7)
            cards.add(c8)
            cards.add(c9)
            cards.add(c10)
            cards.add(c11)

            val cardSet = TblCardSet(
                null,
                "Deutsche Bundespräsidenten",
                cards.size,
                0,
                0
            )

            return CardSetWithCards(
                cardSet,
                cards,
                mutableListOf(),
                mutableListOf()
            )
        }
    }

    abstract fun cardSetDao(): CardSetDao
    abstract fun cardDao(): CardDao
    abstract fun cardSetWithCardsDao(): CardSetWithCardsDao
    abstract fun learnStatDao(): LearnStatDao
    abstract fun questionDao(): QuestionDao
}