package br.com.jhonneao.erp.produto_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto detalhesProduto) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produtoExistente = produtoOptional.get();
            produtoExistente.setNome(detalhesProduto.getNome());
            produtoExistente.setDescricao(detalhesProduto.getDescricao());
            produtoExistente.setPreco(detalhesProduto.getPreco());
            produtoExistente.setQuantidadeEmEstoque(detalhesProduto.getQuantidadeEmEstoque());

            final Produto produtoAtualizado = produtoRepository.save(produtoExistente);
            return ResponseEntity.ok(produtoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
