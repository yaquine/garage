package rahile.abdelmounim.garage.domaine;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CodeIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object entity) throws HibernateException {

        Long nextVal = ((Number) session.createNativeQuery("SELECT nextval('accessoire_seq')").getSingleResult()).longValue();

        return "CODE-" + nextVal;
    }
}
