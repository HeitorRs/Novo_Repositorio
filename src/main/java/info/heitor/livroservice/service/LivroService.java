package info.heitor.livroservice.service;

import info.heitor.livroservice.model.Livro;
import info.heitor.livroservice.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> findAll() {
        return livroRepository.findAll();
    }
    public Optional<Livro> findById(Long id) {
        return livroRepository.findById(id);
    }
    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }
    public Livro update(Livro livro) {
        return livroRepository.save(livro);
    }
    public void delete(Long id) {
        livroRepository.deleteById(id);
    }
}
