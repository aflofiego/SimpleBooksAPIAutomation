repo de la API en: https://github.com/vdespa/introduction-to-postman-course/blob/main/simple-books-api.md


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
