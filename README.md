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
