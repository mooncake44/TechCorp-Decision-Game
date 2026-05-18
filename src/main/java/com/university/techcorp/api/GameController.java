package com.university.techcorp.api;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Developer;
import com.university.techcorp.domain.Employee;
import com.university.techcorp.domain.Manager;
import com.university.techcorp.domain.Project;
import com.university.techcorp.domain.Tester;
import com.university.techcorp.engine.GameEngine;
import com.university.techcorp.events.ClientBonusEvent;
import com.university.techcorp.events.MarketSlowdownEvent;
import com.university.techcorp.events.ProductivityBoostEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    private GameEngine engine;

    public GameController() {
        resetEngine();
    }

    @GetMapping("/game/state")
    public Map<String, Object> getGameState() {
        return buildResponse("Game state loaded.");
    }

    @GetMapping("/game/start-projects")
    public Map<String, Object> startProjects() {
        engine.startAllPlannedProjects();
        return buildResponse("All planned projects have been started.");
    }

    @GetMapping("/game/assign-employees")
    public Map<String, Object> assignEmployees() {
        engine.assignAllEmployeesToFirstProject();
        return buildResponse("All employees assigned to the first project.");
    }

    @GetMapping("/game/work")
    public Map<String, Object> workOneTurn() {
        engine.workOneTurn();
        return buildResponse("Projects worked for one turn.");
    }

    @GetMapping("/game/pay-salaries")
    public Map<String, Object> paySalaries() {
        engine.paySalaries();
        return buildResponse("Salaries paid.");
    }

    @GetMapping("/game/collect-revenue")
    public Map<String, Object> collectRevenue() {
        engine.collectRevenue();
        return buildResponse("Revenue collected.");
    }

    @GetMapping("/game/next-turn")
    public Map<String, Object> nextTurn() {
        engine.nextTurn();
        return buildResponse("Next full turn processed.");
    }

    @GetMapping("/game/reset")
    public Map<String, Object> resetGame() {
        resetEngine();
        return buildResponse("Game has been reset.");
    }

    private void resetEngine() {
        Company company = createCompany();

        this.engine = new GameEngine(company, 12);

        this.engine.addEvent(new MarketSlowdownEvent());
        this.engine.addEvent(new ProductivityBoostEvent());
        this.engine.addEvent(new ClientBonusEvent());
    }

    private Company createCompany() {
        Company company = new Company("TechCorp", 50000);

        company.hire(new Developer("Anna", 8, 7000));
        company.hire(new Tester("Piotr", 6, 6000));
        company.hire(new Manager("Ewa", 7, 9000));

        Project mobileApp = new Project("Mobile App", 60, 25000, true);
        Project website = new Project("Website", 35, 12000, false);
        Project securityAudit = new Project("Security Audit", 45, 18000, false);

        company.addProject(mobileApp);
        company.addProject(website);
        company.addProject(securityAudit);

        return company;
    }

    private Map<String, Object> buildResponse(String message) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("message", message);
        response.put("turn", engine.getTurn());
        response.put("maxTurns", engine.getMaxTurns());
        response.put("result", engine.getResult().toString());
        response.put("company", buildCompanyData());
        response.put("employees", buildEmployeesData());
        response.put("projects", buildProjectsData());

        return response;
    }

    private Map<String, Object> buildCompanyData() {
        Company company = engine.getCompany();

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", company.getName());
        data.put("cash", company.getCash());
        data.put("reputation", company.getReputation());
        data.put("value", company.calculateCompanyValue());
        data.put("bankrupt", company.isBankrupt());

        return data;
    }

    private List<Map<String, Object>> buildEmployeesData() {
        List<Map<String, Object>> data = new ArrayList<>();

        for (Employee employee : engine.getCompany().getEmployees()) {
            Map<String, Object> employeeData = new LinkedHashMap<>();

            employeeData.put("name", employee.getName());
            employeeData.put("role", employee.getRole());
            employeeData.put("skill", employee.getSkill());
            employeeData.put("salary", employee.getSalary());

            data.add(employeeData);
        }

        return data;
    }

    private List<Map<String, Object>> buildProjectsData() {
        List<Map<String, Object>> data = new ArrayList<>();

        for (Project project : engine.getCompany().getProjects()) {
            Map<String, Object> projectData = new LinkedHashMap<>();

            projectData.put("name", project.getName());
            projectData.put("status", project.getStatus().toString());
            projectData.put("progress", project.getProgress());
            projectData.put("requiredWork", project.getRequiredWork());
            projectData.put("progressPercentage", project.getProgressPercentage());
            projectData.put("reward", project.getReward());
            projectData.put("strategic", project.isStrategic());
            projectData.put("teamSize", project.getTeam().size());
            projectData.put("paid", project.isPaid());

            data.add(projectData);
        }

        return data;
    }
}