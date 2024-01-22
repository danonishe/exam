package com.example.exam
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "work")
data class DataClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "cost")
    var cost: Int,
    @ColumnInfo(name = "client")
    var client: String,
)