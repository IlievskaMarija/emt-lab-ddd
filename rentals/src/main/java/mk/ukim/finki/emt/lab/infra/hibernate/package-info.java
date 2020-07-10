@TypeDefs({
        @TypeDef(defaultForType = RentalId.class, typeClass = RentalIdType.class),
        @TypeDef(defaultForType = RentedItemId.class, typeClass = RentedItemIdType.class)
})
package mk.ukim.finki.emt.lab.infra.hibernate;

import mk.ukim.finki.emt.lab.domain.model.RentalId;
import mk.ukim.finki.emt.lab.domain.model.RentedItemId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;