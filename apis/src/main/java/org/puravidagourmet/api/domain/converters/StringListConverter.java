package org.puravidagourmet.api.domain.converters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.puravidagourmet.api.domain.enums.RoleProvider;

public class StringListConverter {

  public String convertToDatabaseColumn(List<RoleProvider> list) {
    return list.stream().map(Enum::name).collect(Collectors.joining(","));
  }

  public List<RoleProvider> convertToEntityAttribute(String joined) {
    return Arrays.stream(joined.split(",")).map(RoleProvider::valueOf).collect(Collectors.toList());
  }
}
