package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class CardDtoKeyDeserializer extends KeyDeserializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        // The key is expected to be a JSON string representing a CardDto
        return objectMapper.readValue(key, CardDto.class);
    }
} 