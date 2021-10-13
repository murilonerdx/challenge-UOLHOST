package com.murilonerdx.uolhost.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.murilonerdx.uolhost.model.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeroDTO {
    List<Hero> vingadores = new ArrayList<>();
    List<Hero> liga_da_justica = new ArrayList<>();
}
