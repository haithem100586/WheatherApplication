package com.helloumi.weatherapplication.data.datasource

import androidx.room.*

/**
 * Base data access object for resources stored in Room.
 */
@Dao
interface BaseDao<T> {
    /**
     * Inserts data in the database.
     *
     * @param data data to insert.
     *
     * @return inserted data id.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T): Long

    /**
     * Inserts data in the database.
     *
     * @param data list of data to insert.
     *
     * @return inserted data id list.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<T>): List<Long>

    /**
     * Updates data in the database.
     *
     * @param data updated data to apply, according to primary key.
     *
     * @return number of updated occurrence.
     */
    @Update
    suspend fun update(data: T): Int

    /**
     * Deletes data in the database.
     *
     * @param data data to delete, according to primary key.
     *
     * @return number of deleted occurrence.
     */
    @Delete
    suspend fun delete(data: T): Int
}
