# PrestoMart-CRUD-Android

PrestoMart es una aplicación Android moderna diseñada para la gestión eficiente de inventarios. Permite realizar operaciones CRUD sobre productos y categorías con una interfaz optimizada, utilizando tecnologías nativas de Android.

## Instalación y Configuración

1. Clonar el repositorio
```bash
git clone https://github.com/Jhuanca2023/PrestoMart-CRUD-Android.git
```

2. Abrir el proyecto
- Abre **Android Studio** (versión Ladybug o superior).
- Selecciona "Open" y busca la carpeta del proyecto.

3. Sincronizar Gradle
- Deja que Android Studio descargue las dependencias y sincronice el proyecto automáticamente.

4. Ejecutar la aplicación
- Conecta un dispositivo físico o inicia un emulador.
- Presiona el botón "Run" (flecha verde) en Android Studio.

## Arquitectura del Proyecto (Estructura de Carpetas)

```text
├── 📁 app
│   └── 📁 src
│       └── � main
│           ├── 📁 java/com/example/crudprestomart
│           │   ├── � data
│           │   │   ├── � local (SQLite/Room DAOs y Database)
│           │   │   ├── � model (Entidades de datos)
│           │   │   └── � repository (Patrón Repositorio)
│           │   ├── 📁 ui
│           │   │   ├── 📁 components (Componentes reutilizables)
│           │   │   ├── 📁 screens (Pantallas de la App)
│           │   │   ├── 📁 theme (Configuración de colores y estilos)
│           │   │   └── 📁 viewmodel (Lógica de negocio para la UI)
│           │   └── � MainActivity.kt
│           ├── 📁 res (Recursos: imágenes, textos, temas)
│           └── 📄 AndroidManifest.xml
├── ⚙️ build.gradle.kts (Scripts de construcción)
└── ⚙️ settings.gradle.kts
```

## Pantallazos de la Aplicación

### Gestión de Productos
| 1. Listado de Productos | 2. Crear Producto | 3. Detalle de Producto | 4. Eliminar Producto |
| :---: | :---: | :---: | :---: |
| ![Listar](./screenshots/prestomart-listar.png) | ![Crear](./screenshots/prestomart-crear.png) | ![Detalle](./screenshots/prestomart-detalle.png) | ![Eliminar](./screenshots/prestomart-eliminar.png) |

### Gestión de Categorías
| 5. Listado de Categorías | 6. Crear Categoría |
| :---: | :---: |
| ![Categorías Listar](./screenshots/prestomart-categoria-listar.png) | ![Categoría Crear](./screenshots/prestomart-categoria-crear.png) |

## Tecnologías Utilizadas
- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Base de Datos Local**: SQLite (Room Persistence Library)
- **Navegación**: Compose Navigation
- **Carga de Imágenes**: Coil

## Teoría y Arquitectura Android

### Arquitectura MVVM (Model-View-ViewModel)
Es un patrón de diseño que separa la lógica de la interfaz de usuario de la lógica de negocio:
- **Model**: Representa los datos y la lógica de negocio (en este proyecto: `data/local` y `data/model`).
- **View**: Interfaz de usuario que muestra los datos (en este proyecto: `ui/screens`).
- **ViewModel**: Actúa como puente, preparando los datos del Model para la View (en este proyecto: `ui/viewmodel`).

### Clean Architecture
Es una arquitectura que divide el proyecto en capas con responsabilidades únicas:
- **Presentation**: UI y componentes visuales (Screens y ViewModels).
- **Domain**: Reglas de negocio puras (Models y Repositorios).
- **Infrastructure/Data**: Implementación técnica (Room Database, DAOs).

### Conceptos Clave en Android
- **AndroidManifest.xml**: Es el archivo de configuración central. Declara las actividades de la app, los permisos necesarios (como acceso a fotos o internet) y la identidad del proyecto. Es indispensable para que Android reconozca la app.
- **Gradle Scripts**: Sistema de construcción automatizado. Sirve para gestionar las librerías externas (dependencias), definir la versión de la app y configurar cómo se empaqueta el archivo final para el celular.
- **Carpeta res**: Almacena todos los recursos visuales y estáticos:
  - `drawable`: Imágenes y formas.
  - `values`: Textos (strings), colores y estilos.
  - `mipmap`: Iconos de la aplicación para diferentes tamaños de pantalla.

---
Tareas que me dejaron sobre arquitecturas para el desarrollo móvil Android.
