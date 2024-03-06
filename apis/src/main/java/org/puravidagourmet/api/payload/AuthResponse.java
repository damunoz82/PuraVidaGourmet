package org.puravidagourmet.api.payload;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthResponse {
  private String userName;
  private Date createdAt;
  private String accessToken;
  private String refreshToken;
  private String tokenType;

}
