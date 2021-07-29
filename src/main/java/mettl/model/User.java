package mettl.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
@Data
@ToString(exclude = "accList")
public class User {
    @Id
    Integer id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    Collection<Account> accList;
//
//    @Override
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//        sb.append("id = ").append(id);
//        sb.append(" , ");
//        sb.append("name = ").append(name);
////        accList.forEach(account -> sb.append(account.toString()));
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
}
