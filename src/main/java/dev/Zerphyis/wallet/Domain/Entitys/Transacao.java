package dev.Zerphyis.wallet.Domain.Entitys;

import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import dev.Zerphyis.wallet.Domain.Entitys.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private Long id;
    private Cliente cliente;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private LocalDateTime data;

    public Transacao(Long id, Cliente cliente, BigDecimal valor, TipoTransacao tipo, LocalDateTime data) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}