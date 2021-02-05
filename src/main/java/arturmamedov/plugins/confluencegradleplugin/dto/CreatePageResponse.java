package arturmamedov.plugins.confluencegradleplugin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePageResponse {
    private Long id;
    private String type;
    private String title;
}
