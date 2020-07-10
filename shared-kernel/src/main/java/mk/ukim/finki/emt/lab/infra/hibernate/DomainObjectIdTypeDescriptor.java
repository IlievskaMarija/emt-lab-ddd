package mk.ukim.finki.emt.lab.infra.hibernate;

import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Function;


public class DomainObjectIdTypeDescriptor<ID extends DomainObjectId> extends AbstractTypeDescriptor<ID> {

    private final Function<String, ID> factory;

    public DomainObjectIdTypeDescriptor(@NonNull Class<ID> type, @NonNull Function<String, ID> factory) {
        super(type);
        this.factory = Objects.requireNonNull(factory, "factory must not be null");
    }

    @Override
    public String toString(ID value) {
        return value.toUUID();
    }

    @Override
    public ID fromString(String string) {
        return factory.apply(string);
    }

    @Override
    public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (type.isAssignableFrom(getJavaType())) {
            return type.cast(value);
        }
        if (type.isAssignableFrom(String.class)) {
            return type.cast(toString(value));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> ID wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (getJavaType().isInstance(value)) {
            return getJavaType().cast(value);
        }
        if (value instanceof String) {
            return fromString((String) value);
        }
        throw unknownWrap(value.getClass());
    }
}
