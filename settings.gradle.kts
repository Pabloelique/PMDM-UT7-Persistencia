// settings.gradle.kts -> le dice a Gradle de dónde bajar los plugins/librerías
// y qué módulos forman el proyecto (aquí solo ":app").
pluginManagement {
    repositories {
        google()            // repositorio de Google (Android Gradle Plugin, AndroidX...)
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PMDM-UT7-Persistencia"
include(":app")
