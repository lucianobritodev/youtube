package com.lucianobrito.youtube.domain.dtos;

import com.lucianobrito.youtube.domain.entities.Produto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true)
public class ProdutoDto {

    private UUID id;
    private String nome;
    private Double preco;
    private String descricao;
    private String unidadeMedida;
    private Double peso;
    private Boolean ativo;

    public ProdutoDto(@NonNull Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.descricao = produto.getDescricao();
        this.unidadeMedida = produto.getUnidadeMedida();
        this.peso = produto.getPeso();
        this.ativo = produto.getAtivo();
    }
}
