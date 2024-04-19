package com.example.taller2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.taller2.configuracion.PrestamoDB
import com.example.taller2.dao.PrestamoDao
import com.example.taller2.modals.Prestamo
import java.lang.Exception

private lateinit var db: PrestamoDB
private lateinit var tableLayout: TableLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edtCodigo = findViewById<EditText>(R.id.edtCodigo)
        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtFecha = findViewById<EditText>(R.id.edtFecha)
        val edtTelefono = findViewById<EditText>(R.id.edtTelefono)

        val btnBuscar = findViewById<ImageButton>(R.id.btnBuscar)
        val btnEliminar = findViewById<ImageButton>(R.id.btnEliminar)
        val btnGuardar = findViewById<ImageButton>(R.id.btnGuardar)
        val btnEditar = findViewById<ImageButton>(R.id.btnEditar)
        val btnListar = findViewById<ImageButton>(R.id.btnListar)

        val lstEquipos = findViewById<Spinner>(R.id.lstEquipos)

        tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        db = Room.databaseBuilder(application, PrestamoDB::class.java, PrestamoDB.DATABASE_NAME)
            .allowMainThreadQueries().build()

        val opcionesEquipos = listOf("tablet", "computador", "cámara", "teclado", "mouse")
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesEquipos)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lstEquipos.adapter = adaptador

        btnGuardar.setOnClickListener {
            val idPrestamo = edtCodigo.text.toString().toLongOrNull()
            val nombre = edtNombre.text.toString()
            val fecha = edtFecha.text.toString()
            val telefono = edtTelefono.text.toString()
            val equipo = lstEquipos.selectedItem.toString()

            if (idPrestamo != null && nombre.isNotEmpty() && fecha.isNotEmpty() && telefono.isNotEmpty()) {
                val prestamo = Prestamo(idPrestamo, nombre, fecha, telefono, equipo)
                try {
                    db.prestamoDao.insert(prestamo)
                    listarPrestamos()
                    Toast.makeText(this, "Se registró el préstamo", Toast.LENGTH_LONG).show()
                } catch (ex: Exception) {
                    Toast.makeText(this, "El id del préstamo ya existe", Toast.LENGTH_LONG).show()
                }
                edtCodigo.setText("")
                edtNombre.setText("")
                edtFecha.setText("")
                edtTelefono.setText("")
            } else {
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_LONG).show()
            }
        }

        btnListar.setOnClickListener {
            listarPrestamos()
        }

        btnBuscar.setOnClickListener {
            val idPrestamo = edtCodigo.text.toString().toLongOrNull()
            if (idPrestamo != null) {
                val prestamoEncontrado = db.prestamoDao.buscar(idPrestamo)
                if (prestamoEncontrado != null) {
                    edtNombre.setText(prestamoEncontrado.nombre)
                    edtFecha.setText(prestamoEncontrado.fecha)
                    edtTelefono.setText(prestamoEncontrado.telefono)
                    lstEquipos.setSelection(opcionesEquipos.indexOf(prestamoEncontrado.equipo))
                } else {
                    Toast.makeText(this, "Prestamo no encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }

        btnEditar.setOnClickListener {
            val idPrestamo = edtCodigo.text.toString().toLongOrNull()
            val nombre = edtNombre.text.toString()
            val fecha = edtFecha.text.toString()
            val telefono = edtTelefono.text.toString()
            val equipo = lstEquipos.selectedItem.toString()

            if (idPrestamo != null && nombre.isNotEmpty() && fecha.isNotEmpty() && telefono.isNotEmpty()) {
                val prestamo = Prestamo(idPrestamo, nombre, fecha, telefono, equipo)
                try {
                    db.prestamoDao.update(prestamo)
                    listarPrestamos()
                    Toast.makeText(this, "Prestamo actualizado", Toast.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                    Toast.makeText(this, "Error al actualizar el prestamo", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            val idPrestamo = edtCodigo.text.toString().toLongOrNull()
            if (idPrestamo == null) {
                Toast.makeText(this, "ID vacío", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    db.prestamoDao.eliminar(idPrestamo)
                    listarPrestamos()
                    Toast.makeText(this, "Prestamo eliminado", Toast.LENGTH_SHORT).show()
                    edtCodigo.setText("")
                    edtNombre.setText("")
                    edtFecha.setText("")
                    edtTelefono.setText("")
                    lstEquipos.setSelection(0)
                } catch (ex: Exception) {
                    Toast.makeText(this, "Error al eliminar el prestamo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun listarPrestamos() {
        tableLayout.removeAllViews()

        val prestamos = db.prestamoDao.obtener()

        for (prestamo in prestamos) {
            val row = TableRow(this)

            val idTextView = TextView(this)
            idTextView.text = prestamo.idPrestamo.toString()
            idTextView.setPadding(16, 0, 16, 0)
            row.addView(idTextView)

            val nombreTextView = TextView(this)
            nombreTextView.text = prestamo.nombre
            nombreTextView.setPadding(16, 0, 16, 0)
            row.addView(nombreTextView)

            val fechaTextView = TextView(this)
            fechaTextView.text = prestamo.fecha
            fechaTextView.setPadding(16, 0, 16, 0)
            row.addView(fechaTextView)

            val telefonoTextView = TextView(this)
            telefonoTextView.text = prestamo.telefono
            telefonoTextView.setPadding(16, 0, 16, 0)
            row.addView(telefonoTextView)

            val equipoTextView = TextView(this)
            equipoTextView.text = prestamo.equipo
            equipoTextView.setPadding(16, 0, 16, 0)
            row.addView(equipoTextView)

            tableLayout.addView(row)
        }
    }
}