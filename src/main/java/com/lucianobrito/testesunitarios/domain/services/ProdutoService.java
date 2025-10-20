package com.lucianobrito.testesunitarios.domain.services;

import com.lucianobrito.testesunitarios.domain.dtos.ProdutoDto;

import java.util.List;
import java.util.UUID;

public interface ProdutoService {

    List<ProdutoDto> listar();
    ProdutoDto obter(UUID id);
    ProdutoDto criar(ProdutoDto produtoDto);
    ProdutoDto atualizar(UUID id, ProdutoDto produtoDto);
    void ativar(UUID id, boolean status);
    void excluir(UUID id);

}
