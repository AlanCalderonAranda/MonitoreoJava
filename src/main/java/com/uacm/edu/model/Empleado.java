package com.uacm.edu.model;

public class Empleado extends Persona{
    private int numEmpleado;
    private double salario;

    public Empleado(int numEmpleado, String nombre, int edad, char sexo, double salario) {
        super(nombre, edad, sexo);
        this.numEmpleado = numEmpleado;
        this.salario = salario;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "numEmpleado=" + numEmpleado + ", salario=" + salario + '}';
    }
}
