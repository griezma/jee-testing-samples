package mangri.sample.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "fibos")
@AllArgsConstructor
public class Fibonacci {
    @Id
    @Getter
    @Column(name = "num")
    private Integer number;

    @Getter
    private Integer fibo;

    Fibonacci() {}
}