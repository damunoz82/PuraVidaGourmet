package org.puravidagourmet.admin.services;

import org.puravidagourmet.admin.db.repository.MesaRepository;
import org.puravidagourmet.admin.domain.entity.Mesa;
import org.puravidagourmet.admin.exceptions.PuraVidaExceptionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.puravidagourmet.admin.exceptions.codes.PuraVidaErrorCodes.MESA_REC001;

@Service
public class MesaService {

  private final MesaRepository mesaRepository;

  public MesaService(MesaRepository mesaRepository) {
    this.mesaRepository = mesaRepository;
  }

  public Mesa saveMesa(Mesa mesa) {
    if (mesa.getId() <= 0) {
      // validate
      mesaRepository
          .findByName(mesa.getNombre())
          .ifPresent(
              (e) -> {
                throw new PuraVidaExceptionHandler(MESA_REC001);
              });

    } else {
      // validate
      Optional<Mesa> check = mesaRepository.findByName(mesa.getNombre());
      if (check.isPresent() && check.get().getId() != mesa.getId()) {
        throw new PuraVidaExceptionHandler(MESA_REC001);
      }
    }
    return mesaRepository.save(mesa);
  }

  public Optional<Mesa> getByid(long id) {
    return mesaRepository.findById(id);
  }

  public List<Mesa> findAll() {
    return mesaRepository.findAll();
  }

  public void delete(long id) {
    mesaRepository.delete(id);
  }
}
