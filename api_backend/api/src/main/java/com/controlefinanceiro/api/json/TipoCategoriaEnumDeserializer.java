package com.controlefinanceiro.api.json;

import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class TipoCategoriaEnumDeserializer extends JsonDeserializer<TipoCategoriaEnum> {
    @Override
    public TipoCategoriaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return TipoCategoriaEnum.valueOf(value.trim().toUpperCase());
    }
}