package com.university.techcorp.engine;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Employee;
import com.university.techcorp.domain.Project;
import com.university.techcorp.events.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private Company company;
    private int turn;
    private int maxTurns;
    private double targetCompanyValue;
    private GameResult result;
    private List<GameEvent> events;
    private Random random;
    private String lastEventMessage;
    private String finalMessage;

    public GameEngine(Company company) {
        this(company, 12, 100000);
    }

    public GameEngine(Company company, int maxTurns) {
        this(company, maxTurns, 100000);
    }

    public GameEngine(Company company, int maxTurns, double targetCompanyValue) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        if (maxTurns <= 0) {
            throw new IllegalArgumentException("Max turns must be positive.");
        }

        if (targetCompanyValue <= 0) {
            throw new IllegalArgumentException("Target company value must be positive.");
        }

        this.company = company;
        this.turn = 1;
        this.maxTurns = maxTurns;
        this.targetCompanyValue = targetCompanyValue;
        this.result = GameResult.IN_PROGRESS;
        this.events = new ArrayList<>();
        this.random = new Random();
        this.lastEventMessage = "No event yet.";
        this.finalMessage = "Game is still in progress.";
    }

    public void startAllPlannedProjects() {
        if (isGameFinished()) {
            return;
        }

        company.startAllPlannedProjects();
    }

    public void startProjectByIndex(int projectIndex) {
        if (isGameFinished()) {
            return;
        }

        Project project = getProjectByIndex(projectIndex);
        company.startProject(project);
    }

    public void assignAllEmployeesToProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        for (Employee employee : company.getEmployees()) {
            project.addEmployee(employee);
        }
    }

    public void assignAllEmployeesToFirstProject() {
        if (company.getProjects().isEmpty()) {
            return;
        }

        Project firstProject = company.getProjects().get(0);
        assignAllEmployeesToProject(firstProject);
    }

    public void assignAllEmployeesToProjectByIndex(int projectIndex) {
        if (isGameFinished()) {
            return;
        }

        Project project = getProjectByIndex(projectIndex);
        assignAllEmployeesToProject(project);
    }

    public void workOneTurn() {
        if (isGameFinished()) {
            return;
        }

        company.workOnProjects();
        evaluateGameResult();
    }

    public void paySalaries() {
        if (isGameFinished()) {
            return;
        }

        company.paySalaries();
        evaluateGameResult();
    }

    public void collectRevenue() {
        if (isGameFinished()) {
            return;
        }

        company.collectRevenue();
        evaluateGameResult();
    }

    public void nextTurn() {
        if (isGameFinished()) {
            return;
        }

        lastEventMessage = "No random event this turn.";

        company.workOnProjects();
        company.paySalaries();
        company.collectRevenue();
        applyRandomEvent();

        evaluateGameResult();

        if (!isGameFinished()) {
            turn++;
        }
    }

    public void putProjectOnHold(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        project.putOnHold();
    }

    public void resumeProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        project.start();
    }

    public void cancelProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }

        project.cancel();
        evaluateGameResult();
    }

    public void addEvent(GameEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        events.add(event);
    }

    private Project getProjectByIndex(int projectIndex) {
        if (projectIndex < 0 || projectIndex >= company.getProjects().size()) {
            throw new IllegalArgumentException("Invalid project index.");
        }

        return company.getProjects().get(projectIndex);
    }

    private void applyRandomEvent() {
        if (events.isEmpty()) {
            lastEventMessage = "No events available.";
            return;
        }

        int chance = random.nextInt(100);

        if (chance < 30) {
            int eventIndex = random.nextInt(events.size());
            GameEvent event = events.get(eventIndex);

            event.apply(company);

            lastEventMessage = event.getName() + ": " + event.getDescription();
        } else {
            lastEventMessage = "No random event this turn.";
        }
    }

    private void evaluateGameResult() {
        if (company.isBankrupt()) {
            result = GameResult.PLAYER_LOSES;
            finalMessage = "You lost because the company went bankrupt.";
            return;
        }

        if (company.allProjectsFinished()) {
            if (company.calculateCompanyValue() >= targetCompanyValue) {
                result = GameResult.PLAYER_WINS;
                finalMessage = "You won because all projects were completed and the company reached the target value.";
            } else {
                result = GameResult.PLAYER_LOSES;
                finalMessage = "You lost because all projects were completed, but the company did not reach the target value.";
            }
            return;
        }

        if (turn >= maxTurns) {
            if (company.calculateCompanyValue() >= targetCompanyValue) {
                result = GameResult.PLAYER_WINS;
                finalMessage = "You won because the company reached the target value by the final turn.";
            } else {
                result = GameResult.PLAYER_LOSES;
                finalMessage = "You lost because the company did not reach the target value by the final turn.";
            }
        }
    }

    public boolean isGameFinished() {
        return result != GameResult.IN_PROGRESS;
    }

    public String getProjectStatusText() {
        StringBuilder builder = new StringBuilder();

        for (Project project : company.getProjects()) {
            builder.append(project.getName())
                    .append(" | ")
                    .append(project.getStatus())
                    .append(" | progress: ")
                    .append(project.getProgress())
                    .append("/")
                    .append(project.getRequiredWork())
                    .append(" | reward: ")
                    .append(project.getReward())
                    .append("\n");
        }

        return builder.toString();
    }

    public String getEmployeeStatusText() {
        StringBuilder builder = new StringBuilder();

        for (Employee employee : company.getEmployees()) {
            builder.append(employee.getRole())
                    .append(" - ")
                    .append(employee.getName())
                    .append(" | skill: ")
                    .append(employee.getSkill())
                    .append(" | salary: ")
                    .append(employee.getSalary())
                    .append("\n");
        }

        return builder.toString();
    }

    public String getCompanyStatusText() {
        return "Company: " + company.getName()
                + "\nCash: " + company.getCash()
                + "\nReputation: " + company.getReputation()
                + "\nCompany value: " + company.calculateCompanyValue()
                + "\nTarget value: " + targetCompanyValue
                + "\nTurn: " + turn + "/" + maxTurns
                + "\nResult: " + result;
    }

    public Company getCompany() {
        return company;
    }

    public int getTurn() {
        return turn;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public double getTargetCompanyValue() {
        return targetCompanyValue;
    }

    public GameResult getResult() {
        return result;
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    public String getLastEventMessage() {
        return lastEventMessage;
    }

    public String getFinalMessage() {
        return finalMessage;
    }
}