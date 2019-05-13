package de.niku.braincards.di.module

import dagger.Module
import dagger.Provides
import de.niku.braincards.data.db.dao.CardDao
import de.niku.braincards.data.db.dao.CardSetDao
import de.niku.braincards.data.db.dao.CardSetWithCardsDao
import de.niku.braincards.data.db.dao.QuestionsDao
import de.niku.braincards.data.repo.card_set.CardSetRepo
import de.niku.braincards.data.repo.card_set.CardSetRepoImpl
import javax.inject.Singleton

@Module
class DataRepoModule {

    @Provides
    @Singleton
    fun provideCardSetRepo(cardSetDao: CardSetDao,
                           cardDao: CardDao,
                           questionDao: QuestionsDao,
                           cardSetWithCardsDao: CardSetWithCardsDao) : CardSetRepo {
        return CardSetRepoImpl(
            cardSetDao,
            cardDao,
            questionDao,
            cardSetWithCardsDao
        )
    }
}