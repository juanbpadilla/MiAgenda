package com.juanpadilla.miagenda.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juanpadilla.miagenda.dao.PersonalDao
import com.juanpadilla.miagenda.models.Personal

@Database(
    entities = [Personal::class],
    version = 1
)
abstract class PersonalDb: RoomDatabase() {
    abstract fun personalDao():PersonalDao
}