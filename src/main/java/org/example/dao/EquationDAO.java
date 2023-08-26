package org.example.dao;

import org.example.models.Equation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EquationDAO {

    private final SessionFactory sessionFactory;

    public EquationDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Integer save(String equation) {
        Session session = sessionFactory.getCurrentSession();
        Equation equation1 = new Equation();
        equation1.setEquation(equation);

        return (Integer) session.save(equation1);
    }

    @Transactional(readOnly = true)
    public Equation getEquation(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Equation.class, id);
    }

    @Transactional(readOnly = true)
    public Equation getEquationByValue(String equationValue) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Equation e WHERE e.equation = :equationValue", Equation.class)
                .setParameter("equationValue", equationValue).getSingleResult();
    }

    //    public List<Equation> findEquationsByRoots(Set<Double> rootSet) {
//        Session session = sessionFactory.getCurrentSession();
//
//        return null;
//    }
}
