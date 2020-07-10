package mk.ukim.finki.emt.lab.infra.hibernate;

import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DomainObjectIdCustomType<ID extends DomainObjectId> extends AbstractSingleColumnStandardBasicType<ID>
        implements ResultSetIdentifierConsumer {


    public DomainObjectIdCustomType(@NonNull DomainObjectIdTypeDescriptor<ID> domainObjectIdTypeDescriptor) {
        super(VarcharTypeDescriptor.INSTANCE, domainObjectIdTypeDescriptor);
    }

    @Override
    public Serializable consumeIdentifier(ResultSet resultSet) {
        try {
            var id = resultSet.getString(1);
            return getJavaTypeDescriptor().fromString(id);
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not extract ID from ResultSet", ex);
        }
    }

    @Override
    public String getName() {
        return getJavaTypeDescriptor().getJavaType().getSimpleName();
    }
}
