package com.university.techcorp.engine;

import com.university.techcorp.ui.ConsoleUI;

public class ConsoleGameRunner {

    private GameEngine engine;
    private ConsoleUI ui;
    private boolean running;

    public ConsoleGameRunner(GameEngine engine, ConsoleUI ui) {
        if (engine == null) {
            throw new IllegalArgumentException("Game engine cannot be null.");
        }

        if (ui == null) {
            throw new IllegalArgumentException("Console UI cannot be null.");
        }

        this.engine = engine;
        this.ui = ui;
        this.running = true;
    }

    public void run() {
        while (running && !engine.isGameFinished()) {
            ui.showTurnHeader(engine.getTurn(), engine.getMaxTurns());
            ui.showCompanyStatus(engine.getCompany());
            ui.showMainMenu();

            int choice = ui.readChoice();
            handleChoice(choice);
        }

        ui.showMessage("Game finished. Result: " + engine.getResult());
        ui.close();
    }

    private void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> ui.showCompanyStatus(engine.getCompany());
                case 2 -> ui.showEmployees(engine.getCompany());
                case 3 -> ui.showProjects(engine.getCompany());
                case 4 -> {
                    engine.startAllPlannedProjects();
                    ui.showMessage("All planned projects have been started.");
                }
                case 5 -> {
                    engine.assignAllEmployeesToFirstProject();
                    ui.showMessage("All employees assigned to the first project.");
                }
                case 6 -> {
                    engine.workOneTurn();
                    ui.showMessage("Projects worked for one turn.");
                }
                case 7 -> {
                    engine.paySalaries();
                    ui.showMessage("Salaries paid.");
                }
                case 8 -> {
                    engine.collectRevenue();
                    ui.showMessage("Revenue collected.");
                }
                case 9 -> {
                    engine.nextTurn();
                    ui.showMessage("Next turn processed.");
                }
                case 0 -> {
                    running = false;
                    ui.showMessage("Exiting game.");
                }
                default -> ui.showMessage("Invalid menu option.");
            }
        } catch (IllegalArgumentException exception) {
            ui.showMessage("Error: " + exception.getMessage());
        }
    }
}