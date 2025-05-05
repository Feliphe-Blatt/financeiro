package com.controlefinanceiro.api.config;

import com.controlefinanceiro.api.enums.NomeCategoriaEnum;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.controlefinanceiro.api.model.Categoria;
import com.controlefinanceiro.api.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(CategoriaRepository categoriaRepository) {
        return args -> {
            // Verifica se já existem categorias no banco
            if (categoriaRepository.count() == 0) {
                for (NomeCategoriaEnum nomeCategoria : NomeCategoriaEnum.values()) {
                    Categoria categoria = new Categoria();
                    categoria.setNomeCategoria(nomeCategoria);
                    categoriaRepository.save(categoria);
                }
            }
        };
    }
}
