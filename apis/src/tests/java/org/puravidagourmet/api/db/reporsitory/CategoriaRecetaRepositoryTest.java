package org.puravidagourmet.api.db.reporsitory;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.puravidagourmet.api.db.repository.CategoriaRecetaRepository;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.springframework.jdbc.core.JdbcTemplate;

public class CategoriaRecetaRepositoryTest {

  @Test
  public void findAllTest() {
    // given
    JdbcTemplate template = mock(JdbcTemplate.class);
    CategoriaRecetaRepository categoriaRecetaRepository = new CategoriaRecetaRepository(template);

    // when
    //        when(template.query(anyString(), any())).thenReturn(generateExpectedFindAll());

    // then
    List<CategoriaReceta> result = categoriaRecetaRepository.findAll();
  }

  private List<CategoriaReceta> generateExpectedFindAll() {
    return new ArrayList<>();
  }
}
