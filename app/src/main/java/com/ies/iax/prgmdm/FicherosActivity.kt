package com.ies.iax.prgmdm

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileNotFoundException

/**
 * Lab 2 UT7 - Los Pergaminos de la Ciudadela Blanca.
 * Es la continuación del Lab 1: en vez de SharedPreferences ahora guardo textos en ficheros.
 *  - Interno: openFileOutput / openFileInput en MODE_PRIVATE (data/data/com.ies.iax.prgmdm/files)
 *  - Externo privado: getExternalFilesDir(DIRECTORY_DOCUMENTS)
 *    (sdcard/Android/data/com.ies.iax.prgmdm/files/Documents)
 *
 * @autor Pablo Jiménez Jorquera
 * @fecha 18/11/2026
 * @modificado 18/11/2026 - añado el almacenamiento externo (mapas de campaña)
 */
class FicherosActivity : AppCompatActivity() {

    private lateinit var etNombreArchivo: EditText
    private lateinit var etContenido: EditText
    private lateinit var btnEscribir: Button
    private lateinit var btnLeer: Button
    private lateinit var btnEscribirExterno: Button
    private lateinit var btnLeerExterno: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficheros)

        // Enlazo las vistas del layout
        etNombreArchivo = findViewById(R.id.etNombreArchivo)
        etContenido = findViewById(R.id.etContenidoFichero)
        btnEscribir = findViewById(R.id.btnEscribirFichero)
        btnLeer = findViewById(R.id.btnLeerFichero)
        btnEscribirExterno = findViewById(R.id.btnEscribirExterno)
        btnLeerExterno = findViewById(R.id.btnLeerExterno)

        // Cada botón solo llama a su función, así el onCreate queda más limpio
        btnEscribir.setOnClickListener { guardarInterno() }
        btnLeer.setOnClickListener { leerInterno() }
        btnEscribirExterno.setOnClickListener { guardarExterno() }
        btnLeerExterno.setOnClickListener { leerExterno() }
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

    // ===================== ALMACENAMIENTO EXTERNO =====================

    private fun guardarExterno() {
        val nombre = etNombreArchivo.text.toString().trim()
        val texto = etContenido.text.toString()

        // Antes de escribir miro que la memoria externa esté montada
        if (!esExternoEscribible()) {
            Toast.makeText(this, "La memoria externa no está disponible", Toast.LENGTH_LONG).show()
            return
        }
        if (nombre.isEmpty()) {
            etNombreArchivo.error = "Pon un nombre para el mapa"
            return
        }

        try {
            // Carpeta Documents de mi app en la memoria externa. No hace falta pedir permisos
            // en el Manifest porque es una carpeta solo de mi app
            val carpeta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val archivo = File(carpeta, nombre)
            archivo.writeText(texto)
            Toast.makeText(this, "Mapa guardado en Android/data/.../Documents/$nombre", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar el mapa: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun leerExterno() {
        val nombre = etNombreArchivo.text.toString().trim()

        // Para leer me vale con que esté montada aunque sea de solo lectura
        if (!esExternoLeible()) {
            Toast.makeText(this, "No se puede leer la memoria externa", Toast.LENGTH_LONG).show()
            return
        }
        if (nombre.isEmpty()) {
            etNombreArchivo.error = "Pon el nombre del mapa que quieres leer"
            return
        }

        try {
            val carpeta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val archivo = File(carpeta, nombre)

            // Compruebo que el fichero existe antes de leerlo
            if (archivo.exists()) {
                etContenido.setText(archivo.readText())
                Toast.makeText(this, "Mapa cargado", Toast.LENGTH_SHORT).show()
            } else {
                etNombreArchivo.error = "Ese mapa no existe"
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al leer el mapa: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // MEDIA_MOUNTED = se puede leer y escribir
    private fun esExternoEscribible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return estado == Environment.MEDIA_MOUNTED
    }

    // MEDIA_MOUNTED_READ_ONLY = solo se puede leer, pero para leer también me sirve
    private fun esExternoLeible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return estado == Environment.MEDIA_MOUNTED || estado == Environment.MEDIA_MOUNTED_READ_ONLY
    }

    // =================================================================

    // Después de guardar dejo los campos vacíos para el siguiente pergamino
    private fun limpiarCampos() {
        etNombreArchivo.text.clear()
        etContenido.text.clear()
    }
}
