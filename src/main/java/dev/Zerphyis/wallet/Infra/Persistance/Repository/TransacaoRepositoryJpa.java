package dev.Zerphyis.wallet.Infra.Persistance.Repository;

import dev.Zerphyis.wallet.Infra.Persistance.EntityJpa.TransacaoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepositoryJpa extends JpaRepository<TransacaoJpa, Long> {
    List<TransacaoJpa> findByClienteId(UUID clienteId);
}