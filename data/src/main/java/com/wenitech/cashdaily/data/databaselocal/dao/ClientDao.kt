package com.wenitech.cashdaily.data.databaselocal.dao

import androidx.room.Dao
import androidx.room.Query
import com.wenitech.cashdaily.data.databaselocal.model.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Query("SELECT * FROM table_client WHERE nombre == :name")
    fun getClient(name: String): Flow<List<ClientEntity>>
}