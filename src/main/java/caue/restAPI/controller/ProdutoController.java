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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @GetMapping("/{id}") // Identifica como uma requisição GET que, em seguida, recebera um valor determinado como "id"
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) { // Um objeto Produto que virá com algum codigo de status (ResponseEntity) e será encontrado pelo id que esta sendo puxado do valor digitado na requisição GET pelo @PathVariable
        return service.buscarPorId(id) // Enviando esse id para o metodo que criamos no ProdutoService, e lá ele irá realizar a busca pelo id
                .map(ResponseEntity::ok) // A partir do resultado da busca ele envia um código, assim como esperado pelo ResponseEntity: se der certo será 200 OK
                .orElse(ResponseEntity.notFound().build()); // Se a busca der errado o código será 404 Not Found
    }
    
    //Endpoint 3: Criar novo produto
    @PostMapping // Identifica como post (criar / envio de dados)
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) { // Um objeto Produto que virá com algum codigo de status (ResponseEntity) e será criado a partir do recebimento de um JSON que será puxado pelo RequestBody e transformado em um objeto de nome produto e de tipo Produto        
        Produto salvo = service.salvar(produto); // Cria uma variavcel salvo do tipo Produto que tem como valor o produto sendo salvo pelo ProdutoService (Para que o novo produto receba o id correto pelo banco)
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo); // Retorna o objeto salvo com o status 201 Created
    }
    
    //Endpoint 4: Atualizar um produto
    @PutMapping("/{id}") // Identifica requisicoes do tipo PUT, ideais para atualizações
    public ResponseEntity<Produto> atualizar( // Indica que irá devolver um objeto produto junto com um codigo de status
        @PathVariable Long id, // Como primeiro parâmetro temos o responsável por pegar o id digitado no endereço
        @RequestBody Produto produto){ // Como segundo parâmetro temos o responsável por pegar o JSON que recebemos pelo endereço e encaixa-lo no objeto Produto
            return service.atualizar(id, produto) // retornamos a função atualizar lá do service, enviando o id que queremos mudar e o novo produto
                    .map(ResponseEntity::ok) // Se der certo retorna 200OK
                    .orElse(ResponseEntity.notFound().build()); // Caso de errado retorna 404 Not Found
    }
    
    @DeleteMapping("/{id}") // Identifica uma requisição de deletar
    public ResponseEntity<Void> deletar(@PathVariable Long id) { // Retorna somente o código de status, sem produto porque esse metodo é de deletar então não tem o que retornar, e pega o id digitado no endereço como parâmetro
        if (service.deletar(id)) { // No service temos o metodo deletar que confere se o id existe, se sim o deleta e retorna true, nesse if, o código de dentro só é executado se o resultado do metodo deletar do service for true
            return ResponseEntity.noContent().build(); // Como o produto ja foi deletado no service, ele retorna somente o codigo de status 204 No Content
        }
        return ResponseEntity.notFound().build(); // Caso contrário, se a resposta do if do service for false e o codigo nem passar pelo if aimca, é sinal que o id nao foi encontrado, então ele retorna 404 Not Found
    }
    
}
    

    