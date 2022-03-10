package ApplicationServer.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class ControllerConfiguration {
    private String DataLayerIP = "localhost";
    private String DataLayerPORT = "6969";
    protected final String DataLayerURI = "http://" + DataLayerIP + ":" + DataLayerPORT;
    protected final RestTemplate restUtility = new RestTemplate();
    protected final ObjectMapper jsonMapper = new ObjectMapper();
}
