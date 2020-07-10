package mk.ukim.finki.emt.lab.domain.base;

import org.springframework.lang.Nullable;

public interface ConcurrencySafeDomainObject extends DomainObject {

    @Nullable
    Long version();
}
