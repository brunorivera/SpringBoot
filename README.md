# 📝 Sistema de Gestión de Tareas - Spring Boot

## 📖 Descripción del Proyecto
Aplicación desarrollada en Spring Boot que permite gestionar tareas (To-Do List) aplicando los conceptos fundamentales del framework: inyección de dependencias, estereotipos, configuración mediante properties y gestión de diferentes entornos con profiles.

Este proyecto implementa un sistema completo de gestión de tareas con almacenamiento en memoria, permitiendo agregar, listar, completar y obtener estadísticas de las tareas, adaptándose a diferentes entornos (desarrollo y producción) mediante Spring Profiles.

## ⚙️ Tecnologías Utilizadas
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.0** - Framework principal
- **Maven** - Gestión de dependencias y construcción del proyecto
- **Spring Boot DevTools** - Herramientas de desarrollo para recarga automática
- **Lombok** - Reducción de código boilerplate (opcional)
- **Spring Context** - Contenedor IoC y gestión de beans

## 🏗️ Arquitectura del Proyecto

### Estructura de Paquetes
```
com.utn.tareas/
├── TareasApplication.java          # Clase principal con CommandLineRunner
├── model/                          # Capa de dominio
│   ├── Prioridad.java             # Enum: ALTA, MEDIA, BAJA
│   └── Tarea.java                 # Entidad principal
├── repository/                     # Capa de persistencia
│   └── TareaRepository.java       # Gestión de datos en memoria
└── service/                        # Capa de lógica de negocio
    ├── MensajeService.java        # Interface de mensajes
    ├── MensajeDevService.java     # Mensajes para desarrollo
    ├── MensajeProdService.java    # Mensajes para producción
    └── TareaService.java          # Lógica de negocio de tareas
```

### Componentes Principales

#### 📦 Model (Modelo de Dominio)
- **Prioridad.java**: Enumeración que define los niveles de prioridad (ALTA, MEDIA, BAJA)
- **Tarea.java**: Clase que representa una tarea con atributos: id, descripción, estado de completitud y prioridad

#### 💾 Repository (Capa de Persistencia)
- **TareaRepository.java**: Componente anotado con `@Repository` que gestiona el almacenamiento en memoria usando `List<Tarea>` y genera IDs automáticos mediante `AtomicLong`

#### 🔧 Service (Lógica de Negocio)
- **TareaService.java**: Servicio principal con métodos para agregar, listar, filtrar y obtener estadísticas. Incluye validación de límite máximo de tareas mediante configuración externa
- **MensajeService.java**: Interfaz que define el contrato para los mensajes del sistema
- **MensajeDevService.java**: Implementación activa en perfil `dev` con mensajes detallados y amigables
- **MensajeProdService.java**: Implementación activa en perfil `prod` con mensajes simples y concisos

## 🚀 Instrucciones para Clonar y Ejecutar

### Prerrequisitos
- Java Development Kit (JDK) 17 o superior instalado
- Maven 3.6+ instalado (o usar el Maven Wrapper incluido)
- Git instalado

### 1️⃣ Clonar el Repositorio
```bash
git clone https://github.com/brunorivera/tareas-spring-boot.git
cd tareas-spring-boot
```

### 2️⃣ Compilar el Proyecto
```bash
# Usando Maven instalado
mvn clean install

# O usando Maven Wrapper (incluido en el proyecto)
# En Windows:
mvnw.cmd clean install

# En Linux/Mac:
./mvnw clean install
```

### 3️⃣ Ejecutar la Aplicación

#### Opción A: Usando Maven
```bash
mvn spring-boot:run
```

#### Opción B: Usando Maven Wrapper
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Opción C: Ejecutar el JAR generado
```bash
java -jar target/tareas-0.0.1-SNAPSHOT.jar
```

## 🔧 Cómo Cambiar Entre Profiles (DEV/PROD)

Spring Boot permite configurar diferentes comportamientos según el entorno mediante **profiles**. Este proyecto incluye dos profiles: `dev` y `prod`.

### Método 1: Modificar application.properties (Recomendado para desarrollo)

Edita el archivo `src/main/resources/application.properties`:
```properties
# Para entorno de desarrollo
spring.profiles.active=dev

# Para entorno de producción
spring.profiles.active=prod
```

### Método 2: Parámetro en línea de comandos
```bash
# Ejecutar con perfil DEV
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev

# Ejecutar con perfil PROD
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
```

