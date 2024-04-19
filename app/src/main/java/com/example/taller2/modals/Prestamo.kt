package com.example.taller2.modals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
class Prestamo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPrestamo")
    var idPrestamo: Long = 0,

    @ColumnInfo(name = "nombre")
    @NotNull
    var nombre: String = "",

    @ColumnInfo(name = "fecha")
    @NotNull
    var fecha: String = "",

    @ColumnInfo(name = "telefono")
    @NotNull
    var telefono : String = "",

    @ColumnInfo(name = "equipo")
    @NotNull
    var equipo : String = ""
)