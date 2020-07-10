package mk.ukim.finki.emt.lab.domain.model.converter;

import mk.ukim.finki.emt.lab.domain.model.MovieId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MovieIdConverter implements AttributeConverter<MovieId, String> {
    @Override
    public String convertToDatabaseColumn(MovieId attribute) {
        return attribute == null ? null : attribute.toUUID();
    }

    @Override
    public MovieId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new MovieId(dbData);
    }
}
