package com.example.RF;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;


public class FunctionExpression {
    Expression exp;

    public FunctionExpression(String str) throws UnknownFunctionOrVariableException
    {
        exp = new ExpressionBuilder(str).variable("x").build();
    }

    public double evaluate(double x)
    {
        exp.setVariable("x", x);
        return exp.evaluate();
    }

    public double differentiate(double x) throws ArithmeticException
    {

        double dx = 0.00001;
        double f1 = evaluate(x+dx);
        double f2 = evaluate(x);
        if (f1 - f2 == 0) throw new ArithmeticException("value of function is too big to differentiate.");
        return (f1 - f2) / dx;
    }
}
