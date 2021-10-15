package org.dita.klinik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(PatientRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Patient("Juned", "10-12-1942", "123")));
      log.info("Preloading " + repository.save(new Patient("Brian", "20-10-1940", "456")));
    };
  }
}