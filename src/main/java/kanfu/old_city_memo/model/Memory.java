package kanfu.old_city_memo.model;

import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "memo")
@Data
public class Memory {
    @Id
    private String id;

    private String title;
    private Integer year;
    private String description;
    @NotBlank
    private Double lat;
    @NotBlank
    private Double lon;
    @NotBlank
    private String username;
    private List<String> picturePath;
    
}
