package org.puravidagourmet.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.puravidagourmet.api.db.repository.DepartamentoRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Departamento;
import org.puravidagourmet.api.domain.entity.Usuario;

public class DepartamentoServiceTest {

  private DepartamentoRepository departamentoRepository = mock(DepartamentoRepository.class);
  private UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);

  private DepartamentoService departamentoService =
      new DepartamentoService(departamentoRepository, usuarioRepository);

  @Test
  public void getDepartamentoTest() {
    // given
    when(departamentoRepository.findById(anyLong())).thenReturn(createOptionalDepartamento());

    // when
    Optional<Departamento> result = departamentoService.getDepartamento(1);

    // then
    assertTrue(result.isPresent());
  }

  @Test
  public void getAllTest() {
    // given
    when(departamentoRepository.findAll()).thenReturn(List.of(createSingleDepartamento()));

    // when
    List<Departamento> result = departamentoService.getAll();

    // then
    assertEquals(1, result.size());
  }

  @Test
  public void saveDepartamentoTest() {
    // given
    Departamento expected = createSingleDepartamento();
    when(usuarioRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(Usuario.builder().email("test@test.com").build()));
    when(departamentoRepository.save(any())).thenReturn(expected);

    // when
    Departamento result =
        departamentoService.saveDepartamento(
            Departamento.builder()
                .responsable(Usuario.builder().email("test@test.com").build())
                .build());

    // then
    assertEquals(1, result.getId());
  }

  @Test
  public void saveDepartamentoTest_NoUser() {
    // given
    when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(departamentoRepository.save(any())).thenReturn(createSingleDepartamento());

    // when
    Exception exception = null;
    try {
      departamentoService.saveDepartamento(
          Departamento.builder()
              .responsable(Usuario.builder().email("test@test.com").build())
              .build());
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNotNull(exception);
    assertTrue(exception.getMessage().contains("Usuario"));
  }

  @Test
  public void saveDepartamentoTest_Create_happypath() {
    // given
    Departamento expected = createSingleDepartamento();
    when(usuarioRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(Usuario.builder().email("test@test.com").build()));
    when(departamentoRepository.save(any())).thenReturn(expected);

    // when
    Exception exception = null;
    Departamento result = null;
    try {
      result =
          departamentoService.saveDepartamento(
              Departamento.builder()
                  .responsable(Usuario.builder().email("test@test.com").build())
                  .build());
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNull(exception);
    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  public void saveDepartamentoTest_Create_alreadyExists() {
    // given
    Departamento expected = createSingleDepartamento();
    when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(departamentoRepository.save(any())).thenReturn(expected);
    when(departamentoRepository.findByNombre(any())).thenReturn(createOptionalDepartamento());

    // when
    Exception exception = null;
    Departamento result = null;
    try {
      result =
          departamentoService.saveDepartamento(
              Departamento.builder()
                  .responsable(Usuario.builder().email("test@test.com").build())
                  .build());
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNull(result);
    assertNotNull(exception);
    assertTrue(exception.getMessage().contains("Ya existe un departamento con ese nombre"));
  }

  @Test
  public void saveDepartamentoTest_Update_happypath() {
    // given
    Departamento expected = createSingleDepartamento();
    when(usuarioRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(Usuario.builder().email("test@test.com").build()));
    when(departamentoRepository.save(any())).thenReturn(expected);
    when(departamentoRepository.findByNombre(any())).thenReturn(createOptionalDepartamento());

    // when
    Exception exception = null;
    Departamento result = null;
    try {
      result = departamentoService.saveDepartamento(createSingleDepartamento());
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNull(exception);
    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  public void saveDepartamentoTest_Update_alreadyExists() {
    // given
    Departamento expected = createSingleDepartamento();
    when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(departamentoRepository.save(any())).thenReturn(expected);
    when(departamentoRepository.findByNombre(any())).thenReturn(createOptionalDepartamento());

    // when
    Exception exception = null;
    Departamento result = null;
    try {
      result =
          departamentoService.saveDepartamento(
              Departamento.builder()
                  .nombre("test")
                  .responsable(Usuario.builder().email("test@test.com").build())
                  .build());
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNull(result);
    assertNotNull(exception);
    assertTrue(exception.getMessage().contains("Ya existe un departamento con ese nombre"));
  }

  private Optional<Departamento> createOptionalDepartamento() {
    return Optional.of(createSingleDepartamento());
  }

  private Departamento createSingleDepartamento() {
    Usuario responsable = Usuario.builder().email("test@test.com").name("Test").build();
    return Departamento.builder().id(1).nombre("test").responsable(responsable).build();
  }
}
