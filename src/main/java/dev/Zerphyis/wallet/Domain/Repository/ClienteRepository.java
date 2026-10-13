package dev.Zerphyis.wallet.Domain.Repository;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;


public interface ClienteRepository {
    Cliente save(Cliente cliente);
    boolean existsByCpf(String cpf);
}
