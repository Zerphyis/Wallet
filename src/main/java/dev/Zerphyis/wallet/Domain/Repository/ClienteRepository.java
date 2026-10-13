package dev.Zerphyis.wallet.Domain.Repository;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    boolean existsByCpf(String cpf);
    Optional<Cliente> findById(UUID id);

}
