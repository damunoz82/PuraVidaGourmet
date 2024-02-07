// package org.puravidatgourmet.security.db.repository;
//
// import java.util.List;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.puravidatgourmet.security.domain.entity.MateriaPrima;
// import org.puravidatgourmet.security.domain.enums.UnidadMedidas;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringRunner;
//
// @ActiveProfiles("test")
// @SpringBootTest
// @RunWith(SpringRunner.class)
// public class MateriaPrimaRepositoryTest {
//
//  @Autowired private MateriaPrimaRepository materiaPrimaRepository;
//
//  @Test
//  public void probarCrud() {
//    salvarMateriaPrima();
//    leerMateriaPrima();
//    MateriaPrima harina = leerHarina();
//    actualizarHarina(harina);
//    eliminarHarina();
//  }
//
//  private void eliminarHarina() {
//    materiaPrimaRepository.delete(materiaPrimaRepository.findByNombre("xxxxx"));
//    materiaPrimaRepository.delete(materiaPrimaRepository.findByNombre("yyyyy"));
//    materiaPrimaRepository.delete(materiaPrimaRepository.findByNombre("uuuuu"));
//  }
//
//  private void actualizarHarina(MateriaPrima materiaPrima) {
//    materiaPrima.setNombre("Harina Fortificada");
//    materiaPrimaRepository.save(materiaPrima);
//  }
//
//  private MateriaPrima leerHarina() {
//    return materiaPrimaRepository.findByNombre("xxxxx");
//  }
//
//  private void salvarMateriaPrima() {
//    MateriaPrima leche =
//        MateriaPrima.builder()
//            .nombre("xxxxx")
//            .cantidadEnBodega(10)
//            .cantidadPorUnidad(1000)
//            .unidadMedida(UnidadMedidas.MILI_GRAMOS)
//            .build();
//
//    MateriaPrima harina =
//        MateriaPrima.builder()
//            .nombre("yyyyy")
//            .cantidadEnBodega(10)
//            .cantidadPorUnidad(1000)
//            .unidadMedida(UnidadMedidas.GRAMOS)
//            .build();
//
//    MateriaPrima sal =
//        MateriaPrima.builder()
//            .nombre("uuuuu")
//            .cantidadEnBodega(10)
//            .cantidadPorUnidad(1000)
//            .unidadMedida(UnidadMedidas.GRAMOS)
//            .build();
//
//    materiaPrimaRepository.save(leche);
//    materiaPrimaRepository.save(sal);
//    materiaPrimaRepository.save(harina);
//  }
//
//  private void leerMateriaPrima() {
//    List<MateriaPrima> materiaPrimas = materiaPrimaRepository.findAll();
//  }
// }
