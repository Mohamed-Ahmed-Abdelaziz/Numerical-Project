package com.example.RF;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Bisection extends RootFinder{
    private double lowerLimit;
    private double upperLimit;
    private double result;
    private int iterations;
    private double toleranceError;
    private FunctionExpression function;
    private final static String stepsFile = "Bisection.txt";
    private long startTime, endTime, writeTime;

    public Bisection(boolean applyPrecision1, int precision1, double lowerLimit, double upperLimit, int iterations,double toleranceError, FunctionExpression function) {
        super(applyPrecision1, precision1);
        super.clearFile(stepsFile);
        this.lowerLimit=lowerLimit;
        this.upperLimit=upperLimit;
        this.iterations = iterations;
        this.function = function;
        this.toleranceError=toleranceError;
        String titles = String.format("%10s %20s %20s %20s %20s", "iteration", "(xl)","(xu)","(xr)", "relative error");
        writeFile(titles);
    }


    @Override
    public double getRoot() {
        if(this.function.evaluate(this.upperLimit)*this.function.evaluate(this.lowerLimit)>=0){
            throw new ArithmeticException ("No roots found between entered limits");
        }
        double xrOld=0;
        boolean first=true;
        double approximateError=1;
        String step="";
        startTime=System.currentTimeMillis();
        for (int i = 0; i <this.iterations ; i++) {
            this.result=round((this.lowerLimit+this.upperLimit)/2.0);
            if(round(this.function.evaluate(this.result)*this.function.evaluate(this.lowerLimit))>0){
                if(first){
                    step = String.format("%10s %20s %20s %20s %20s", i, this.lowerLimit, this.upperLimit,this.result,approximateError );
                    writeFile(step);
                    startTime+=writeTime;
                    this.lowerLimit=this.result;
                    xrOld=this.result;
                    first=false;

                }
                else {
                    approximateError=Math.abs(round(((this.result-xrOld)/this.result)));
                    System.out.println("Erroru: "+approximateError);
                    step = String.format("%10s %20s %20s %20s %20s", i, this.lowerLimit, this.upperLimit,this.result,approximateError );
                    writeFile(step);
                    startTime+=writeTime;
                    this.lowerLimit=this.result;
                    xrOld=this.lowerLimit;
                    if(approximateError<this.toleranceError){
                        endTime=System.currentTimeMillis();
                        return this.result;
                    }
                }

            }
            else if(round(this.function.evaluate(this.result)*this.function.evaluate(this.lowerLimit))<0){
                if(first){
                    step = String.format("%10s %20s %20s %20s %20s", i, this.lowerLimit, this.upperLimit,this.result,approximateError );
                    writeFile(step);
                    startTime+=writeTime;
                    this.upperLimit=this.result;
                    xrOld=this.result;
                    first=false;

                }
                else {
                    approximateError=Math.abs(round(((this.result-xrOld)/this.result)));
                    System.out.println("Erroru: "+approximateError);
                    step = String.format("%10s %20s %20s %20s %20s", i, this.lowerLimit, this.upperLimit,this.result,approximateError );
                    writeFile(step);
                    startTime+=writeTime;
                    this.upperLimit=this.result;
                    xrOld=this.upperLimit;
                    if(approximateError<this.toleranceError){
                        startTime+=writeTime;
                        endTime=System.currentTimeMillis();
                        return this.result;
                    }
                }

            }
            else {
                step = String.format("%10s %20s %20s %20s %20s", i, this.lowerLimit, this.upperLimit,this.result,approximateError );
                writeFile(step);
                startTime+=writeTime;
                endTime=System.currentTimeMillis();
                return this.result;
            }
        }
        step = String.format("%10s %20s %20s %20s %20s", this.iterations, this.lowerLimit, this.upperLimit,this.result,approximateError );
        writeFile(step);
        startTime+=writeTime;
        endTime=System.currentTimeMillis();
        return this.result;
    }

    public String getStepsFile() {
        return stepsFile;
    }
    private void writeFile(String step) {//write steps function
        long currTime = System.currentTimeMillis();
        try {
            FileWriter writer = new FileWriter(stepsFile, true);
            writer.write(step + "\n");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        writeTime = System.currentTimeMillis() - currTime;
    }


    public long getTime(){
        return endTime - startTime;
    }

    public static void main(String[] args) throws IOException {//for test
        String s="x^3-0.165*x^2+3.993*10^(-4)";
        FunctionExpression function=new FunctionExpression(s);
        Bisection F=new Bisection(true,5,0,0.11,10,0.0001,function);
        System.out.println(F.getRoot());

    }
}
