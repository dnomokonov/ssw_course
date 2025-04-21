package da4a.service_data.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthServiceClient {
    @Value("${auth.service.url:http://localhost:8081/auth}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;

    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String login(String username, String password) {
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        request.put("password", password);
        Map<String, String> response = restTemplate.postForObject(authServiceUrl + "/login", request, Map.class);
        return response.get("bearer");
    }

    public Map<String, Object> validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(authServiceUrl + "/validate", HttpMethod.POST, entity, Map.class).getBody();
    }
}