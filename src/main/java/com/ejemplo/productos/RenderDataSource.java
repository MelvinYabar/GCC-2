package com.ejemplo.productos;

import com.zaxxer.hikari.HikariDataSource;
import java.net.URI;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("render")
public class RenderDataSource {
    @Bean
    DataSource postgres(@Value("${DATABASE_URL}") String databaseUrl) {
        URI direccion;
        try {
            direccion = URI.create(databaseUrl);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("DATABASE_URL no tiene formato PostgreSQL");
        }

        String usuarioClave = direccion.getUserInfo();
        if (!"postgresql".equals(direccion.getScheme()) || direccion.getHost() == null
                || direccion.getPath() == null || direccion.getPath().length() <= 1
                || usuarioClave == null || !usuarioClave.contains(":")) {
            throw new IllegalArgumentException("DATABASE_URL no tiene formato PostgreSQL");
        }

        String[] credenciales = usuarioClave.split(":", 2);
        int puerto = direccion.getPort() == -1 ? 5432 : direccion.getPort();
        HikariDataSource conexion = new HikariDataSource();
        conexion.setJdbcUrl("jdbc:postgresql://" + direccion.getHost() + ":" + puerto + direccion.getPath());
        conexion.setUsername(credenciales[0]);
        conexion.setPassword(credenciales[1]);
        conexion.setMaximumPoolSize(3);
        return conexion;
    }
}
