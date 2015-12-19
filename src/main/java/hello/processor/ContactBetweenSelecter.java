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
    private Long queryMaxInd = 0L;// find query size
    private Long pointer = 0L;// current point

    protected String query; //our query
    protected Session session = null; // session
    protected Criteria criteria = null; // our criteria
    protected int  alpha; // count element from 1 querry

    //true - if we havemore elements
    public boolean available(){
        return pointer <= queryMaxInd;
    }

    public ContactBetweenSelecter(String name, int alpha){
        this.alpha = alpha;
        this.query = "not name ~ '" + name + "'";
        this.session = HibernateUtil.getSessionFactory().openSession();
        //find borders all element
        //max
        this.queryMaxInd = (Long) session.createCriteria(Contact.class)
                .setReadOnly(true)
                .add(Restrictions.sqlRestriction(query))
                .setProjection(Projections.max("id")).uniqueResult();
        //min
        this.pointer = (Long) session.createCriteria(Contact.class)
                .setReadOnly(true)
                .add(Restrictions.sqlRestriction(query))
                .setProjection(Projections.min("id")).uniqueResult();

    }
    //get rows "pointer" and "pointer + alpha"
    //get rows in 1 itertion between "pointer" and "pointer + alpha"
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
