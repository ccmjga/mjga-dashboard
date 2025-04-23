package com.zl.mjga.integration.quartz;

import com.zl.mjga.config.QuartzConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringJUnitConfig(classes = QuartzConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@JooqTest
public class AbstractQuartzTest {
  public static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:17.2-alpine").withDatabaseName("mjga");

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.flyway.locations", () -> "classpath:db/migration/test");
    registry.add("spring.flyway.default-schema", () -> "public");
  }

  static {
    postgres.start();
  }
}
