package dev.Zerphyis.wallet.Infra.Persistance.Repository.Adapter;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Repository.ClienteRepository;
import dev.Zerphyis.wallet.Infra.Persistance.EntityJpa.ClienteJpa;
import dev.Zerphyis.wallet.Infra.Persistance.Mapper.ClienteMapper;
import dev.Zerphyis.wallet.Infra.Persistance.Repository.ClienteRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AdapterRepositoryCliente implements ClienteRepository {
    private final ClienteRepositoryJpa springRepository;

    public AdapterRepositoryCliente(ClienteRepositoryJpa springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteJpa clienteJpa = ClienteMapper.toJpa(cliente);
        ClienteJpa savedJpa = springRepository.save(clienteJpa);
        return ClienteMapper.toDomain(savedJpa);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return springRepository.existsByCpf(cpf);
    }

    @Override
    public Optional<Cliente> findById(UUID id) {
        return springRepository.findById(id)
                .map(ClienteMapper::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return springRepository.findAll().stream()
                .map(ClienteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }
}

