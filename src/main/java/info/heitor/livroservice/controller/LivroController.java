package info.heitor.livroservice.controller;

import info.heitor.livroservice.model.Livro;
import info.heitor.livroservice.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> findAll() {
        return livroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id) {
        Optional<Livro> livro = livroService.findById(id);
        return livro.isPresent() ? ResponseEntity.ok(livro.get()) : ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Livro save(@RequestBody Livro livro) {
        return livroService.save(livro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro) {
        Optional<Livro> livroOptional = livroService.findById(id);
        if (livroOptional.isPresent()) {
            Livro livroToUpdate = livroOptional.get();
            livroToUpdate.setTitulo(livro.getTitulo());
            livroToUpdate.setAutor(livro.getAutor());
            livroToUpdate.setIsbn(livro.getIsbn());
            Livro livroUpdated = livroService.save(livroToUpdate);
            return ResponseEntity.ok(livroUpdated);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Livro> delete(@PathVariable Long id) {
        if(livroService.findById(id).isPresent()) {
            livroService.delete(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
