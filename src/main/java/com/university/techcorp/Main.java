package com.university.techcorp;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Project;
import com.university.techcorp.domain.Developer;
import com.university.techcorp.domain.Tester;
import com.university.techcorp.engine.GameEngine;
import com.university.techcorp.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {

        // Create company
        Company company = new Company("TechCorp", 50000);

        // Hire employees
        company.hire(new Developer("Anna", 8, 7000));
        company.hire(new Tester("Piotr", 6, 6000));

        // Create project
        Project project = new Project("Mobile_App", 40);
        company.addProject(project);

        // Initialize UI and Engine
        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, ui);

        // Run simulation
        engine.run();
    }
}