package org.puravidatgourmet.api.services;

import com.google.common.base.Strings;
import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.api.db.repository.IngredienteRepository;
import org.puravidatgourmet.api.db.repository.ProductoRepository;
import org.puravidatgourmet.api.db.repository.RecetaRepository;
import org.puravidatgourmet.api.domain.entity.Ingrediente;
import org.puravidatgourmet.api.domain.entity.Producto;
import org.puravidatgourmet.api.domain.entity.Receta;
import org.puravidatgourmet.api.domain.pojo.RecetaPojo;
import org.puravidatgourmet.api.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecetasServices {

  @Autowired private RecetaRepository recetaRepository;

  @Autowired private IngredienteRepository ingredienteRepository;

  @Autowired private ProductoRepository productoRepository;

  @Transactional
  public Receta saveReceta(Receta receta) {
    // Get Receta from DB
    Optional<Receta> dbReceta = recetaRepository.findById(receta.getId());

    // cleanup
    dbReceta.ifPresent(
        value -> value.getIngredientes().forEach(i -> ingredienteRepository.delete(i)));

    // validate products exists and calculate costs.
    validateAndCalculateCostsForReceipe(receta);

    // make sure ingredients are in the DB
    for (Ingrediente ingrediente : receta.getIngredientes()) {
      ingredienteRepository.save(ingrediente);
    }

    return recetaRepository.save(receta);
  }

  public List<Receta> getAll(String categoria) {
    if (!Strings.isNullOrEmpty(categoria)) {
      return recetaRepository.findByCategoriaRecetaNombre(categoria);
    }
    return recetaRepository.findAll();
  }

  public Optional<Receta> get(long id) {
    return recetaRepository.findById(id);
  }

  public void delete(long id) {
    recetaRepository.deleteById(id);
  }

  public void validateSave(RecetaPojo receta) {
    Receta dbReceta = recetaRepository.findByNombre(receta.getNombre());

    if (dbReceta != null) {
      throw new BadRequestException("Ya existe una receta con ese nombre");
    }

    //    validateIngredientes(receta.getIngredientes());
  }

  public void validateUpdate(RecetaPojo receta) {
    Receta dbReceta = recetaRepository.findByNombre(receta.getNombre());

    if (dbReceta != null && dbReceta.getId() != receta.getId()) {
      throw new BadRequestException(
          "Ya existe una receta con ese nombre - escoge otro nombre para actualizar");
    }

    //    validateIngredientes(receta.getIngredientes());
  }

  public void validateDelete(long id) {
    // fixme finish
    // check if receta is been used in any menu.
  }

  //  private void validateIngredientes(List<IngredientePojo> ingredientes) {
  //    ingredientes.forEach(
  //        i -> {
  //          productoRepository
  //              .findById(i.getProducto().getId())
  //              .orElseThrow(
  //                  () -> new BadRequestException("Ingrediente con materia prima inexistente."));
  //        });
  //  }

  private void validateAndCalculateCostsForReceipe(Receta receta) {

    // calculo del costo de la receta
    receta.setCostoReceta(
        receta.getIngredientes().stream()
            .map(
                i -> {
                  Producto producto =
                      productoRepository
                          .findById(i.getProducto().getId())
                          .orElseThrow(
                              () ->
                                  new BadRequestException(
                                      "Ingrediente con materia prima inexistente."));

                  return i.getCantidad() * producto.getCosteUnitario();
                })
            .reduce((float) 0, Float::sum));

    // calculo del costo por porcion
    receta.setCostoPorcion(receta.getCostoReceta() / receta.getNumeroPorciones());

    // calculo del margen de ganancia
    receta.setMargenGanancia(receta.getPrecioDeVenta() / receta.getCostoPorcion());
  }
}
