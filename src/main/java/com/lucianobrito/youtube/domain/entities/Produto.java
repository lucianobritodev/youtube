package com.lucianobrito.youtube.domain.entities;

import com.lucianobrito.youtube.domain.dtos.ProdutoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private Double preco;
    private String descricao;
    private String unidadeMedida;
    private Double peso;
    private Boolean ativo;

    public Produto(@NonNull ProdutoDto produtoDto) {
        this.id = produtoDto.getId();
        this.nome = produtoDto.getNome();
        this.preco = produtoDto.getPreco();
        this.descricao = produtoDto.getDescricao();
        this.unidadeMedida = produtoDto.getUnidadeMedida();
        this.peso = produtoDto.getPeso();
        this.ativo = produtoDto.getAtivo();
    }
}
