package com.lucianobrito.testesunitarios.domain.services.impl;

import com.lucianobrito.testesunitarios.commons.exceptions.ResourceNotFoundException;
import com.lucianobrito.testesunitarios.domain.dtos.ProdutoDto;
import com.lucianobrito.testesunitarios.domain.entities.Produto;
import com.lucianobrito.testesunitarios.domain.services.BaseService;
import com.lucianobrito.testesunitarios.domain.services.ProdutoService;
import com.lucianobrito.testesunitarios.infra.repositories.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl extends BaseService implements ProdutoService {

    private ProdutoRepository produtoRepository;

    @Override
    public List<ProdutoDto> listar() {
        try {
            return produtoRepository.findAll().stream().map(ProdutoDto::new).toList();
        } catch (Exception e) {
            throw handlerLogBusinessError(e);
        }
    }

    @Override
    public ProdutoDto obter(UUID id) {
        try {
            return produtoRepository.findById(id).stream()
                    .map(ProdutoDto::new)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado! Confira os dados e tente novamente."));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handlerLogBusinessError(e);
        }
    }

    @Override
    @Transactional
    public ProdutoDto criar(ProdutoDto produtoDto) {
        try {
            Produto newProduto = new Produto(produtoDto);
            newProduto = produtoRepository.save(newProduto);
            return new ProdutoDto(newProduto);
        } catch (Exception e) {
            throw handlerLogBusinessError(e);
        }
    }

    @Override
    @Transactional
    public ProdutoDto atualizar(UUID id, ProdutoDto produtoDto) {
        try {
            obter(id);
            produtoDto.setId(id);
            Produto updateProduto = new Produto(produtoDto);
            updateProduto = produtoRepository.save(updateProduto);
            return new ProdutoDto(updateProduto);
        } catch (ResourceNotFoundException e) {
            throw handlerLogError(e);
        } catch (Exception e) {
            throw handlerLogBusinessError(e);
        }
    }

    @Override
    @Transactional
    public void ativar(UUID id, boolean status) {
        try {
            obter(id);
            produtoRepository.ativar(id, status);
        } catch (ResourceNotFoundException e) {
            throw handlerLogError(e);
        } catch (Exception e) {
            throw handlerLogBusinessError(e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        try {
            obter(id);
            produtoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw handlerLogError(e);
        } catch (Exception e) {
            throw handlerLogBusinessError(e);
        }
    }
}
