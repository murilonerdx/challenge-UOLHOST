package com.murilonerdx.uolhost.service;

import com.murilonerdx.uolhost.model.Hero;
import com.murilonerdx.uolhost.model.dto.HeroDTO;
import com.murilonerdx.uolhost.repository.HeroRepository;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParamRequestService {

  final String URL_PATH_VINGADORES =
      "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json";

  final String URL_PATH_LIGA_DA_JUSTICA =
      "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";

  private final HeroRepository heroRepository;

  final RestTemplateBuilder builder = new RestTemplateBuilder();
  final RestTemplate template = builder.build();

  public ParamRequestService(HeroRepository heroRepository) {
    this.heroRepository = heroRepository;
  }

  public void getVingadoresConsumerJson() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(
        Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
    template.getMessageConverters().add(0, converter);

    List<Hero> heros =
        Objects.requireNonNull(template.getForObject(URL_PATH_VINGADORES, HeroDTO.class))
            .getVingadores()
            .stream()
            .peek(x -> x.setNameGroup("Vingadores"))
            .collect(Collectors.toList());

    heroRepository.saveAll(heros);
  }

  public void getLigaDaJusticaConsumerXml() throws IOException {
    HeroDTO heroDTO = new HeroDTO();
    int lenght = searchHtmlElement(URL_PATH_LIGA_DA_JUSTICA, "codinome").size();
    for (int a = 0; a < lenght; a++) {
      heroDTO
          .getLiga_da_justica()
          .add(new Hero(uniqueHtmlElement(URL_PATH_LIGA_DA_JUSTICA, "codinome", a)));
    }
    heroRepository.saveAll(
            heroDTO.getLiga_da_justica().stream()
                    .peek(x -> x.setNameGroup("Liga da Justi??a"))
                    .collect(Collectors.toList()));
  }

  public Elements searchHtmlElement(String path, String param) throws IOException {
    return Jsoup.connect(path).get().select(param);
  }

  public String uniqueHtmlElement(String path, String param, int index) throws IOException {
    return Jsoup.connect(path).get().select(param).get(index).text();
  }

  public Hero sortedVingadores() {
    List<Hero> herosObtain =
        heroRepository.findAll().stream()
            .filter(x -> x.isObtain() && x.getNameGroup().equals("Vingadores"))
            .collect(Collectors.toList());
    if (herosObtain.isEmpty()) return null;
    int length = herosObtain.size();

    int getRandom = getRandom().nextInt(length);
    return setNotObtain(herosObtain.get(getRandom));
  }

  public Hero sortedLigaDaJustica() {
    List<Hero> herosObtain =
        heroRepository.findAll().stream()
            .filter(x -> x.isObtain() && x.getNameGroup().equals("Liga da Justi??a"))
            .collect(Collectors.toList());
    if (herosObtain.isEmpty()) return null;
    int length = herosObtain.size();

    int getRandom = getRandom().nextInt(length);
    return setNotObtain(herosObtain.get(getRandom));
  }

  public Hero setNotObtain(Hero hero){
    hero.setObtain(true);
    return heroRepository.save(hero);
  }

  public Random getRandom(){
    return new Random();
  }
}
