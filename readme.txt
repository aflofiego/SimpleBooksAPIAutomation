# SimpleBooks API Automation

Este proyecto contiene una suite de pruebas automatizadas para validar el comportamiento de la [Simple Books API](https://simple-books-api.glitch.me/).

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **JUnit 5**
- **Rest Assured** – para pruebas sobre API REST
- **Gradle** – gestión de dependencias y construcción del proyecto
- **Allure** – generación de reportes
- **GitHub** – control de versiones

## 🚀 ¿Cómo ejecutar los tests?
gradle clean test

## 🚀 ¿Cómo ver los reportes?
Allure comands (https://allurereport.org/docs/how-it-works/)
allure serve allure-results
allure generate
allure open

### Como clonar el repositorio?

git clone https://github.com/aflofiego/SimpleBooksAPIAutomation.git
cd SimpleBooksAPIAutomation

SimpleBooksAPIAutomation/
├── src/
│   └── test/
│       └── java/
│           └── tests/             # Casos de prueba
│           └── utils/             # Helpers o utilidades
├── build.gradle                    # Configuración de Gradle
├── settings.gradle
├── .gitignore
└── README.md


Instalación de allure reports

Requisitos previos:
- Tener PowerShell instalado (viene por defecto en Windows).
- Ejecutar PowerShell como Administrador (haz clic derecho sobre el ícono y elige "Ejecutar como administrador").
- Tener Git instalado. (Es necesario para algunos buckets de Scoop.)

Pasos para instalar Scoop:
1.) Primero, debes permitir la ejecución de scripts en PowerShell. Ejecuta esto:
    Set-ExecutionPolicy RemoteSigned -scope CurrentUser
    Presiona Y y luego Enter para confirmar.
2.) Instalar Scoop
    Ejecuta este comando:
    irm get.scoop.sh | iex
    Esto descargará e instalará Scoop.
3.) Verificar instalación
    Una vez termine, puedes verificar que Scoop está funcionando con:
    scoop --version
    Deberías ver algo como Scoop vX.Y.Z.
4.) (Opcional) Añadir buckets comunes
    Para agregar herramientas de desarrollo comunes:
    scoop bucket add extras
    scoop bucket add versions
5.) En el mismo PowerShell corre este comando:
    scoop install allure
6.) Para verificar la instalación corre este comando:
    allure --version


Allure comands (https://allurereport.org/docs/how-it-works/)
allure generate
allure open

Notas:
La API de Simple Books es pública, por lo que puede tener variaciones en los datos.
Este proyecto es solo con fines de aprendizaje y/o demostración profesional.

Autor
@aflofiego – https://github.com/aflofiego
