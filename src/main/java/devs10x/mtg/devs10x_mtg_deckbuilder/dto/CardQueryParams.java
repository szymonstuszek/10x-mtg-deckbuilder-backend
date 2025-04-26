package devs10x.mtg.devs10x_mtg_deckbuilder.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to encapsulate all possible query parameters for card searches
 */
@Data
@NoArgsConstructor
public class CardQueryParams {
    private String set;
    // Add more parameters as needed in the future, like:
    // private String name;
    // private String type;
    // private String rarity;

    /**
     * Utility method to check if this object has any parameters set
     */
    public boolean isEmpty() {
        return (set == null || set.trim().isEmpty()); 
        // When adding new parameters, update this method like:
        // return (set == null || set.trim().isEmpty()) &&
        //        (name == null || name.trim().isEmpty()) &&
        //        ...
    }

    /**
     * Convert the non-null query parameters to a map for use with UriComponentsBuilder
     */
    public Map<String, String> toQueryParamsMap() {
        Map<String, String> paramsMap = new HashMap<>();
        
        if (set != null && !set.trim().isEmpty()) {
            paramsMap.put("set", set.trim());
        }
        
        // Add more parameters as they're introduced
        // if (name != null && !name.trim().isEmpty()) {
        //     paramsMap.put("name", name.trim());
        // }
        
        return paramsMap;
    }
} 