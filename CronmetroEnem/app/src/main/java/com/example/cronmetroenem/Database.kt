package com.example.cronmetroenem

import androidx.room.*

@Entity
data class Prova(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "cancelled") val cancelled: Boolean
)

@Dao
interface ProvaDao {
    @Query("SELECT * FROM prova")
    fun getAll(): List<Prova>

    @Query("SELECT * FROM prova WHERE day IS :cDay")
    fun getByDay(cDay: Int): List<Prova>

    @Insert
    fun insertAll(vararg provas: Prova)

    @Delete
    fun delete(prova: Prova)
}

@Database(entities = arrayOf(Prova::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun provaDao(): ProvaDao
}