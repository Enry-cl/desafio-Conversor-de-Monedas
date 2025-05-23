import java.io.IOException;
import java.util.Scanner;

public class Principal {

    private final ConsultaTipoDeMoneda consultaTipoDeMoneda = new ConsultaTipoDeMoneda();
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("--- Conversor de Monedas ---");
        System.out.println("Seleccione la moneda de origen:");
        System.out.println("1. USD (Dólar estadounidense)");
        System.out.println("2. EUR (Euro)");
        System.out.println("3. GBP (Libra esterlina)");
        System.out.println("4. JPY (Yen japonés)");
        System.out.println("5. CAD (Dólar canadiense)");
        System.out.println("6. AUD (Dólar australiano)");
        System.out.println("7. Salir");
        System.out.print("Ingrese su opción: ");
    }

    public int leerOpcionUsuario() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer
            System.out.print("Ingrese su opción: ");
        }
        return scanner.nextInt();
    }

    public String obtenerMonedaOrigen(int opcion) {
        return switch (opcion) {
            case 1 -> "USD";
            case 2 -> "EUR";
            case 3 -> "GBP";
            case 4 -> "JPY";
            case 5 -> "CAD";
            case 6 -> "AUD";
            default -> null; // Indica una opción inválida
        };
    }

    public String obtenerMonedaDestino() {
        System.out.print("Ingrese la moneda de destino (ej: EUR): ");
        scanner.nextLine(); // Consumir la nueva línea pendiente del nextInt()
        return scanner.nextLine().trim().toUpperCase();
    }

    public double obtenerCantidad() {
        double cantidad = 0.0;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Ingrese la cantidad a convertir: ");
            if (scanner.hasNextDouble()) {
                cantidad = scanner.nextDouble();
                entradaValida = true;
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next(); // Limpiar el buffer
            }
        }
        scanner.nextLine(); // Consumir la nueva línea pendiente del nextDouble()
        return cantidad;
    }

    public void mostrarResultado(double cantidadOriginal, String monedaOrigen, double cantidadConvertida, String monedaDestino) {
        System.out.printf("%.2f %s equivalen a %.2f %s%n", cantidadOriginal, monedaOrigen, cantidadConvertida, monedaDestino);
    }

    public void ejecutarConversion() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcionUsuario();
            String monedaOrigen = obtenerMonedaOrigen(opcion);

            if (monedaOrigen != null) {
                String monedaDestino = obtenerMonedaDestino();
                double cantidad = obtenerCantidad();

                try {
                    TipoCambio tipoCambio = consultaTipoDeMoneda.obtenerTipoDeCambio(monedaOrigen, monedaDestino);
                    double cantidadConvertida = cantidad * tipoCambio.valor();
                    mostrarResultado(cantidad, monedaOrigen, cantidadConvertida, tipoCambio.monedaDestino());
                } catch (IOException e) {
                    System.err.println("Error al obtener el tipo de cambio: " + e.getMessage());
                } catch (TipoDeMonedaNoEncontradaException e) {
                    System.err.println(e.getMessage());
                }
            } else if (opcion != 7) {
                System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
            System.out.println(); // Línea en blanco para mejor legibilidad
        } while (opcion != 7);

        System.out.println("¡Gracias por usar el conversor de monedas!");
        scanner.close();
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.ejecutarConversion();
    }
}