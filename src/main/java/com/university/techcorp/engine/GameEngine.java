package com.university.techcorp.engine;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Employee;
import com.university.techcorp.domain.Project;
import com.university.techcorp.domain.ProjectStatus;
import com.university.techcorp.events.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private Company company;
    private int turn;
    private int maxTurns;
    private GameResult result;
    private List<GameEvent> events;
    private Random random;

    public GameEngine(Company company) {
        this(company, 12);
    }

    public GameEngine(Company company, int maxTurns) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        if (maxTurns <= 0) {
            throw new IllegalArgumentException("Max turns must be positive.");
        }

        this.company = company;
        this.turn = 1;
        this.maxTurns = maxTurns;
        this.result = GameResult.IN_PROGRESS;
        this.events = new ArrayList<>();
        this.random = new Random();
    }

    public void startAllPlannedProjects() {
        if (isGameFinished()) {
            return;
        }

        company.startAllPlannedProjects();
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

    private void applyRandomEvent() {
        if (events.isEmpty()) {
            return;
        }

        // 30% chance that a random event happens during a turn.
        int chance = random.nextInt(100);

        if (chance < 30) {
            int eventIndex = random.nextInt(events.size());
            GameEvent event = events.get(eventIndex);
            event.apply(company);
        }
    }

    private void evaluateGameResult() {
        if (company.isBankrupt()) {
            result = GameResult.PLAYER_LOSES;
            return;
        }

        if (company.hasFinishedStrategicProject()) {
            result = GameResult.PLAYER_WINS;
            return;
        }

        if (company.allProjectsFinished()) {
            result = GameResult.PLAYER_WINS;
            return;
        }

        if (turn >= maxTurns) {
            if (company.calculateCompanyValue() > 0) {
                result = GameResult.PLAYER_WINS;
            } else {
                result = GameResult.PLAYER_LOSES;
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

    public GameResult getResult() {
        return result;
    }

    public List<GameEvent> getEvents() {
        return events;
    }
}