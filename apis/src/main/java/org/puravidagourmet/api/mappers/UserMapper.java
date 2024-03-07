package org.puravidagourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.User;
import org.puravidagourmet.api.domain.pojo.UserPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

  User toUser(UserPojo user);

  UserPojo toUserPojo(User user);
}
