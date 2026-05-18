package com.university.techcorp.domain;

public class Manager extends Employee {

    public Manager(String name, int skill, double salary) {
        super(name, skill, salary);
    }

    @Override
    public int work() {
        // Managers improve organisation, so they contribute a stable bonus.
        return getSkill() + 3;
    }

    @Override
    public String getRole() {
        return "Manager";
    }
}