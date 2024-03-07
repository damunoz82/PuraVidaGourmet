package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.ProductoRepository;
import org.puravidagourmet.api.domain.entity.Producto;
import org.puravidagourmet.api.domain.pojo.ProductoPojo;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.puravidagourmet.api.utils.MathUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

  private final ProductoRepository productoRepository;

  public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
  }

  public void deleteById(long id) {
    productoRepository.delete(id);
  }

  @Transactional
  public Producto saveProducto(Producto producto) {
    try {

      // calcular coste final de producto
      calcularCosteFinalDeProducto(producto);

      // guardar.
      return productoRepository.save(producto);
    } catch (Exception e) {
      throw new BadRequestException(
          "Error al salvar el producto. Verifica que los datos esten correctos.");
    }
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
          costeUnitario =
              (float) producto.getPrecioDeCompra()
                  / producto.getCantidadPorUnidad()
                  * (1 - producto.getPorcentajeMerma());
    }

    producto.setCosteUnitario(MathUtils.round(costeUnitario, 2));
  }
}
