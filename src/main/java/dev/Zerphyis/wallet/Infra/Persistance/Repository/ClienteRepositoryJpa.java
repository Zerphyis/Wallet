package dev.Zerphyis.wallet.Infra.Persistance.Repository;

import dev.Zerphyis.wallet.Infra.Persistance.ClienteJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepositoryJpa extends JpaRepository<ClienteJpa, UUID> {
}