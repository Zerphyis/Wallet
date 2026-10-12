package dev.Zerphyis.wallet.Domain.Repository;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {
    Optional<Cliente> buscarPorid(UUID uuid);

    Cliente salvar(Cliente cliente);

}
