package mk.ukim.finki.emt.lab.domain.financial.converter;

import mk.ukim.finki.emt.lab.domain.financial.Fee4K;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class VATAttributeConverter implements AttributeConverter<Fee4K, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Fee4K attribute) {
        return attribute == null ? null : attribute.toInteger();
    }

    @Override
    public Fee4K convertToEntityAttribute(Integer dbData) {
        return Fee4K.valueOf(dbData);
    }
}
