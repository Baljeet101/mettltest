package mettl.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "transaction")
@ToString(exclude = "account")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "Value_Date")
    Date valDate;

    @Column(name = "Debit_Amt")
    Double debitAmt;

    @Column(name = "Debit_Credit")
    String type;

    @Column(name = "Trans_Narrative")
    String txNarrat;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "accnum", referencedColumnName = "Account_Number")
    Account account;
}
