package com.application.prueba.data.database

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import java.util.*

@Dao
abstract class BaseDao<T>(private val entityClass: Class<T>) {

    // GENERIC QUERIES

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: T?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(list: List<T>)

    @Delete
    abstract suspend fun delete(entity: T)

    @Delete
    abstract suspend fun delete(list: List<T>)

    suspend fun getAll(): List<T> {
        val query = SimpleSQLiteQuery("SELECT * FROM ${DatabaseService(entityClass).getTableName()}")
        return queryGetAll(query)
    }

    suspend fun clear(): Int {
        val query = SimpleSQLiteQuery("DELETE FROM ${DatabaseService(entityClass).getTableName()}")
        return queryClear(query)
    }

    @RawQuery
    protected abstract suspend fun queryGetAll(query: SupportSQLiteQuery?): List<T>

    @RawQuery
    protected abstract suspend fun queryClear(query: SupportSQLiteQuery?): Int
}

open class DatabaseService<T>(private val entityClass: Class<T>) {

    fun getTableName(): String {
        return when (entityClass) {
            entityClass::class.java -> "SomeTableThatDoesntMatchClassName"
            else -> entityClass.simpleName.toLowerCase(Locale.ROOT)
        }
    }
}