package com.lucianobrito.youtube.domain.services.impl;

import com.lucianobrito.youtube.commons.exceptions.BusinessException;
import com.lucianobrito.youtube.commons.exceptions.ResourceNotFoundException;
import com.lucianobrito.youtube.domain.dtos.ProdutoDto;
import com.lucianobrito.youtube.domain.entities.Produto;
import com.lucianobrito.youtube.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    private static final UUID ID = UUID.randomUUID();

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Produto produto;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        createMocks();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
        produto = null;
    }

    @Test
    void listar() {
        //Criar os stubbs
        when(produtoRepository.findAll()).thenReturn(List.of(produto));

        //execução
        List<ProdutoDto> response = produtoService.listar();

        //Assertivas
        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(produtoRepository).findAll();
    }

    @Test
    void listarBusinessException() {
        //Criar os stubbs
        when(produtoRepository.findAll()).thenThrow(new RuntimeException("Erro ao tentar recuperar os produtos!"));

        //execução
        BusinessException e = assertThrows(BusinessException.class, () -> produtoService.listar());

        //Assertivas
        assertNotNull(e);
        verify(produtoRepository).findAll();
    }

    @Test
    void obter() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));

        ProdutoDto response = produtoService.obter(ID);

        assertNotNull(response);
        verify(produtoRepository).findById(any(UUID.class));
    }

    @Test
    void obterBusinessException() {
        when(produtoRepository.findById(any(UUID.class))).thenThrow(new RuntimeException("Erro ao tentar recuperar o produto!"));

        BusinessException ex = assertThrows(BusinessException.class, () -> produtoService.obter(ID));

        assertNotNull(ex);
        verify(produtoRepository).findById(any(UUID.class));
    }

    @Test
    void criar() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDto response = produtoService.criar(new ProdutoDto(produto));

        assertNotNull(response);
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void criarBusinessException() {
        when(produtoRepository.save(any(Produto.class))).thenThrow(new RuntimeException("Erro ao tentar salvar o produto!"));

        BusinessException ex = assertThrows(BusinessException.class, () -> produtoService.criar(new ProdutoDto(produto)));

        assertNotNull(ex);
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void atualizar() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDto response = produtoService.atualizar(ID, new ProdutoDto(produto));

        assertNotNull(response);
        verify(produtoRepository).findById(any(UUID.class));
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void atualizarResourceNotFoundException() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                produtoService.atualizar(ID, new ProdutoDto(produto)));

        assertNotNull(ex);
        verify(produtoRepository).findById(any(UUID.class));
    }

    @Test
    void atualizarBusinessException() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenThrow(new RuntimeException("Erro ao tentar atualizar o produto!"));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                produtoService.atualizar(ID, new ProdutoDto(produto)));

        assertNotNull(ex);
        verify(produtoRepository).findById(any(UUID.class));
    }

    @Test
    void ativar() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).ativar(any(UUID.class), any(Boolean.class));

        produtoService.ativar(ID, true);

        verify(produtoRepository).ativar(any(UUID.class), any(Boolean.class));
    }

    @Test
    void ativarResourceNotFoundException() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> produtoService.ativar(ID,true));

        assertNotNull(ex);
        verify(produtoRepository, never()).ativar(any(UUID.class), anyBoolean());
    }

    @Test
    void ativarBusinessException() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));
        doThrow(new RuntimeException("Erro ao excluir produto!")).when(produtoRepository).ativar(any(UUID.class), anyBoolean());

        BusinessException ex = assertThrows(BusinessException.class, () -> produtoService.ativar(ID, true));

        assertNotNull(ex);
        verify(produtoRepository).ativar(any(UUID.class), anyBoolean());
    }

    @Test
    void excluir() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).deleteById(any(UUID.class));

        produtoService.excluir(ID);

        verify(produtoRepository).deleteById(any(UUID.class));
    }

    @Test
    void excluirResourceNotFoundException() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> produtoService.excluir(ID));

        assertNotNull(ex);
        verify(produtoRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void excluirBusinessException() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.of(produto));
        doThrow(new RuntimeException("Erro ao excluir produto!")).when(produtoRepository).deleteById(any(UUID.class));

        BusinessException ex = assertThrows(BusinessException.class, () -> produtoService.excluir(ID));

        assertNotNull(ex);
        verify(produtoRepository).deleteById(any(UUID.class));
    }

    private void createMocks() {
        produto = new Produto();
        produto.setId(ID);
        produto.setNome("Produto 1");
        produto.setAtivo(true);
    }
}