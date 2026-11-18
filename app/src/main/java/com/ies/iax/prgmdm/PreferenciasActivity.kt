package com.ies.iax.prgmdm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Lab 1 UT7 - El Registro de Guardias de la Ciudadela.
 * De momento solo monto la pantalla y enlazo las vistas.
 *
 * @autor Pablo Jiménez Jorquera
 * @fecha 18/11/2026
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

        // TODO: guardar y cargar con SharedPreferences
    }
}
