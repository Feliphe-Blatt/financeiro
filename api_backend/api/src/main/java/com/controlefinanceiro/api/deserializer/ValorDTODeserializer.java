package com.controlefinanceiro.api.deserializer;

import com.controlefinanceiro.api.dto.MovimentacaoDTO.ValorDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class ValorDTODeserializer extends JsonDeserializer<ValorDTO> {
  
  @Override
  public ValorDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    
    // Se o node é um valor numérico
    if (node.isNumber()) {
      BigDecimal valor = new BigDecimal(node.asText());
      return new ValorDTO(valor);
    }
    // Se o node é um objeto
    else if (node.isObject()) {
      BigDecimal valor = node.has("valor") ? new BigDecimal(node.get("valor").asText()) : null;
      String tipo = node.has("tipo") ? node.get("tipo").asText() : "DESPESA";
      return new ValorDTO(valor, tipo);
    }
    
    // Retorna um valor padrão em caso de formato desconhecido
    return new ValorDTO();
  }
}