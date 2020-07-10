package mk.ukim.finki.emt.lab.domain.base;

public interface DeletableDomainObject extends DomainObject {

    boolean isDeleted();

    void delete();
}
