import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class ConsultaTipoDeMoneda {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/2aeeffcf040e24d24d7a6373/latest/";

    private URL construirURL(String monedaBase) throws MalformedURLException {
        return new URL(API_URL + monedaBase);
    }

    private String realizarPeticionHTTP(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    private TipoCambio parsearRespuestaJSON(String jsonRespuesta, String monedaDestino) throws TipoDeMonedaNoEncontradaException {
       // System.out.println("Respuesta JSON completa: " + jsonRespuesta); // Imprime la respuesta completa
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonRespuesta).getAsJsonObject();
           // System.out.println("Objeto JSON parseado: " + jsonObject); // Imprime el objeto JSON

            JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
            //System.out.println("Objeto 'rates': " + rates); // Imprime el objeto 'rates'

            if (rates != null && rates.has(monedaDestino)) {
                double tipoCambio = rates.get(monedaDestino).getAsDouble();
                return new TipoCambio(monedaDestino, tipoCambio);
            } else {
                throw new TipoDeMonedaNoEncontradaException("El tipo de moneda '" + monedaDestino + "' no fue encontrado en la respuesta.");
            }
        } catch (Exception e) {
            throw new TipoDeMonedaNoEncontradaException("Error al parsear la respuesta de la API: " + e.getMessage(), e);
        }
    }

    public TipoCambio obtenerTipoDeCambio(String monedaOrigen, String monedaDestino) throws IOException, TipoDeMonedaNoEncontradaException {
        try {
            URL url = construirURL(monedaOrigen);
            String jsonRespuesta = realizarPeticionHTTP(url);
            return parsearRespuestaJSON(jsonRespuesta, monedaDestino);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al construir la URL", e);
        }
    }
}