package org.example.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "equation")
public class Equation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "equation")
    private String equation;

    @ManyToMany
    @JoinTable( name = "equation_root",
            joinColumns = @JoinColumn(name = "equation_id"),
            inverseJoinColumns = @JoinColumn(name = "root_id"))
    private List<Root> roots;

    public Equation(int id, String equation) {
        this.id = id;
        this.equation = equation;
    }

    public Equation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public List<Root> getRoots() {
        return roots;
    }

    public void setRoots(List<Root> roots) {
        this.roots = roots;
    }
}
