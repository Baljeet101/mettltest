package mettl.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "Account_Number")
    Integer accNumber;

    @Column(name = "Account_Name")
    String accName;

    @Column(name = "Account_Type")
    String accType;

    @Column(name = "Balance_Date")
    Date balDate;

    @Column(name = "Currency")
    String  currency;

    @Column(name = "Open_Avail_Bal")
    Double openAvailBal;

    @OneToMany(mappedBy="account", fetch=FetchType.LAZY)
    Collection<Transaction> txList;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
