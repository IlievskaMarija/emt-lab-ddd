package mk.ukim.finki.emt.lab.infra.hibernate;

import mk.ukim.finki.emt.lab.domain.model.MovieId;

public class ProductIdType extends DomainObjectIdCustomType<MovieId> {

    private static final DomainObjectIdTypeDescriptor<MovieId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            MovieId.class, MovieId::new);

    public ProductIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
