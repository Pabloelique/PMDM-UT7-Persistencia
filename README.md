# PMDM · UT7 — Persistencia de Datos

**Pablo Jiménez Jorquera** · 2º DAM · Programación Multimedia y Dispositivos Móviles · IES Alfonso X

Proyecto Android en Kotlin (paquete `com.ies.iax.prgmdm`). Cada laboratorio está en su propia rama:

| Rama | Laboratorio | Qué se trabaja |
|------|-------------|----------------|
| `lab1` | El Registro de Guardias de la Ciudadela | `SharedPreferences` (`RegistroGuardia.xml`), `.apply()`, carga en `onResume()` |
| `lab2` | Los Pergaminos de la Ciudadela Blanca | Ficheros internos (`openFileOutput`/`openFileInput` + `.use {}`) y externos privados (`getExternalFilesDir`) |
| `lab3` | (pendiente de enunciado) | — |

```bash
git checkout lab1   # o lab2 / lab3
```

Abrir la carpeta con Android Studio → *Sync Gradle* → *Run* sobre un emulador.

---

## Lab 1 — El Registro de Guardias de la Ciudadela (esta rama)

`PreferenciasActivity.kt` guarda los datos del soldado con `SharedPreferences` en `RegistroGuardia.xml` y los carga en `onResume()`.

| Control | Tipo | Clave | Valores |
|---------|------|-------|---------|
| Nombre (`EditText`) | `String` | `KEY_SOLDADO` | Texto, no vacío |
| Rango (`Spinner`, sale de `strings.xml`) | `Int` | `KEY_RANGO` | 0 Recluta · 1 Capitán · 2 Veterano |
| Estado (`Switch`) | `Boolean` | `KEY_ESTADO` | `true` En Guardia · `false` En Reposo |

**Prueba:** Aragorn · Veterano · En Guardia → *Guardar registro* → cerrar la app desde el administrador de tareas → al volver a abrirla el formulario sale relleno.

El fichero está en *Device File Explorer* → `data/data/com.ies.iax.prgmdm/shared_prefs/RegistroGuardia.xml`.
