package mk.ukim.finki.emt.lab.infra.hibernate;

import mk.ukim.finki.emt.lab.domain.model.RentalId;

public class RentalIdType extends DomainObjectIdCustomType<RentalId> {
    private static final DomainObjectIdTypeDescriptor<RentalId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            RentalId.class, RentalId::new);

    public RentalIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
