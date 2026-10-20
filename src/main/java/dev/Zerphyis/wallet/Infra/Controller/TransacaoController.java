package dev.Zerphyis.wallet.Infra.Controller;

import dev.Zerphyis.wallet.Application.Dto.Transacao.ExtratoResponse;
import dev.Zerphyis.wallet.Application.Dto.Transacao.ExtratoResponseDTO;
import dev.Zerphyis.wallet.Application.Dto.Transacao.TransacaoRequestDto;
import dev.Zerphyis.wallet.Application.Dto.Transacao.TransacaoResponseDto;
import dev.Zerphyis.wallet.Application.UseCases.Transacao.ServiceTransacao;
import dev.Zerphyis.wallet.Domain.Entitys.Transacao;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
public class TransacaoController {
    private final ServiceTransacao serviceTransacao;

    public TransacaoController(ServiceTransacao serviceTransacao) {
        this.serviceTransacao = serviceTransacao;
    }

    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoResponseDto> cadastrarTransacao(@Valid @RequestBody TransacaoRequestDto request) {
        Transacao transacaoSalva = serviceTransacao.realizarTransacao(request.paraEntidade());
        TransacaoResponseDto response = TransacaoResponseDto.daEntidade(transacaoSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/clientes/{idCliente}/saldo")
    public ResponseEntity<Map<String, Object>> consultarSaldo(@PathVariable UUID idCliente) {
        BigDecimal saldo = serviceTransacao.consultarSaldo(idCliente);
        return ResponseEntity.ok(Map.of(
                "idCliente", idCliente,
                "saldo", saldo
        ));
    }

    @GetMapping("/clientes/{idCliente}/extrato")
    public ResponseEntity<ExtratoResponseDTO> consultarExtrato(@PathVariable UUID idCliente) {
        ExtratoResponse response = serviceTransacao.consultarExtrato(idCliente);
        ExtratoResponseDTO dto = ExtratoResponseDTO.doResponse(response);
        return ResponseEntity.ok(dto);
    }
}
