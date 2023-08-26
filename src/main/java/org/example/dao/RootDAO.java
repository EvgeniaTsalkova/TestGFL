package org.example.dao;

import org.example.models.Equation;
import org.example.models.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class RootDAO {
    private final SessionFactory sessionFactory;

    public RootDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(String rootValue, Equation equation) {
        Session session = sessionFactory.getCurrentSession();
        Root root = new Root();
        root.setRootValue(Double.parseDouble(rootValue));
        root.setEquations(new ArrayList<>(Collections.singletonList(equation)));
        session.save(root);
    }
}
