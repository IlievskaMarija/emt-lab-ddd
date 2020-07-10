package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;

public class MovieId extends DomainObjectId {
    @JsonCreator
    public MovieId(String uuid) {
        super(uuid);
    }
}
