package com.ies.iax.prgmdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Menú principal de la UT7. Desde aquí abro cada laboratorio.
 *
 * @autor Pablo Jiménez Jorquera
 * @fecha 18/11/2026
 * @modificado 18/11/2026 - añado el botón del Lab 2 (Pergaminos)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cojo los botones del menú por su id
        val btnIrPrefs = findViewById<Button>(R.id.btnIrPrefs)
        val btnIrFicheros = findViewById<Button>(R.id.btnIrFicheros)

        // Lab 1: abro la pantalla del Registro de Guardias
        btnIrPrefs.setOnClickListener {
            val intent = Intent(this, PreferenciasActivity::class.java)
            startActivity(intent)
        }

        // Lab 2: abro la pantalla de los Pergaminos (ficheros internos y externos)
        btnIrFicheros.setOnClickListener {
            val intent = Intent(this, FicherosActivity::class.java)
            startActivity(intent)
        }
    }
}
