# PrestoMart-CRUD-Android

PrestoMart es un CRUD sencillo que permite controlar productos y categorías con una interfaz intuitiva y funcional, utilizando tecnología nativa.

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/Jhuanca2023/PrestoMart-CRUD-Android.git
```

2. Abrir en Android Studio (Ladybug o superior).
3. Sincronizar Gradle para descargar las dependencias.
4. Ejecutar en un emulador o dispositivo físico.

## Estructura del Proyecto (MVVM)

```text
app/
├── src/
│   └── main/
│       ├── java/com/example/crudprestomart/
│       │   ├── data/                 # Base de datos (Room), Entidades y Repositorios
│       │   ├── ui/                   # Interfaz de usuario (Screens, Components, Theme)
│       │   ├── viewmodel/            # Lógica de negocio y estado de la UI
│       │   └── MainActivity.kt       # Punto de entrada de la aplicación
│       ├── res/                      # Recursos (Imágenes, Textos, Estilos)
│       └── AndroidManifest.xml       # Configuración principal de Android
├── build.gradle.kts                  # Configuración de dependencias
└── settings.gradle.kts                # Configuración de módulos
```

## Pantallazos

### Productos
| Listado | Crear | Detalle | Eliminar |
| :---: | :---: | :---: | :---: |
| ![Listar](./screenshots/prestomart-listar.png) | ![Crear](./screenshots/prestomart-crear.png) | ![Detalle](./screenshots/prestomart-detalle.png) | ![Eliminar](./screenshots/prestomart-eliminar.png) |

### Categorías
| Listado | Crear |
| :---: | :---: |
| ![Categorías Listar](./screenshots/prestomart-categoria-listar.png) | ![Categoría Crear](./screenshots/prestomart-categoria-crear.png) |

## Tecnologías
- **Kotlin**: Lenguaje principal.
- **Jetpack Compose**: Para la interfaz moderna.
- **Room (SQLite)**: Base de datos local.
- **Navigation Compose**: Flujo entre pantallas.
- **Coil**: Carga de imágenes.

## Tarea de Investigación: Arquitectura y Conceptos Android

### Patrones de Arquitectura en Android

*   **MVVM (Model-View-ViewModel):** Separa la lógica de datos de la interfaz. El ViewModel prepara la información para que la vista solo la muestre. Es el estándar actual de Android.
*   **MVC (Model-View-Controller):** El controlador recibe eventos de la vista y actualiza el modelo. Es el patrón clásico pero poco flexible para interfaces complejas.
*   **MVP (Model-View-Presenter):** El presentador maneja la lógica y ordena a la vista qué mostrar exactamente. Mejora la capacidad de realizar pruebas.
*   **MVI (Model-View-Intent):** Basado en flujo unidireccional. El usuario envía un intento, el estado cambia y la vista se actualiza totalmente.

### Clean Architecture

Es un modelo de diseño que divide el sistema en capas con responsabilidades únicas para facilitar el mantenimiento:

*   **Application:** Lógica de la aplicación y ViewModels.
*   **Domain:** Reglas de negocio, entidades puras y casos de uso.
*   **Presentation:** Interfaz de usuario y componentes visuales.
*   **Infrastructure:** Implementación de base de datos y servicios externos.

### Conceptos Fundamentales

*   **AndroidManifest.xml:** Archivo central de configuración donde se registran pantallas, permisos y la identidad de la aplicación.
*   **Gradle Scripts:** Gestiona la construcción del proyecto, dependencias de librerías y configuración de compilación.
*   **Carpeta res:** Almacena recursos estáticos como imágenes (drawables), textos (strings) y colores del sistema.

---
Dudas o sugerencias sobre arquitecturas de desarrollo móvil, escribir a **josehuanca612@gmail.com**. Todo feedback es bienvenido para seguir mejorando.
