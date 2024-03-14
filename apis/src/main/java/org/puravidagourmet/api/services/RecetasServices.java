package org.puravidagourmet.api.services;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.*;

import com.google.common.base.Strings;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.IngredienteRepository;
import org.puravidagourmet.api.db.repository.ProductoRepository;
import org.puravidagourmet.api.db.repository.RecetaRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Ingrediente;
import org.puravidagourmet.api.domain.entity.Producto;
import org.puravidagourmet.api.domain.entity.Receta;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.utils.MathUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecetasServices {

  private final RecetaRepository recetaRepository;

  private final IngredienteRepository ingredienteRepository;

  private final ProductoRepository productoRepository;

  private final UsuarioRepository usuarioRepository;

  public RecetasServices(
      RecetaRepository recetaRepository,
      IngredienteRepository ingredienteRepository,
      ProductoRepository productoRepository,
      UsuarioRepository usuarioRepository) {
    this.recetaRepository = recetaRepository;
    this.ingredienteRepository = ingredienteRepository;
    this.productoRepository = productoRepository;
    this.usuarioRepository = usuarioRepository;
  }

  @Transactional
  public Receta saveReceta(Receta receta, UserPrincipal userPrincipal) {

    // get current user
    Optional<Usuario> user = usuarioRepository.findByEmail(userPrincipal.getUsername());

    // if updating
    if (receta.getId() > 0) {
      // validate update
      validateUpdate(receta);

      // check exists.
      Optional<Receta> dbReceta = recetaRepository.findById(receta.getId());
      if (dbReceta.isEmpty()) {
        throw new PuraVidaExceptionHandler(RECETA_REC003, receta.getId());
      }

      // set updating user
      receta.setUsuarioModifica(
          user.orElseThrow(() -> new RuntimeException("Updating user not found")));

      // clean up ingredientes
      ingredienteRepository.deleteForReceta(dbReceta.orElseThrow().getId());

    } else {
      // is creating

      // validate create
      validateSave(receta);

      // set creating user
      receta.setUsuarioRegistra(
          user.orElseThrow(() -> new RuntimeException("Creating user not found")));
    }

    calculateCostsForReceipe(receta);

    Receta saved = recetaRepository.save(receta);

    // save ingredientes
    ingredienteRepository.save(receta.getIngredientes(), saved.getId());

    return saved;
  }

  public List<Receta> getAll(String categoria) {
    if (!Strings.isNullOrEmpty(categoria)) {
      return recetaRepository.findByCategoriaRecetaNombre(categoria);
    }
    return recetaRepository.findAll();
  }

  public Optional<Receta> get(long id) {
    Optional<Receta> receta = recetaRepository.findById(id);
    // load ingredientes
    receta.orElseThrow().setIngredientes(ingredienteRepository.findIngredientes(id));
    return receta;
  }

  public void delete(long id) {
    validateDelete(id);
    ingredienteRepository.deleteForReceta(id);
    recetaRepository.delete(id);
  }

  private void validateSave(Receta receta) {
    Optional<Receta> dbReceta = recetaRepository.findByNombre(receta.getNombre());

    if (dbReceta.isPresent()) {
      throw new PuraVidaExceptionHandler(RECETA_REC001);
    }

    validateIngredientes(receta.getIngredientes());
  }

  private void validateUpdate(Receta receta) {
    Optional<Receta> dbReceta = recetaRepository.findByNombre(receta.getNombre());

    if (dbReceta.isPresent() && dbReceta.get().getId() != receta.getId()) {
      throw new PuraVidaExceptionHandler(RECETA_REC001);
    }

    validateIngredientes(receta.getIngredientes());
  }

  private void validateDelete(long id) {
    // fixme finish
    // check if receta is been used in any menu.
  }

  private void validateIngredientes(List<Ingrediente> ingredientes) {
    ingredientes.forEach(
        i -> {
          productoRepository
              .findById(i.getProducto().getId())
              .orElseThrow(() -> new PuraVidaExceptionHandler(RECETA_REC002));
        });
  }

  private void calculateCostsForReceipe(Receta receta) {

    // calculo del costo de la receta
    receta.setCostoReceta(
        MathUtils.round(
            receta.getIngredientes().stream()
                .map(
                    i -> {
                      Producto producto =
                          productoRepository
                              .findById(
                                  i.getProducto()
                                      .getId()) // fixme se esta haciendo dos llamadas a la DB en el
                                                // mismo proceso, mejorar.
                              .orElseThrow(() -> new PuraVidaExceptionHandler(RECETA_REC002));

                      return i.getCantidad() * producto.getCosteUnitario();
                    })
                .reduce((float) 0, Float::sum),
            2));

    // calculo del costo por porcion
    receta.setCostoPorcion(
        MathUtils.round(receta.getCostoReceta() / receta.getNumeroPorciones(), 2));

    // calculo del margen de ganancia
    float impuestos = receta.getImpuestos() * receta.getPrecioDeVenta();
    receta.setMargenGanancia(
        MathUtils.round(
            1 - ((receta.getCostoPorcion() - impuestos) / receta.getPrecioDeVenta()), 2));
  }
}
