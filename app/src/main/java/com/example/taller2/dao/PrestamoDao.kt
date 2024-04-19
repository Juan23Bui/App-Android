package com.example.taller2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taller2.modals.Prestamo

@Dao
interface PrestamoDao {
    @Query("Select * from prestamo")
    fun obtener(): List<Prestamo>

    @Insert
    fun insert (prestamo: Prestamo)

    @Query("SELECT * FROM prestamo WHERE idPrestamo = :idPrestamo")
    fun buscar(idPrestamo: Long): Prestamo

    @Update
    fun update(prestamo: Prestamo)

    @Query("DELETE FROM prestamo WHERE idPrestamo = :idPrestamo")
    fun eliminar(idPrestamo: Long)
}