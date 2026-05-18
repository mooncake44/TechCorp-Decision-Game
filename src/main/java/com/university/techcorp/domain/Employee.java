package com.university.techcorp.domain;

public abstract class Employee {

    private String name;
    private int skill;
    private double salary;

    public Employee(String name, int skill, double salary) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name cannot be blank.");
        }

        if (skill <= 0) {
            throw new IllegalArgumentException("Skill must be positive.");
        }

        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }

        this.name = name;
        this.skill = skill;
        this.salary = salary;
    }

    // Different employee types will work in different ways.
    public abstract int work();

    public abstract String getRole();

    public String getName() {
        return name;
    }

    public int getSkill() {
        return skill;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return getRole() + " - " + name + " | skill: " + skill + " | salary: " + salary;
    }
}