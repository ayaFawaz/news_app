package com.example.dailynews.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dailynews.data.models.Article

@Database(entities = [Article::class], version = 3, exportSchema = false)
@TypeConverters(SourceConverter::class)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articleDAO(): ArticleDAO

    companion object {
        @Volatile
        private var INSTANCE: ArticleDataBase? = null

        fun getDatabase(context: Context): ArticleDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDataBase::class.java,
                    "article_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}