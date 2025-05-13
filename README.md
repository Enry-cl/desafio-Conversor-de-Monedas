Proyecto: Conversor de monedas

Definiendo responsabilidades claves en la aplicación:

1. Obtener los tipos de cambio: Esto implica interactuar con la API.
2. Gestionar la entrada del usuario: Obtener la moneda de origen, la moneda de destino a través de un Menú, ademas de solicitar la cantidad a convertir.
3. Realizar la conversión: Aplicar el tipo de cambio a la cantidad ingresada.
4. Mostrar el resultado: Presentar la cantidad convertida al usuario.
5. Dar la opción de continuar o salir.

1. Clase ConsultaTipoDeMoneda:
* Responsabilidad: Esta clase se encarga de obtener los tipos de cambio de la API. El encapsulamiento aquí se centraría en ocultar los detalles de cómo
  se realiza la petición HTTP y cómo se procesa la respuesta de la API.
* Beneficios del encapsulamiento:
* Aislamiento de la lógica de la API: Si en el futuro se decide cambiar de API, solo habría que modificar esta clase, sin afectar al resto de la aplicación
  (como la clase Principal).
* Mayor facilidad de prueba: Simular (mockear) esta clase en pruebas unitarias sin tener que hacer llamadas reales a la API.
* Mayor claridad en la clase Principal: La clase Principal solo necesitaría llamar a un método de ConsultaTipoDeMoneda
  (por ejemplo, obtenerTipoDeCambio(String moneda)) sin preocuparse por los detalles de la conexión HTTP.

2. Estructura completa del proyecto
* Clases: Principal, ConsultaTipoDeMoneda y el record TipoCambio.java(un record para representar el tipo de cambio),
  TipoDeMonedaNoEncontradaException.java (una excepción personalizada):
* Clase Principal: Esta clase se encarga de la interacción con el usuario (mostrar el menú, leer la entrada) y de orquestar el flujo de la aplicación.

3. Puntos clave en este código:
* Encapsulamiento en ConsultaTipoDeMoneda:
    * Los detalles de la construcción de la URL, la realización de la petición HTTP y el parseo del JSON están ocultos dentro de métodos privados.
    * La clase Principal solo interactúa con el método público obtenerTipoDeCambio, sin necesidad de conocer los detalles internos.
    * Se maneja la excepción específica TipoDeMonedaNoEncontradaException, lo que permite a la clase Principal reaccionar de manera más específica
      a este error.
* Encapsulamiento en Principal:
    * La lógica para mostrar el menú, leer la entrada del usuario (la opción de moneda, la moneda destino y la cantidad) se encuentra en métodos separados,
      lo que hace que la clase sea más organizada y fácil de entender.
    * El método ejecutarConversion orquesta el flujo principal de la aplicación, utilizando los otros métodos para realizar tareas específicas.
* Uso del record TipoCambio:
    * Se utiliza para almacenar de forma concisa el tipo de cambio obtenido de la API. Los campos monedaDestino y valor son directamente accesibles
      (aunque inmutables).
* Manejo de excepciones:
    * Se incluye una excepción personalizada TipoDeMonedaNoEncontradaException para manejar los casos en que la API no encuentra la moneda solicitada.
    * La clase Principal maneja las posibles IOException y TipoDeMonedaNoEncontradaException que pueden ocurrir durante la consulta a la API.
 
Enrique Hernández Gómez
