package com.example.taller2.configuracion

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taller2.dao.PrestamoDao
import com.example.taller2.modals.Prestamo

@Database(
    entities = [Prestamo::class],
    version = 1
)
abstract class PrestamoDB: RoomDatabase() {
    abstract val prestamoDao: PrestamoDao

    companion object{
        const val DATABASE_NAME = "BD_Prestamo"
    }
}