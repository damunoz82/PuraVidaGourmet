//package org.puravidatgourmet.security.db.repository;
//
//import java.util.List;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.puravidatgourmet.security.domain.entity.MateriaPrima;
//import org.puravidatgourmet.security.domain.entity.Receta;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@ActiveProfiles("test")
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class RecetaRepositoryTest {
//
//  @Autowired private MateriaPrimaRepository materiaPrimaRepository;
//  @Autowired private RecetaRepository recetaRepository;
//
//  @Test
//  public void runTestingScenarios() {
////    createReceta();
//    leerRecetas();
//    Receta brownies = recetaRepository.findByNombre("Brownies");
//    actualizarReceta(brownies);
//    eliminarReceta(brownies);
//  }
//
//  private void eliminarReceta(Receta receta) {
//    recetaRepository.delete(receta);
//  }
//
//  private void actualizarReceta(Receta receta) {
//    receta.getIngredientes().add(materiaPrimaRepository.findByNombre("Sal"));
//    receta.getIngredientes().add(materiaPrimaRepository.findByNombre("Leche"));
//    recetaRepository.save(receta);
//  }
//
//  private void leerRecetas() {
//    List<Receta> recetas = recetaRepository.findAll();
//    recetas.forEach(System.out::println);
//  }
//
//  private void createReceta() {
//    List<MateriaPrima> ingredientes = List.of(materiaPrimaRepository.findByNombre("Harina Fortificada"));
//    Receta receta =
//        Receta.builder()
//            .nombre("Brownies")
//            .rendimiento(24)
//            .costosFijos(1000)
//            .descuentos(0)
//            .ingredientes(ingredientes)
//            .build();
//
//    recetaRepository.save(receta);
//  }
//}
