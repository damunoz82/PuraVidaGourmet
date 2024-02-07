package org.puravidatgourmet.security.domain.converters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.puravidatgourmet.security.utils.RoleProvider;

@Converter
public class StringListConverter implements AttributeConverter<List<RoleProvider>, String> {

  @Override
  public String convertToDatabaseColumn(List<RoleProvider> list) {
    return list.stream().map(Enum::name).collect(Collectors.joining(","));
  }

  @Override
  public List<RoleProvider> convertToEntityAttribute(String joined) {
    return Arrays.stream(joined.split(",")).map(RoleProvider::valueOf).collect(Collectors.toList());
  }
}
