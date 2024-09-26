package info.heitor.livroservice;

import info.heitor.livroservice.model.Livro;
import info.heitor.livroservice.repository.LivroRepository;
import info.heitor.livroservice.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc // Anotação para configuração automática do MockMVC
class LivroServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc; // Objeto para simular as requisições HTTP aos endpoints da API
    @MockBean
    private LivroService livroService;

    @Test
    void testeCreateLivro() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Spring Microservices");
        livro.setAutor("John Doe");
        livro.setIsbn("1234567890");
        when(livroService.save(any(Livro.class))).thenReturn(livro);
        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Spring Microservices\",\"autor\":\"John Doe\",\"isbn\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Spring Microservices"))
                .andExpect(jsonPath("$.autor").value("John Doe"))
                .andExpect(jsonPath("$.isbn").value("1234567890"));
    }

    @Test
    public void testGetAllLivros() throws Exception {
        mockMvc.perform(get("/api/livros")).andExpect(status().isOk());
    }

    @Test
    public void testGetLivroById() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro 1");
        livro.setAutor("Autor 1");
        livro.setIsbn("1231231231");
        when(livroService.findById(1L)).thenReturn(Optional.of(livro));
        mockMvc.perform(get("/api/livros/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Livro 1"))
                .andExpect(jsonPath("$.autor").value("Autor 1"))
                .andExpect(jsonPath("$.isbn").value("1231231231"));
    }

    @Test
    public void testDeleteLivroById() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro 1");
        livro.setAutor("Autor 1");
        livro.setIsbn("1231231231");
        when(livroService.findById(1L)).thenReturn(Optional.of(livro));
        mockMvc.perform(delete("/api/livros/1")).andExpect(status().isOk());
    }

    @Test
    public void testUpdateLivroById() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro 1");
        livro.setAutor("Autor 1");
        livro.setIsbn("1231231231");

        when(livroService.findById(1L)).thenReturn(Optional.of(livro));
        when(livroService.save(any(Livro.class))).thenReturn(livro);

        mockMvc.perform(put("/api/livros/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Spring Microservices\",\"autor\":\"John Doe\",\"isbn\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Spring Microservices"))
                .andExpect(jsonPath("$.autor").value("John Doe"))
                .andExpect(jsonPath("$.isbn").value("1234567890"));
    }
}
