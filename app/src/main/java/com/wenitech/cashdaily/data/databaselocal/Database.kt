package com.wenitech.cashdaily.data.databaselocal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wenitech.cashdaily.data.databaselocal.dao.ClientDao
import com.wenitech.cashdaily.data.databaselocal.model.ClientEntity

@Database(entities = [ClientEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun clientDao() : ClientDao
}