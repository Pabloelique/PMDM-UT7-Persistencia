package com.ies.iax.prgmdm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad de la rama main.
 * No hace nada especial: solo carga el layout con la explicación de las ramas.
 * El código de cada laboratorio está en su propia rama (lab1, lab2, lab3).
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
