package org.puravidatgourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.api.db.repository.ProductoRepository;
import org.puravidatgourmet.api.domain.entity.Producto;
import org.puravidatgourmet.api.domain.pojo.ProductoPojo;
import org.puravidatgourmet.api.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

  @Autowired private ProductoRepository productoRepository;

  public void deleteById(long id) {
    productoRepository.deleteById(id);
  }

  @Transactional
  public Producto saveProducto(Producto producto) {
    // calcular coste final de producto
    calcularCosteFinalDeProducto(producto);
    // guardar.
    return productoRepository.save(producto);
  }

  public Optional<Producto> getProductoById(long id) {
    return productoRepository.findById(id);
  }

  public List<Producto> getAllProducto() {
    return productoRepository.findAll();
  }

  public void validateSave(ProductoPojo producto) {
    Optional<Producto> dbProducto = productoRepository.findByNombre(producto.getNombre());

    if (dbProducto.isPresent()) {
      throw new BadRequestException("Ya existe un producto con ese nombre");
    }
  }

  public void validateUpdate(ProductoPojo producto) {
    Optional<Producto> dbProducto = productoRepository.findByNombre(producto.getNombre());

    if (dbProducto.isPresent() && dbProducto.get().getId() != producto.getId()) {
      throw new BadRequestException(
          "Ya existe un producto con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long producto) {
    // fixme finish
    // check if ingrediente is been used in any recipe.
  }

  private void calcularCosteFinalDeProducto(Producto producto) {
    float costeUnitario = 0;
    switch (producto.getUnidadMedida()) {
      case GRAMOS, MILI_LITROS ->
          costeUnitario =
              (float) 1 * producto.getPrecioDeCompra() / producto.getCantidadPorUnidad();
      case UNIDAD ->
          costeUnitario = (float) producto.getPrecioDeCompra() / producto.getCantidadPorUnidad();
    }

    producto.setCosteUnitario(costeUnitario);
  }
}
