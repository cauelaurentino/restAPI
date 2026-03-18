package caue.restAPI.service;

import caue.restAPI.model.Produto;
import caue.restAPI.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;
    
    public List<Produto> listarTodos(){
        return repository.findAll();
    }
    
    public Optional<Produto> buscarPorId(Long id){ // Parametros: variavel id do tipo Long
        return repository.findById(id);
    }
    
    public Produto salvar(Produto p){ // Parametros: variavel p do tipo Produto
        if (p.getPreco() < 0) {
            throw new IllegalArgumentException("Preco nao pode ser negativo");
        }
        return repository.save(p);
    }
    
    public Optional<Produto> atualizar(Long id, Produto dados){
        return repository.findById(id).map(p -> {
            p.setNome(dados.getNome());
            p.setPreco(dados.getPreco());
            p.setDescricao(dados.getDescricao());
            p.setQuantidade(dados.getQuantidade());
            return repository.save(p);           
        });
    }
    
    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    };
}
