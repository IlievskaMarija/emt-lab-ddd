package mk.ukim.finki.emt.lab.infra.hibernate;

import mk.ukim.finki.emt.lab.domain.model.RentedItemId;

public class RentedItemIdType extends DomainObjectIdCustomType<RentedItemId> {
    private static final DomainObjectIdTypeDescriptor<RentedItemId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            RentedItemId.class, RentedItemId::new);

    public RentedItemIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
