package com.murilonerdx.uolhost.config;

import com.murilonerdx.uolhost.service.ParamRequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SuppressWarnings("SameReturnValue")
@Profile("test")
@Configuration
@RequiredArgsConstructor
public class DbInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbInitializer.class);

  private ParamRequestService service;

  @Autowired
  public DbInitializer(ParamRequestService service) {
    this.service = service;
  }

  @SuppressWarnings("SameReturnValue")
  @Bean
  public boolean instantiateDatabase() {
    try{
      LOGGER.info("Mockando os dados do JSON");
      service.getVingadoresConsumerJson();
      service.getLigaDaJusticaConsumerXml();
      LOGGER.info("Dados mockados com sucesso");
    }catch(Exception e){
      LOGGER.info("Erro ao mockar dados");
    }

    return false;
  }
}
