package kanfu.old_city_memo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String id;
    private String username;
    private String role;
    private String token;
    private String type;
    
}
