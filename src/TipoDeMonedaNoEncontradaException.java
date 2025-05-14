public class TipoDeMonedaNoEncontradaException extends Exception {
  public TipoDeMonedaNoEncontradaException(String message) {
    super(message);
  }

  public TipoDeMonedaNoEncontradaException(String message, Throwable cause) {
    super(message, cause);
  }
}