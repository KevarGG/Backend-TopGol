package com.topgol.store.config;

import com.topgol.store.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Carga el catalogo inicial (seed/catalogo.sql) UNA sola vez.
 * Si la tabla de productos ya tiene datos, no hace nada, asi no se duplican
 * los productos cada vez que reinicias el backend.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final ProductRepository productRepository;
    private final DataSource dataSource;

    public DataSeeder(ProductRepository productRepository, DataSource dataSource) {
        this.productRepository = productRepository;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        long existentes = productRepository.count();
        if (existentes > 0) {
            log.info("Catalogo ya cargado ({} productos). No se vuelve a cargar.", existentes);
            return;
        }

        log.info("Base de datos vacia: cargando catalogo inicial...");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("seed/catalogo.sql"));
        populator.setSqlScriptEncoding("UTF-8");

        // Todo en una sola transaccion: o se carga el catalogo completo o no se carga nada.
        try (Connection conn = dataSource.getConnection()) {
            boolean autoCommitOriginal = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                populator.populate(conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(autoCommitOriginal);
            }
        }
        log.info("Catalogo cargado: {} productos.", productRepository.count());
    }
}
