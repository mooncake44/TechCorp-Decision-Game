package com.university.techcorp.domain;

public class Developer extends Employee {

    public Developer(String name, int skill, double salary) {
        super(name, skill, salary);
    }

    @Override
    public int work() {
        // Developers are the strongest contributors to direct project progress.
        return getSkill() * 2;
    }

    @Override
    public String getRole() {
        return "Developer";
    }
}