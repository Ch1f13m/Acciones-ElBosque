package co.edu.unbosque.accioneselbosqueapi.apiExternal;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class AlpacaClient {

    private final String API_KEY = "TU_API_KEY";
    private final String API_SECRET = "TU_API_SECRET";
    private final String ENDPOINT = "https://broker-api.sandbox.alpaca.markets/v1/accounts";

    public ResponseEntity<String> crearCuentaAlpaca(Map<String, Object> datosCuenta) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("APCA-API-KEY-ID", API_KEY);
        headers.set("APCA-API-SECRET-KEY", API_SECRET);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(datosCuenta, headers);

        return restTemplate.postForEntity(ENDPOINT, request, String.class);
    }
}
