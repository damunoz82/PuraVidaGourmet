package org.puravidatgourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.domain.pojo.UserPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

  User toUser(UserPojo user);

  UserPojo toUserPojo(User user);
}
