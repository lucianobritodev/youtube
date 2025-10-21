package com.lucianobrito.youtube.infra.controllers;

import com.lucianobrito.youtube.domain.dtos.ProdutoDto;
import com.lucianobrito.youtube.domain.services.ProdutoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoService produtoService;


    @GetMapping("/v1")
    public ResponseEntity<List<ProdutoDto>> listar() {
        return ResponseEntity.ok(produtoService.listar());
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<ProdutoDto> obter(@PathVariable("id") @NotNull UUID id) {
        return ResponseEntity.ok(produtoService.obter(id));
    }

    @PostMapping("/v1")
    public ResponseEntity<ProdutoDto> criar(@RequestBody @NotBlank ProdutoDto produtoDto) {
        produtoDto = produtoService.criar(produtoDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(produtoDto);
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<ProdutoDto> atualizar(@PathVariable("id") @NotNull UUID id,
                                                @RequestBody @NotBlank ProdutoDto produtoDto) {

        return ResponseEntity.ok(produtoService.atualizar(id, produtoDto));
    }

    @PatchMapping("/v1/{id}")
    public ResponseEntity<Void> ativar(@PathVariable("id") @NotNull UUID id,
                                         @RequestBody @NotNull Boolean status) {

        produtoService.ativar(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") @NotNull UUID id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
