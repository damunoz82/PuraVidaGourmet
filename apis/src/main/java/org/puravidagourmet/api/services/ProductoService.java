package org.puravidagourmet.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.ProductoRepository;
import org.puravidagourmet.api.domain.entity.Producto;
import org.puravidagourmet.api.domain.pojo.ProductoPojo;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

  @Autowired private ProductoRepository productoRepository;

  public void deleteById(long id) {
    productoRepository.delete(id);
  }

  @Transactional
  public Producto saveProducto(Producto producto) {
    try {
      LOGGER.info("Start: saveProducto");

      // calcular coste final de producto
      calcularCosteFinalDeProducto(producto);

      // guardar.
      return productoRepository.save(producto);
    } catch(Exception e) {
      LOGGER.error("Error al salvar el producto", e);
      throw new BadRequestException("Error al salvar el producto. Verifica que los datos esten correctos." +
              "");
    }finally {
      LOGGER.info("End: saveProducto");
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
          costeUnitario = (float) producto.getPrecioDeCompra() / producto.getCantidadPorUnidad() * (1 - producto.getPorcentajeMerma());
    }

    producto.setCosteUnitario(round(costeUnitario, 2));
  }

  // fixme - move to more suitable place
  private float round(float d, int decimalPlace) {
    BigDecimal bd = new BigDecimal(Float.toString(d));
    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
    return bd.floatValue();
  }
}
