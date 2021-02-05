package arturmamedov.plugins.confluencegradleplugin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfluenceRequest {
    private Long id;
    private Version version;
    private String type;
    private String title;
    private Map<String,String> space;
    private Body body;
    private List<Ancestors> ancestors;

    @Data
    @AllArgsConstructor
    public static class Body {
        private Storage storage;
    }

    @Data
    @Builder
    public static class Storage {
        String value;
        String representation;
    }


    @Data
    @Builder
    public static class Ancestors {
        private String type;
        private Long id;
    }
}
