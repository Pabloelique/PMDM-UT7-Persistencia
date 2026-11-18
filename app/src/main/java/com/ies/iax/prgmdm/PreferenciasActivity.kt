package com.ies.iax.prgmdm

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Lab 1 UT7 - El Registro de Guardias de la Ciudadela.
 * Guardo el nombre, el rango y el estado del soldado con SharedPreferences
 * y los vuelvo a cargar en onResume() para que no se pierdan al cerrar la app.
 *
 * @autor Pablo Jiménez Jorquera
 * @fecha 18/11/2026
 * @modificado 18/11/2026 - guardo y cargo los datos con SharedPreferences
 */
class PreferenciasActivity : AppCompatActivity() {

    // Nombre del fichero (Android lo crea como RegistroGuardia.xml) y las claves del enunciado
    companion object {
        const val FICHERO = "RegistroGuardia"
        const val KEY_SOLDADO = "KEY_SOLDADO"   // String
        const val KEY_RANGO = "KEY_RANGO"       // Int: 0 Recluta, 1 Capitán, 2 Veterano
        const val KEY_ESTADO = "KEY_ESTADO"     // Boolean: true En Guardia, false En Reposo
    }

    private lateinit var etSoldado: EditText
    private lateinit var spRango: Spinner
    private lateinit var swEstado: Switch
    private lateinit var btnGuardar: Button
    private lateinit var tvUltimoRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        // Enlazo las vistas del layout
        etSoldado = findViewById(R.id.etSoldado)
        spRango = findViewById(R.id.spRango)
        swEstado = findViewById(R.id.swEstado)
        btnGuardar = findViewById(R.id.btnGuardar)
        tvUltimoRegistro = findViewById(R.id.tvUltimoRegistro)

        // Cuando cambio el Switch cambio también su texto
        swEstado.setOnCheckedChangeListener { _, activado ->
            swEstado.text = if (activado) "En Guardia" else "En Reposo"
        }

        btnGuardar.setOnClickListener {
            val nombre = etSoldado.text.toString().trim()

            // El nombre no puede ir vacío
            if (nombre.isEmpty()) {
                etSoldado.error = "El nombre del efectivo no puede estar vacío"
            } else {
                // La posición del Spinner ya es el número del rango (0, 1 o 2)
                guardarRegistro(nombre, spRango.selectedItemPosition, swEstado.isChecked)

                // Una vez hecho el apply() limpio el EditText y le quito el foco
                etSoldado.text.clear()
                etSoldado.clearFocus()
                mostrarResumen(nombre, spRango.selectedItemPosition, swEstado.isChecked)
                Toast.makeText(this, "Registro guardado en RegistroGuardia.xml", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Cargo los datos en onResume() porque se ejecuta siempre que la pantalla vuelve
    // a primer plano (al abrir la app, después de una llamada, al desbloquear...)
    override fun onResume() {
        super.onResume()
        cargarRegistro()
    }

    private fun guardarRegistro(nombre: String, rango: Int, enGuardia: Boolean) {
        val prefs = getSharedPreferences(FICHERO, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_SOLDADO, nombre)
        editor.putInt(KEY_RANGO, rango)
        editor.putBoolean(KEY_ESTADO, enGuardia)
        // Uso apply() y no commit() porque apply() escribe en segundo plano
        // y no bloquea la pantalla. Los tres datos se guardan juntos en el mismo apply()
        editor.apply()
    }

    private fun cargarRegistro() {
        val prefs = getSharedPreferences(FICHERO, Context.MODE_PRIVATE)

        // Si todavía no hay nada guardado uso los valores por defecto:
        // nombre vacío, Recluta (0) y En Reposo (false)
        val nombre = prefs.getString(KEY_SOLDADO, "") ?: ""
        var rango = prefs.getInt(KEY_RANGO, 0)
        val enGuardia = prefs.getBoolean(KEY_ESTADO, false)

        // Por si el rango guardado no está entre 0 y 2, lo dejo en Recluta para que no pete el Spinner
        if (rango !in 0..2) {
            rango = 0
        }

        // Relleno el formulario con lo que había guardado
        etSoldado.setText(nombre)
        spRango.setSelection(rango)
        swEstado.isChecked = enGuardia
        mostrarResumen(nombre, rango, enGuardia)
    }

    // Enseño abajo lo último que se ha guardado
    private fun mostrarResumen(nombre: String, rango: Int, enGuardia: Boolean) {
        if (nombre.isEmpty()) {
            tvUltimoRegistro.text = "Último registro: ninguno"
        } else {
            val nombreRango = resources.getStringArray(R.array.rangos)[rango]
            val estado = if (enGuardia) "En Guardia" else "En Reposo"
            tvUltimoRegistro.text = "Último registro: $nombre - $nombreRango - $estado"
        }
    }
}
