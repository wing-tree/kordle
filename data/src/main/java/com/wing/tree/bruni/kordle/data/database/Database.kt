package com.wing.tree.bruni.kordle.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wing.tree.bruni.kordle.data.dao.KoreanVocabularyDao
import com.wing.tree.bruni.kordle.data.entity.KoreanVocabulary

@androidx.room.Database(
    entities = [KoreanVocabulary::class],
    exportSchema = true,
    version = 3
)
abstract class Database: RoomDatabase() {
    abstract fun koreanVocabularyDao(): KoreanVocabularyDao

    companion object {
        private const val PACKAGE_NAME = "com.wing.tree.bruni.kordle.data.database"
        private const val CLASS_NAME = "Database"
        private const val DATABASE_FILE_PATH = "korean_vocabulary.db"
        private const val NAME = "$PACKAGE_NAME.$CLASS_NAME.$DATABASE_FILE_PATH"
        private const val VERSION = "1.0.2"

        @Volatile
        private var INSTANCE: Database? = null

        fun instance(context: Context): Database {
            synchronized(this) {
                return INSTANCE ?: run {
                    Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "$NAME.$VERSION"
                    )
                        .createFromAsset(DATABASE_FILE_PATH)
                        .build()
                        .also {
                            INSTANCE = it
                        }
                }
            }
        }
    }
}