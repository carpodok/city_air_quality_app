package com.example.cityairqualityapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cityairqualityapp.data.entities.AirQuality
import com.example.cityairqualityapp.util.Converters

@TypeConverters(Converters::class)
@Database(entities = [AirQuality::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun airQualityDao(): AirQualityDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "airqualities")
                .fallbackToDestructiveMigration()
                .build()
    }
}