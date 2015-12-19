package hello.model;

/**
 * Created by Assares on 15.12.2015.
 */
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
//Contact entity
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "contacts")
public class Contact implements Serializable{

    @Id
    @Column(name= "id")
    @Index(name = "user_id_hidx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Index(name = "user_name_hidx")
    @Column(name= "name", length=50)
    private String name;

    public Contact() {

    }
    public Contact(Long id, String name) {
        this.id = id;
        this.name = name;

    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
