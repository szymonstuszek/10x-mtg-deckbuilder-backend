package devs10x.mtg.devs10x_mtg_deckbuilder.service;

import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardListResponseDto;
import devs10x.mtg.devs10x_mtg_deckbuilder.dto.CardQueryParams;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Service
public class MtgApiServiceImpl implements MtgApiService {

    @Value("${mtg.api.baseUrl}")
    private String mtgApiBaseUrl; // e.g., http://api.mtg.com/cards

    private final RestTemplate restTemplate;

    public MtgApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CardListResponseDto fetchCards() {
        ResponseEntity<CardListResponseDto> response = restTemplate.exchange(
            mtgApiBaseUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<CardListResponseDto>() {}
        );
        return response.getBody();
    }
    
    @Override
    public CardListResponseDto fetchCards(CardQueryParams queryParams) {
        // If no parameters provided, use the default method
        if (queryParams == null || queryParams.isEmpty()) {
            return fetchCards();
        }
        
        // Build URI with parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(mtgApiBaseUrl);
        
        // Add all parameters from the map
        Map<String, String> paramsMap = queryParams.toQueryParamsMap();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        
        URI uri = builder.build().toUri();
            
        ResponseEntity<CardListResponseDto> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<CardListResponseDto>() {}
        );
        return response.getBody();
    }
} 