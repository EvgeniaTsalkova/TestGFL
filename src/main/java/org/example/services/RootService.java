package org.example.services;

import org.example.dao.EquationDAO;
import org.example.dao.RootDAO;
import org.example.models.Equation;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RootService {
    private final RootDAO rootDAO;
    private final EquationDAO equationDAO;

    @Autowired
    public RootService(RootDAO rootDAO, EquationDAO equationDAO) {
        this.rootDAO = rootDAO;
        this.equationDAO = equationDAO;
    }

    public void save(String root, Integer equationId) {
        Equation equation = equationDAO.getEquation(equationId);
        try {
            rootDAO.save(root, equation);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        }
    }
}
