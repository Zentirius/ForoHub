package com.example.ForoHub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLSyntaxErrorException;

@Configuration
public class InitialDataLoader {

    @Value("classpath:sql/V1__CreateTables.sql")
    private org.springframework.core.io.Resource createScript;

    @Bean
    CommandLineRunner loadData(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(createScript.getInputStream()));
                 Statement statement = connection.createStatement()) {

                StringBuilder sql = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("--")) {
                        continue; // Saltar líneas vacías o comentarios
                    }
                    sql.append(line).append(" ");
                    if (line.endsWith(";")) {
                        try {
                            System.out.println("Ejecutando sentencia SQL: " + sql.toString());
                            statement.execute(sql.toString());
                        } catch (SQLSyntaxErrorException e) {
                            if (e.getMessage().contains("already exists")) {
                                System.out.println("Advertencia: La tabla ya existe, saltando la creación...");
                            } else {
                                System.out.println("Error en la sentencia SQL: " + sql.toString());
                                e.printStackTrace();
                            }
                        }
                        sql.setLength(0); // Limpiar para la siguiente sentencia
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