### Método 3: Variable de entorno
```bash
# Windows (CMD)
set SPRING_PROFILES_ACTIVE=prod
mvn spring-boot:run

# Windows (PowerShell)
$env:SPRING_PROFILES_ACTIVE="prod"
mvn spring-boot:run

# Linux/Mac
export SPRING_PROFILES_ACTIVE=prod
mvn spring-boot:run
```

### Método 4: Al ejecutar el JAR
```bash
java -jar target/tareas-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## ⚙️ Diferencias Entre Profiles

| Característica | Profile DEV | Profile PROD |
|----------------|-------------|--------------|
| Máximo de tareas | 10 | 1000 |
| Mostrar estadísticas | ✅ Sí | ❌ No |
| Nivel de logs | DEBUG | ERROR |
| Mensajes | Detallados y amigables | Concisos y profesionales |
| Bean activo | `MensajeDevService` | `MensajeProdService` |

## 📸 Capturas de Pantalla

### Ejecución con Profile DEV

![Ejecución DEV](capturas/ejecucion-dev.png)

**Características visibles:**
- Mensaje de bienvenida detallado con emojis
- Configuración mostrando límite de 10 tareas
- Logs en nivel DEBUG
- Estadísticas completas
- Mensaje de despedida amigable
```
🚀 ¡Bienvenido al entorno de DESARROLLO!
💻 Modo Debug activado - Logs detallados habilitados
=====================================

⚙️  Configuración:
   Nombre: Sistema de Gestión de Tareas
   Máximo de tareas: 10
   Mostrar estadísticas: true

📋 Tareas iniciales:
Tarea{id=1, descripcion='Estudiar Spring Boot', completada=false, prioridad=ALTA}
...

📊 Estadísticas:
   Total de tareas: 5
   Completadas: 1
   Pendientes: 4

👋 ¡Hasta luego! Entorno de desarrollo
🔧 Recuerda: estás en modo DEV
```

### Ejecución con Profile PROD

![Ejecución PROD](capturas/ejecucion-prod.png)

**Características visibles:**
- Mensaje de bienvenida simple y profesional
- Configuración mostrando límite de 1000 tareas
- Logs en nivel ERROR (solo errores críticos)
- Sin estadísticas detalladas
- Mensaje de despedida conciso
```
Sistema iniciado - Producción

⚙️  Configuración:
   Nombre: Sistema de Gestión de Tareas
   Máximo de tareas: 1000
   Mostrar estadísticas: false

📋 Tareas iniciales:
Tarea{id=1, descripcion='Estudiar Spring Boot', completada=false, prioridad=ALTA}
...

Sistema finalizado
```

## 🎓 Conceptos de Spring Boot Aplicados

### 1. Application Context
El contenedor IoC de Spring gestiona el ciclo de vida de todos los beans (TareaRepository, TareaService, MensajeService).

### 2. Inyección de Dependencias
Implementada mediante constructor (buena práctica):
```java
@Service
public class TareaService {
    private final TareaRepository repository;
    
