# CampoMarket

## Requisitos previos
1. Java JDK 25 o superior
   - Descarga: [Eclipse Temurin JDK](https://adoptium.net/)
   - Instalar y agregar a PATH

2. Apache Maven 3.9.0 o superior
   - Descarga: [Apache Maven](https://maven.apache.org/download.cgi)
   - Descomprimir y agregar la carpeta `bin` al PATH

## Ejecutar la aplicación

### Opción 1: Usando Maven (recomendado)
Esta es la forma más fácil ya que Maven descarga automáticamente todas las dependencias:

```powershell
# Ejecutar la aplicación
mvn clean javafx:run
```

### Opción 2: Usando Java directamente
Si prefieres ejecutar la aplicación manualmente:

1. Descarga el SDK de JavaFX 25
   - Visita [OpenJFX](https://openjfx.io/)
   - Descarga la versión para Windows
   - Descomprime en una ubicación conocida (ejemplo: `C:\javafx-sdk-25.0.1`)

2. Compila el proyecto:
```powershell
mvn clean package
```

3. Ejecuta la aplicación:
```powershell
java --enable-native-access=javafx.graphics --module-path "C:\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp target\classes uniajc.Main
```

## Solución de problemas

### Error: JavaFX runtime components are missing
Este error ocurre cuando Java no puede encontrar los componentes de JavaFX. Soluciones:

1. Usa Maven para ejecutar (opción 1 arriba)
2. Asegúrate de que la ruta al SDK de JavaFX es correcta en el comando `java`
3. Verifica que las dependencias de JavaFX están en el classpath

### Error: Java version
Asegúrate de usar Java 25:
```powershell
java -version
```

Si necesitas cambiar la versión de Java, usa:
```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-25.0.1.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
```