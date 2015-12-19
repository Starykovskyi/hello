package hello.processor;

import hello.model.Contact;
import hello.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.Collection;


/**
 * Created by Assares on 18.12.2015.
 */
public class ContactBetweenSelecter {
    private Long querySize = 0L;
    private Long pointer = 0L;

    protected String query;
    protected Session session = null;
    protected Criteria criteria = null;
    protected int  alpha;

    public boolean available(){
        return pointer < querySize;
    }

    public ContactBetweenSelecter(String name, int alpha){
        this.alpha = alpha;
        this.query = "not name ~ '" + name + "'";
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.querySize = (Long) session.createCriteria(Contact.class)
                .setReadOnly(true)
                .add(Restrictions.sqlRestriction(query))
                .setProjection(Projections.countDistinct("id")).uniqueResult();

    }

    public Collection<Contact> getFilteredList(){
        if(available()){
            Long start = pointer;
            Long end  = (pointer += alpha);
            criteria = session.createCriteria(Contact.class)
                    .setReadOnly(true)
                    .add(Restrictions.sqlRestriction(query))
                    .add(Restrictions.between("id", start, end));
            return criteria.list();
        }else{
            session.close();
            return null;
        }
    }
}
