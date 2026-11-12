package br.com.alura.agenda.database.converter;

import androidx.room.TypeConverter;

import br.com.alura.agenda.model.TipoTelefone;

public class TelefoneConverter {
    @TypeConverter
    public String toString(TipoTelefone tipo) {
        if(tipo != null) {
            return tipo.name();
        }
        return null;
    }

    @TypeConverter
    public TipoTelefone toTipoTelefone(String valor) {
        if(valor != null) {
            return TipoTelefone.valueOf(valor);
        }
        return TipoTelefone.FIXO;   // retorna fixo como default
    }
}
