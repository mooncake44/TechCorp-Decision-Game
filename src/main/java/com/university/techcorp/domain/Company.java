package com.university.techcorp.domain;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private String name;
    private double cash;
    private int reputation;
    private List<Employee> employees;
    private List<Project> projects;

    public Company(String name, double cash) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be blank.");
        }

        if (cash < 0) {
            throw new IllegalArgumentException("Cash cannot be negative.");
        }

        this.name = name;
        this.cash = cash;
        this.reputation = 0;
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public void hire(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }

        employees.add(employee);
    }

    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        projects.add(project);
    }

    public void workOnProjects() {
        for (Project project : projects) {
            project.workOneTurn();
        }
    }

    public void startAllPlannedProjects() {
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.PLANNED) {
                project.start();
            }
        }
    }

    public void paySalaries() {
        for (Employee employee : employees) {
            cash -= employee.getSalary();
        }
    }

    public void collectRevenue() {
        for (Project project : projects) {
            if (project.isFinished() && !project.isPaid()) {
                cash += project.getReward();
                reputation += 10;
                project.markAsPaid();
            }
        }
    }

    public void reduceCash(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }

        cash -= amount;
    }

    public void addCash(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }

        cash += amount;
    }

    public void addReputation(int amount) {
        reputation += amount;
    }

    public boolean isBankrupt() {
        return cash < 0;
    }

    public boolean allProjectsFinished() {
        if (projects.isEmpty()) {
            return false;
        }

        for (Project project : projects) {
            if (!project.isFinished()) {
                return false;
            }
        }

        return true;
    }

    public boolean hasFinishedStrategicProject() {
        for (Project project : projects) {
            if (project.isStrategic() && project.isFinished()) {
                return true;
            }
        }

        return false;
    }

    public int countFinishedProjects() {
        int count = 0;

        for (Project project : projects) {
            if (project.isFinished()) {
                count++;
            }
        }

        return count;
    }

    public double calculateCompanyValue() {
        return cash + reputation * 1000 + countFinishedProjects() * 5000;
    }

    public String getName() {
        return name;
    }

    public double getCash() {
        return cash;
    }

    public int getReputation() {
        return reputation;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return name
                + " | cash: " + cash
                + " | reputation: " + reputation
                + " | employees: " + employees.size()
                + " | projects: " + projects.size();
    }
}