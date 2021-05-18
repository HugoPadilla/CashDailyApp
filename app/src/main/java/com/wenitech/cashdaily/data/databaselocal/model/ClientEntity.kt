package com.wenitech.cashdaily.data.databaselocal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClientEntity(
        @PrimaryKey
        @ColumnInfo(name = "nombre")
        val nombre: String
)
