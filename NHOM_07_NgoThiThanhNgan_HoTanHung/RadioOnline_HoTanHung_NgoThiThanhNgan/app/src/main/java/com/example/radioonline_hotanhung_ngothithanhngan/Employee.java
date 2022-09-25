package com.example.radioonline_hotanhung_ngothithanhngan;

public class Employee {
    String name;
    String urc;

    public Employee(String name, String urc) {
        this.name = name;
        this.urc = urc;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", urc='" + urc + '\'' +
                '}';
    }
}
