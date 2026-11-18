package com.ies.iax.prgmdm

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException

/**
 * Lab 2 UT7 - Los Pergaminos de la Ciudadela Blanca.
 * Es la continuación del Lab 1: en vez de SharedPreferences ahora guardo textos en ficheros.
 *  - Interno: openFileOutput / openFileInput en MODE_PRIVATE (data/data/com.ies.iax.prgmdm/files)
 *
 * @autor Pablo Jiménez Jorquera
 * @fecha 18/11/2026
 */
class FicherosActivity : AppCompatActivity() {

    private lateinit var etNombreArchivo: EditText
    private lateinit var etContenido: EditText
    private lateinit var btnEscribir: Button
    private lateinit var btnLeer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficheros)

        // Enlazo las vistas del layout
        etNombreArchivo = findViewById(R.id.etNombreArchivo)
        etContenido = findViewById(R.id.etContenidoFichero)
        btnEscribir = findViewById(R.id.btnEscribirFichero)
        btnLeer = findViewById(R.id.btnLeerFichero)

        // Cada botón solo llama a su función, así el onCreate queda más limpio
        btnEscribir.setOnClickListener { guardarInterno() }
        btnLeer.setOnClickListener { leerInterno() }
    }

    // ===================== ALMACENAMIENTO INTERNO =====================

    private fun guardarInterno() {
        val nombre = etNombreArchivo.text.toString().trim()
        val texto = etContenido.text.toString()

        if (nombre.isEmpty()) {
            etNombreArchivo.error = "Pon un nombre para el pergamino"
            return
        }

        try {
            // MODE_PRIVATE: solo mi app puede ver el fichero y si ya existe lo sobrescribe.
            // El .use { } cierra el flujo él solo al acabar, aunque salte un error
            openFileOutput(nombre, Context.MODE_PRIVATE).use { flujoSalida ->
                flujoSalida.write(texto.toByteArray(Charsets.UTF_8))
            }
            Toast.makeText(this, "Pergamino guardado en files/$nombre", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun leerInterno() {
        val nombre = etNombreArchivo.text.toString().trim()

        if (nombre.isEmpty()) {
            etNombreArchivo.error = "Pon el nombre del pergamino que quieres leer"
            return
        }

        try {
            // Abro el flujo de lectura y lo paso a String. El .use { } también lo cierra al terminar
            val textoLeido = openFileInput(nombre).bufferedReader(Charsets.UTF_8).use { lector ->
                lector.readText()
            }
            etContenido.setText(textoLeido)
            Toast.makeText(this, "Pergamino leído", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            // Si el fichero no existe no dejo que la app se cierre, solo aviso
            etNombreArchivo.error = "Ese pergamino no existe en la bóveda"
            Toast.makeText(this, "Fichero no encontrado", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al leer: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Después de guardar dejo los campos vacíos para el siguiente pergamino
    private fun limpiarCampos() {
        etNombreArchivo.text.clear()
        etContenido.text.clear()
    }
}
