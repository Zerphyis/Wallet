package dev.Zerphyis.wallet.Infra.Persistance.Mapper;


import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Infra.Persistance.EntityJpa.ClienteJpa;

public class ClienteMapper {

    public static ClienteJpa toJpa(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteJpa(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf()
        );
    }

    public static Cliente toDomain(ClienteJpa clienteJpa) {
        if (clienteJpa == null) {
            return null;
        }
        return new Cliente(
                clienteJpa.getId(),
                clienteJpa.getNome(),
                clienteJpa.getCpf()
        );
    }
}