package mk.ukim.finki.emt.lab.domain.model;

import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;

/**
 * Value object representing a {@link Movie} ID.
 */
public class MovieId extends DomainObjectId {
    public MovieId(String uuid) {
        super(uuid);
    }
}