package org.example.services;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import org.example.dao.EquationDAO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EquationService {

    private final EquationDAO equationDAO;

    public EquationService(EquationDAO equationDAO) {
        this.equationDAO = equationDAO;
    }

    public boolean checkForCorrectness(String equation) {
        String[] equationParts = equation.split("=");

        String left = equationParts[0];
        String right = equationParts[1];
        Expression expression1 = new ExpressionBuilder(left).variable("x").build()
                .setVariable("x", 0.0);
        ValidationResult result1 = expression1.validate();

        Expression expression2 = new ExpressionBuilder(right).variable("x").build()
                .setVariable("x", 0.0);
        ValidationResult result2 = expression2.validate();

        return result1.isValid() && result2.isValid();
    }

    public boolean checkForBrackets(String equation) {
        int countBrackets = 0;

        for (char c : equation.toCharArray()) {
                if (c == '(') {
                    countBrackets++;
                } else if (c == ')') {
                    countBrackets--;
                    if (countBrackets < 0) {
                        return false;
                    }
                }
            }
           return countBrackets == 0;
    }

    public boolean isRoot(Double root, Integer equationId) {
        String equationValue = equationDAO.getEquation(equationId).getEquation();
        String[] equationParts = equationValue.split("=");

        String left = equationParts[0];
        String right = equationParts[1];

        if (left.contains("x")) {
            return evaluateExpressionAndCheckRoot(left, right, root);
        } else {
            return evaluateExpressionAndCheckRoot(right, left, root);
        }
    }

    public boolean evaluateExpressionAndCheckRoot(String expr, String answ, Double root) {
        Expression expression = new ExpressionBuilder(expr).variable("x").build()
                .setVariable("x", root);
        Double result = expression.evaluate();
        Double answer = Double.parseDouble(answ);

        return result.equals(answer);
    }

    public Integer saveEquation(String equation) {
        try {
            return equationDAO.save(equation);
        } catch (ConstraintViolationException e) {
            return equationDAO.getEquationByValue(equation).getId();
        }
    }

    public Integer numsCount(String equation) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(equation);
        List<String> numList = new ArrayList<>();
        while (matcher.find()) {
            numList.add(matcher.group());
        }
        return numList.size();
    }

//    public List<Equation> findEquationsByRoots(String roots) {
//        Set<Double> rootSet = Arrays.stream(roots.split(","))
//                .map(String::trim)
//                .map(Double::parseDouble)
//                .collect(Collectors.toSet());
//
//        return equationDAO.findEquationsByRoots(rootSet);
//    }
}
