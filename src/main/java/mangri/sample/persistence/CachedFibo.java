package mangri.sample.persistence;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "fibos")
@NamedQuery(name = "fibo", query = "select fibo from CachedFibo where base=:base")
@AllArgsConstructor
public class CachedFibo {
    @Id
    @Getter
    @Column
    private Integer base;

    @Getter
    @Column(columnDefinition = "decimal(50,0)")
    private BigInteger fibo;

    CachedFibo() {}
}