/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package caue.restAPI.controller;

import caue.restAPI.model.Produto;
import caue.restAPI.service.ProdutoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digma
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;
    
    // Endpoint 1: Listar Todos
    
    @GetMapping //Identifica como uma requisição GET sem nome proprio (/produtos)
    public ResponseEntity<List<Produto>> listarTodos() { // identifica a devolução de um protocolo http com uma lista de produtos dentro, tudo isso com o nome listarTodos()
        List<Produto> produtos = service.listarTodos(); // chama o metodo listar todos que fizemos no ProdutoService.java e atribui em uma variavel do tipo List<Produtos>
        return ResponseEntity.ok(produtos); // retorna o resultado do metodo com o status 200 OK
    }
    
    // Endpoint 2: Buscar por id
    
    @GetMapping("/{id}") // Identifica como GET com o endereço produto/{id} 
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) { // identifica a devolução de um protocolo http com um Produto dentro, tudo isso com o nome buscarPorId e recebendo o Long id como parametro associado com o PathVariable que torna ele uma variavel, possibilitando a busca por esse numero
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
