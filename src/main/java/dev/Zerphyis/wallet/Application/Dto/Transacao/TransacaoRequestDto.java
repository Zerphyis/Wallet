package dev.Zerphyis.wallet.Application.Dto.Transacao;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Entitys.TipoTransacao;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;

import java.math.BigDecimal;
import java.util.UUID;

public record TransacaoRequestDto(
        UUID idCliente,
        BigDecimal valor,
        TipoTransacao tipo
) {
    public Transacao paraEntidade() {
        Cliente cliente = new Cliente();
        cliente.setId(this.idCliente);

        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacao.setValor(this.valor);
        transacao.setTipo(this.tipo);

        return transacao;
    }
}