    // Spring inyecta automáticamente TareaRepository
    public TareaService(TareaRepository repository) {
        this.repository = repository;
    }
}
```

### 3. Estereotipos de Spring
- `@SpringBootApplication`: Marca la clase principal y habilita autoconfiguración
- `@Service`: Define componentes de lógica de negocio
- `@Repository`: Define componentes de acceso a datos
- `@Component`: Estereotipo genérico (base de los demás)

### 4. Configuración Externa con @Value
```java
@Value("${app.max-tareas}")
private int maxTareas;
```
Permite inyectar valores desde archivos `.properties` sin hardcodear constantes.

### 5. Profiles para Múltiples Entornos
```java
@Service
@Profile("dev")
public class MensajeDevService implements MensajeService {
    // Solo se instancia si el profile activo es "dev"
}
```

### 6. CommandLineRunner
Interface que permite ejecutar código al iniciar la aplicación:
```java
@Override
public void run(String... args) throws Exception {
    // Código que se ejecuta automáticamente al iniciar
}
```

## 💭 Conclusiones Personales

### Aprendizajes Clave

Durante el desarrollo de este trabajo práctico, profundicé en los conceptos fundamentales de Spring Boot y comprendí cómo este framework simplifica significativamente el desarrollo de aplicaciones empresariales en Java.

**Inversión de Control (IoC):** El concepto más revolucionario que aprendí fue cómo Spring gestiona automáticamente la creación y el ciclo de vida de los objetos. En lugar de usar `new` para instanciar clases, Spring se encarga de todo mediante el Application Context. Esto elimina el acoplamiento entre componentes y facilita enormemente el testing.

**Inyección de Dependencias:** Implementar la inyección por constructor me permitió entender cómo desacoplar componentes. Por ejemplo, `TareaService` no necesita saber cómo se instancia `TareaRepository`; simplemente lo recibe como dependencia. Esto hace el código más mantenible y testeable.

**Estereotipos y Separación de Responsabilidades:** La arquitectura en capas (model, repository, service) con anotaciones especializadas (`@Service`, `@Repository`) resultó en un código mucho más organizado. Cada clase tiene una responsabilidad clara y única, siguiendo el principio SOLID de responsabilidad única.

**Profiles:** Una de las funcionalidades más útiles fue poder configurar diferentes comportamientos según el entorno sin modificar código. El mismo JAR puede ejecutarse en desarrollo con logs detallados y límites bajos para pruebas, o en producción con configuración optimizada. Esto es invaluable en aplicaciones reales.

**Configuración Externa:** El uso de `@Value` y archivos `.properties` me mostró la importancia de externalizar la configuración. Cambiar parámetros sin recompilar el código es una práctica profesional esencial.

### Desafíos Enfrentados

1. **Configuración inicial de Maven:** Al principio tuve problemas con las dependencias de Spring Boot. Comprendí la importancia de verificar el `pom.xml` y forzar la descarga de librerías con `mvn clean install`.

2. **Estructura de paquetes:** Inicialmente confundí la creación de packages (carpetas) con la creación de clases Java. Entendí que los packages se crean con notación de puntos: `com.utn.tareas.service`.

3. **Beans condicionales con @Profile:** Me costó entender cómo Spring selecciona qué bean instanciar cuando hay múltiples implementaciones de la misma interfaz. Los profiles resuelven este problema elegantemente.

4. **CommandLineRunner:** Implementar esta interfaz me permitió probar toda la funcionalidad al iniciar la aplicación, lo cual es excelente para demos y verificaciones rápidas.

### Aplicabilidad en el Mundo Real

Este proyecto, aunque académico, refleja patrones de diseño utilizados en aplicaciones empresariales reales:
- La separación en capas es estándar en proyectos Java profesionales
- Los profiles son esenciales para CI/CD (desarrollo → staging → producción)
- La inyección de dependencias facilita el testing con mocks
- La externalización de configuración permite deploy en diferentes ambientes

### Próximos Pasos de Aprendizaje

1. **Persistencia con JPA/Hibernate:** Reemplazar el repositorio en memoria por una base de datos real (PostgreSQL, MySQL)
2. **API REST:** Exponer las funcionalidades mediante endpoints HTTP con `@RestController`
3. **Validaciones:** Implementar Bean Validation con `@Valid`, `@NotNull`, etc.
4. **Testing:** Crear pruebas unitarias con JUnit 5 y Mockito
5. **Seguridad:** Agregar Spring Security para autenticación y autorización
6. **Documentación de API:** Integrar Swagger/OpenAPI

### Reflexión Final

Spring Boot eliminó la complejidad de configuración que tenía Spring Framework tradicional (XML, configuraciones manuales) y permite enfocarse en la lógica de negocio. La filosofía de "convención sobre configuración" hace que el framework sea intuitivo: solo necesitas anotar las clases correctamente y Spring hace el resto.

Este trabajo me dio una base sólida para desarrollar aplicaciones Spring Boot profesionales y entender cómo funcionan internamente los frameworks de inyección de dependencias.

## 👤 Información del Autor

- **Nombre:** Bruno Rivera
- **Legajo:** 52747
- **Carrera:** Tecnicatura Universitaria en Programación
- **Materia:** Programación III
- **Institución:** Universidad Tecnológica Nacional (UTN)
- **Año Académico:** 2025

## 📚 Referencias y Recursos

- [Documentación Oficial de Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Guías de Spring](https://spring.io/guides)
- [Baeldung - Spring Tutorials](https://www.baeldung.com/spring-tutorial)
- [Maven Central Repository](https://mvnrepository.com/)
- [Spring Initializr](https://start.spring.io/)


**Universidad Tecnológica Nacional - Tecnicatura Universitaria en Programación**  
*Trabajo Práctico - Fundamentos de Spring Boot*  
**2025**
