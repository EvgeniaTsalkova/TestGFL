package org.example.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "root")
public class Root {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    private Double rootValue;

    @ManyToMany(mappedBy = "roots")
    private List<Equation> equations;

    public Root(int id, Double rootValue) {
        this.id = id;
        this.rootValue = rootValue;
    }

    public Root() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getRootValue() {
        return rootValue;
    }

    public void setRootValue(Double rootValue) {
        this.rootValue = rootValue;
    }

    public List<Equation> getEquations() {
        return equations;
    }

    public void setEquations(List<Equation> equations) {
        this.equations = equations;
    }
}
