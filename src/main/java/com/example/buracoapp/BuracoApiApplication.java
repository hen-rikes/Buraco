package com.example.buracoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Esta é a classe principal que inicia toda a aplicação Spring Boot.
 * A anotação @SpringBootApplication combina várias anotações essenciais
 * que habilitam a autoconfiguração, a varredura de componentes e a configuração do Spring.
 */
@SpringBootApplication
public class BuracoApiApplication {

    /**
     * O método main é o ponto de entrada padrão para uma aplicação Java.
     * A linha SpringApplication.run() inicializa o Spring, cria o contexto da
     * aplicação e inicia o servidor web embutido (Tomcat).
     * @param args Argumentos de linha de comando (não usados neste caso).
     */
    public static void main(String[] args) {
        SpringApplication.run(BuracoApiApplication.class, args);
    }

}
