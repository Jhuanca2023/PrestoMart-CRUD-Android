# PrestoMart-CRUD-Android

PrestoMart es una aplicación Android moderna diseñada para la gestión eficiente de inventarios. Permite realizar operaciones CRUD sobre productos y categorías con una interfaz optimizada.

## Instalación y Configuración

1. Levantar el backend
Levantar backend con docker compose
```bash
docker-compose up -d --build
```

Apagar todo los contenedores
```bash
docker compose down
```

2. Instalar dependencias
```bash
bun install
```

3. Crear el archivo .env en la raiz del proyecto
```env
VITE_API_URL=http://localhost:9090
VITE_MERCADOPAGO_PUBLIC_KEY=TU_CLAVE_PUBLICA_DE_MERCADOPAGO
```

4. Levantar el frontend y backend
```bash
bun run dev
```
- backend -> http://localhost:9090
- frontend -> http://localhost:5173

## Arquitectura del Proyecto

```text
├── 📁 assets
├── 📁 core
│   ├── 📁 api
│   │   └── 📄 fitdeskApi.ts
│   ├── 📁 context
│   │   └── 📄 get-strict-context.tsx
│   ├── 📁 hooks
│   │   ├── 📄 useAuth.ts
│   │   ├── 📄 useChat.ts
│   │   └── 📄 useDebounce.ts
│   ├── 📁 interfaces
│   │   ├── 📄 admin-user.interface.ts
│   ├── 📁 lib
│   │   ├── 📄 currency-formatter.ts
│   │   └── 📄 utils.ts
│   ├── 📁 middlewares
│   │   └── 📄 logger.middleware.ts
│   ├── 📁 providers
│   │   ├── 📄 auth-provider.tsx
│   │   └── 📄 theme-provider.tsx
│   ├── 📁 queries
│   │   ├── 📄 useAdminUserQuery.ts
│   │   ├── 📄 useAuthQuery.ts
│   │   ├── 📄 useBillingQuery.ts
│   │   └── 📄 useMemberQuery.ts
│   ├── 📁 routes
│   │   └── 📄 usePrefetchRoutes.ts
│   ├── 📁 services
│   │   ├── 📄 admin-user.service.ts
│   │   ├── 📄 auth.service.ts
│   ├── 📁 store
│   │   ├── 📄 auth.store.ts
│   │   ├── 📄 chat.store.ts
│   │   └── 📄 payment.store.ts
│   ├── 📁 utils
│   │   ├── 📄 auth-helpers.ts
│   │   ├── 📄 card-utils.ts
│   │   ├── 📄 chat-helpers.ts
│   │   ├── 📄 cropImage.ts
│   │   ├── 📄 generate-uuid.ts
│   │   └── 📄 sounds.ts
│   └── 📁 zod
│       ├── 📁 admin
│       │   └── 📄 profile.schemas.ts
│       └── 📄 trainer-configuration.schemas.ts
├── 📁 lib
│   └── 📄 utils.ts
├── 📁 modules
│   ├── 📁 admin
│   │   ├── 📁 analytics
│   │   ├── 📁 billing
│   │   ├── 📁 classes
│   │   ├── 📁 components
│   │   │   └── 📁 ui
│   │   ├── 📁 dashboard
│   │   ├── 📁 members
│   │   ├── 📁 plans
│   │   ├── 📁 profile
│   │   ├── 📁 roles
│   │   └── 📁 trainers
│   ├── 📁 client
│   │   ├── 📁 blog
│   │   ├── 📁 classes
│   │   ├── 📁 components
│   │   │   └── 📁 ui
│   │   ├── 📁 dashboard
│   │   ├── 📁 messages
│   │   ├── 📁 notifications
│   │   ├── 📁 payments
│   │   ├── 📁 profile
│   │   ├── 📁 reserva-clase
│   │   └── 📁 sesiones-personalizadas
│   ├── 📁 shared
│   │   ├── 📁 auth
│   │   ├── 📁 chat
│   │   └── 📁 landing
│   └── 📁 trainer
│       ├── 📁 attendance
│       ├── 📁 calendar
│       ├── 📁 configuration
│       ├── 📁 dashboard
│       ├── 📁 messages
│       ├── 📁 profile
│       └── 📁 students
├── 📁 shared
│   ├── 📁 components
│   │   ├── 📁 animated
│   │   │   ├── 📁 effects
│   │   │   │   ├── � motion-effect.tsx
│   │   │   │   ├── 📄 motion-highlight.tsx
│   │   │   │   └── 📄 theme-toggler.tsx
│   │   ├── 📁 icons
│   │   ├── 📁 ui
│   └── 📁 layouts
├── ⚙️ .editorconfig
├── 📄 App.tsx
├── 📄 app.router.tsx
├── 🎨 index.css
├── 📄 main.tsx
```

## Pantallazos de la Aplicación

### Gestión de Productos
| Listado de Productos | Crear Producto | Detalle de Producto | Eliminar Producto |
| :---: | :---: | :---: | :---: |
| ![Listar](./screenshots/prestomart-listar.png) | ![Crear](./screenshots/prestomart-crear.png) | ![Detalle](./screenshots/prestomart-detalle.png) | ![Eliminar](./screenshots/prestomart-eliminar.png) |

### Gestión de Categorías
| Listado de Categorías | Crear Categoría |
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
Es un patrón de diseño que separa la lógica de la interfaz de usuario de la lógica de negocio.
- **Model**: Representa los datos y la lógica de negocio (Room, Entity).
- **View**: Interfaz de usuario que muestra los datos (Compose Screens).
- **ViewModel**: Actúa como puente, preparando los datos del Model para la View.

### Clean Architecture
Es una arquitectura que divide el proyecto en capas con responsabilidades únicas:
- **Application**: Lógica de la aplicación y ViewModels.
- **Domain**: Reglas de negocio puras, casos de uso e interfaces.
- **Presentation**: UI y componentes visuales.
- **Infrastructure/Data**: Implementación de base de datos, API y repositorios.

### Conceptos Clave en Android
- **AndroidManifest.xml**: Es el archivo de configuración central. Declara componentes (Activities, Services), permisos (Internet, Cámara), y metadatos de la app. Es el "DNI" de la aplicación.
- **Gradle Scripts**: Sistema de construcción que gestiona dependencias (librerías externas), versiones de compilación y empaquetado del APK/AAB.
- **Carpeta res**: Contiene todos los recursos no codificados de la app, como imágenes (drawables), strings (textos), layouts (en XML) y temas/colores.

---
Tareas que me dejaron sobre arquitecturas para el desarrollo móvil Android.
