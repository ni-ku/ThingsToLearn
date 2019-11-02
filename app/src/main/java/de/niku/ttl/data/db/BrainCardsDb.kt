package de.niku.ttl.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.niku.ttl.common.executors.ioThread
import de.niku.ttl.data.db.dao.CardDao
import de.niku.ttl.data.db.dao.CardSetDao
import de.niku.ttl.data.db.dao.CardSetWithCardsDao
import de.niku.ttl.data.db.dao.QuestionsDao
import de.niku.ttl.data.db.entity.CardSetWithCards
import de.niku.ttl.data.db.entity.TblCard
import de.niku.ttl.data.db.entity.TblCardSet
import de.niku.ttl.data.db.entity.TblQuestion

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
                            val cardSetDao = getInstance(context).CardSetDao()
                            val cardDao = getInstance(context).CardDao()
                            val questionDao = getInstance(context).QuestionsDao()

                            var cardSetWithCards = createExampleCardSet()
                            val cardSetId = cardSetDao.insert(cardSetWithCards.cardSet)

                            for (c in cardSetWithCards.cards) {
                                var card = TblCard(
                                    null,
                                    c.front,
                                    c.back,
                                    cardSetId
                                )
                                cardDao.insert(card)
                            }

                            for (q in cardSetWithCards.questions) {
                                var question = TblQuestion(
                                    null,
                                    q.question,
                                    cardSetId
                                )
                                questionDao.insert(question)
                            }
                        }
                    }
                })
                .build()

        private fun createExampleCardSet(): CardSetWithCards {
            var cards: MutableList<TblCard> = mutableListOf()
            var c0 = TblCard(
                null,
                "1",
                "Theodor Heuss",
                0
            )
            var c1 = TblCard(
                null,
                "2",
                "Heinrich Lübcke",
                0
            )
            var c2 = TblCard(
                null,
                "3",
                "Gustav Heinemann",
                0
            )
            var c3 = TblCard(
                null,
                "4",
                "Walter Scheel",
                0
            )
            var c4 = TblCard(
                null,
                "5",
                "Karl Carstens",
                0
            )
            var c5 = TblCard(
                null,
                "6",
                "Richard von Weizsäcker",
                0
            )
            var c6 = TblCard(
                null,
                "7",
                "Roman Herzog",
                0
            )
            var c7 = TblCard(
                null,
                "8",
                "Johannes Rau",
                0
            )
            var c8 = TblCard(
                null,
                "9",
                "Horst Köhler",
                0
            )
            var c9 = TblCard(
                null,
                "10",
                "Christian Wulff",
                0
            )
            var c10 = TblCard(
                null,
                "11",
                "Joachim Gauck",
                0
            )
            var c11 = TblCard(
                null,
                "12",
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

            var questions: MutableList<TblQuestion> = mutableListOf()
            var q0 = TblQuestion(
                null,
                "Wer war der {front} deutsche Bundespräsident?",
                0
            )
            questions.add(q0)

            var cardSet = TblCardSet(
                null,
                "Deutsche Bundespräsidenten",
                cards.size
            )
            var cardSetFull = CardSetWithCards(
                cardSet,
                cards,
                questions
            )

            return cardSetFull
        }
    }

    abstract fun CardSetDao(): CardSetDao
    abstract fun CardDao(): CardDao
    abstract fun QuestionsDao(): QuestionsDao
    abstract fun CardSetWithCardsDao(): CardSetWithCardsDao
}