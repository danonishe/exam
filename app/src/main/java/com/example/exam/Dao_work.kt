package com.example.exam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao_work {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(rec: DataClass)

    @Query("SELECT COUNT() FROM work")
    fun countRec(): Int

    @Query("SELECT * FROM work")
    fun returnAll(): List<DataClass>

    @Query("SELECT * FROM work WHERE client = :client")
    fun returnClient(client: String): List<DataClass>

    @Query("SELECT * FROM work WHERE id = :id")
    fun returnItem(id : Int): DataClass

    @Query("DELETE FROM work WHERE id = :id")
    fun delete(id : Int)

    //Функции изменения данных в бд
    @Query("UPDATE work SET name = :name WHERE id = :id")
    fun updateNameItem(id : Int, name : String)
    @Query("UPDATE work SET cost = :cost WHERE id = :id")
    fun updateCostItem(id : Int, cost : Int)
    @Query("UPDATE work SET client = :client WHERE id = :id")
    fun updateClientItem(id : Int, client : String)
    //----------------------------------------------
}