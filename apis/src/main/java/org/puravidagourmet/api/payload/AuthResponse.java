package org.puravidagourmet.api.payload;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
  private String userName;
  private Date createdAt;
  private String accessToken;
  private String refreshToken;
  private String tokenType;
}
