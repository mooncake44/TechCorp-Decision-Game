package com.university.techcorp.ui;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Employee;
import com.university.techcorp.domain.Project;

import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void showTurnHeader(int turn, int maxTurns) {
        System.out.println();
        System.out.println("=================================");
        System.out.println("TURN " + turn + " / " + maxTurns);
        System.out.println("=================================");
    }

    public void showMainMenu() {
        System.out.println();
        System.out.println("Choose an action:");
        System.out.println("1. Show company status");
        System.out.println("2. Show employees");
        System.out.println("3. Show projects");
        System.out.println("4. Start all planned projects");
        System.out.println("5. Assign all employees to first project");
        System.out.println("6. Work one turn");
        System.out.println("7. Pay salaries");
        System.out.println("8. Collect revenue");
        System.out.println("9. Next full turn");
        System.out.println("0. Exit game");
    }

    public int readChoice() {
        System.out.print("Enter choice: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        return choice;
    }

    public void showCompanyStatus(Company company) {
        System.out.println();
        System.out.println("=== COMPANY STATUS ===");
        System.out.println("Name: " + company.getName());
        System.out.println("Cash: " + company.getCash());
        System.out.println("Reputation: " + company.getReputation());
        System.out.println("Company value: " + company.calculateCompanyValue());
        System.out.println("Employees: " + company.getEmployees().size());
        System.out.println("Projects: " + company.getProjects().size());
    }

    public void showEmployees(Company company) {
        System.out.println();
        System.out.println("=== EMPLOYEES ===");

        if (company.getEmployees().isEmpty()) {
            System.out.println("No employees.");
            return;
        }

        for (Employee employee : company.getEmployees()) {
            System.out.println("- " + employee);
        }
    }

    public void showProjects(Company company) {
        System.out.println();
        System.out.println("=== PROJECTS ===");

        if (company.getProjects().isEmpty()) {
            System.out.println("No projects.");
            return;
        }

        for (Project project : company.getProjects()) {
            System.out.println("- " + project);

            if (!project.getTeam().isEmpty()) {
                System.out.println("  Team:");

                for (Employee employee : project.getTeam()) {
                    System.out.println("  * " + employee.getRole() + " - " + employee.getName());
                }
            } else {
                System.out.println("  Team: no employees assigned");
            }
        }
    }

    public void showMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}