package ru.pogo.sbrf.cu.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.ref.Nominal;

/*import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;*/
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Data
//@Entity // Указывает, что данный класс является сущностью
//@Table(name = "students")
public class ATMImpl implements ATM {
    private static Logger logger = LoggerFactory.getLogger(ATMImpl.class);

    private final List<Cassette> cassettes;

    private Integer totalSum;

   @JsonCreator
   public ATMImpl(@JsonProperty("balance")  List<Cassette> balance, @JsonProperty("totalSum") Integer totalSum) {
       System.out.println("JsonCreator makes object...");
       cassettes = new ArrayList<>();
       for (var nominal : balance)
           cassettes.add(new CassetteImpl(nominal.getNominal(), nominal.count()));
       this.totalSum = totalSum;
   }

    public ATMImpl(){
       this.cassettes = getDefaultCassettes();
       totalSum = 0;
    }

    @Override
    public Integer getTotalSum() {
        return totalSum;
    }

    @Override
    public List<Cassette> getCassettes() {
        return cassettes;
    }

    @Override
    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public List<Cassette> getDefaultCassettes(){
        var res  = new ArrayList<Cassette>();
        for (var nominal : Nominal.values())
            res.add(new CassetteImpl(nominal, 0));
        return res;
    }
}
