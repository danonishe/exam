package com.example.exam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [DataClass::class], version = 1)
abstract class DB_work : RoomDatabase() {
    abstract fun getDao(): Dao_work

    companion object{
        fun getDb(context: Context): DB_work {
            return Room.databaseBuilder(
                context.applicationContext,
                DB_work::class.java,
                "work.db"
            ).build()
        }
    }
}