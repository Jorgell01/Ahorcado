# Ahorcado

## Descripción

Este es un juego de Ahorcado desarrollado en Java utilizando JavaFX para la interfaz gráfica. El objetivo del juego es adivinar una palabra secreta letra por letra antes de que se agoten los intentos. El juego incluye funcionalidades para gestionar puntuaciones de jugadores y una lista de palabras personalizable.

## Funcionalidades

- **Juego de Ahorcado**: Adivina la palabra secreta antes de que se agoten los intentos.
- **Gestión de Puntuaciones**: Guarda y muestra las puntuaciones de los jugadores.
- **Lista de Palabras**: Añade y elimina palabras de la lista utilizada en el juego.
- **Música de Fondo**: Música de fondo durante el juego.
- **Interfaz Gráfica**: Interfaz amigable y fácil de usar.

## Requisitos

- Java 8 o superior
- Maven

## Instalación

1. Clona el repositorio:
    ```sh
    git clone https://github.com/tu-usuario/ahorcado.git
    ```
2. Navega al directorio del proyecto:
    ```sh
    cd ahorcado
    ```
3. Compila el proyecto con Maven:
    ```sh
    mvn clean compile
    ```

## Uso

1. Ejecuta la aplicación:
    ```sh
    mvn exec:java
    ```
2. **Pantalla de Login**: Introduce tu nombre de jugador y haz clic en "Login".
3. **Pantalla Principal**: 
    - **Nueva Partida**: Inicia una nueva partida de Ahorcado.
    - **Palabras**: Añade o elimina palabras de la lista.
    - **Puntuaciones**: Consulta las puntuaciones de los jugadores.
4. **Juego de Ahorcado**:
    - Introduce letras para adivinar la palabra secreta.
    - Si adivinas la palabra, ganas puntos.
    - Si te quedas sin intentos, pierdes puntos.
5. **Finalizar Partida**: Al finalizar una partida, la pestaña de "Palabras" se habilitará para que puedas gestionar la lista de palabras.

## Estructura del Proyecto

- `src/main/java/dad/ahorcado/controllers`: Controladores de la aplicación.
- `src/main/resources/fxml`: Archivos FXML para la interfaz gráfica.
- `src/main/resources/css`: Archivos CSS para los estilos.
- `src/main/resources/music`: Archivos de música de fondo.
- `src/main/resources/images`: Imágenes utilizadas en el juego.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o un pull request para discutir cualquier cambio que desees realizar.
