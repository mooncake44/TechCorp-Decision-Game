# TechCorp-Decision-Game


TechCorp Simulator is a turn-based business decision-making game developed in Java.  
The player manages a technology company, starts projects, assigns employees, processes turns, handles costs and revenues, and tries to reach the target company value before the game ends.

The project was created as an educational Java project focused on object-oriented programming, game logic, project structure, and web-based user interaction using Spring Boot.

---

## Project Overview

In the game, the player controls a company called **TechCorp**. The company has employees, cash, reputation, and a portfolio of projects. Each project requires a certain amount of work to be completed and may generate revenue after completion.

The player must make strategic decisions such as:

- which project to start,
- where to assign the team,
- when to process the next turn,
- how to balance project progress with company costs.

The game ends when the company goes bankrupt, all projects are completed, or the maximum number of turns is reached.

---

## Main Goal

The main objective of the game is to reach the required **target company value** before the end of the game.

The company value is calculated based on:

```text
cash + reputation * 1000 + finishedProjects * 5000
```

---

## Technologies Used

* Java 17
* Spring Boot
* Maven
* HTML
* JavaScript
* GitHub Codespaces

---

## Features

* Turn-based game system
* Web-based user interface
* Project management mechanics
* Employee assignment system
* Company cash and salary system
* Project rewards and start costs
* Random business events
* Win and lose conditions
* Object-oriented architecture
* REST endpoints for game actions

## Game Mechanics

### Company

The Company class represents the player-controlled organization.
It stores the main state of the game, including:

* company name,
* available cash,
* reputation,
* employees,
* projects.

The company can start projects, pay salaries, collect revenue, and calculate its total value.

### Employees

Employees are represented using an abstract Employee class.
The project includes several employee types:

* Developer
* Tester
* Manager

Each employee type has a different implementation of the work() method.
This demonstrates inheritance and polymorphism.

Example:

public class Developer extends Employee {

    @Override
    public int work() {
        return getSkill() * 2;
    }
}

### Projects

Each project has:

* a name,
* required work,
* current progress,
* reward,
* start cost,
* status,
* assigned team.

Projects can have different statuses, such as:

PLANNED,
IN_PROGRESS,
ON_HOLD,
FINISHED,
CANCELLED

A project receives progress only when it is in progress and has employees assigned to it.

### Turns

The game is turn-based.
When the player processes the next turn, the following actions are performed:

1. Active projects receive progress.
2. Employee salaries are paid.
3. Revenue from completed projects is collected.
4. A random event may occur.
5. The game checks win or lose conditions.
6. The turn number increases if the game is still in progress.

### Random Events

The game includes random events that can affect the company.
Examples include:

* market slowdown,
* productivity boost,
* client bonus.

Events are implemented using the GameEvent interface.

---

## Project Structure

```text
src/main/java/com/university/techcorp
│
├── api
│   ├── GameController.java
│   └── HealthController.java
│
├── domain
│   ├── Company.java
│   ├── Employee.java
│   ├── Developer.java
│   ├── Tester.java
│   ├── Manager.java
│   ├── Project.java
│   └── ProjectStatus.java
│
├── engine
│   ├── GameEngine.java
│   └── GameResult.java
│
├── events
│   ├── GameEvent.java
│   ├── MarketSlowdownEvent.java
│   ├── ProductivityBoostEvent.java
│   └── ClientBonusEvent.java
│
└── Main.java
```

Static frontend files are located in:

```text
src/main/resources/static
```

The main web interface is:

```text
index.html
```

---

## Architecture

The project follows a simple layered structure:

### Domain Layer

Contains the main business and game objects:

* Company
* Project
* Employee
* employee subclasses
* project statuses

### Engine Layer

Contains the main game logic:

* turn processing,
* win and lose conditions,
* random event handling,
* game state control.

### API Layer

Contains Spring Boot controllers that expose game actions as HTTP endpoints.

### Frontend Layer

Contains the HTML and JavaScript interface used by the player in the browser.

This separation keeps the code easier to understand, maintain, and extend.

## API Endpoints

### Health Check

GET /health

Returns a simple message confirming that the application is running.

### Game State

GET /game/state

Returns the current state of the game.

### Start Project

GET /game/start-project/{projectIndex}

Starts a selected project.

### Assign Employees

GET /game/assign-employees/{projectIndex}

Assigns employees to the selected project.

### Next Turn

GET /game/next-turn

Processes the next full turn.

### Reset Game

GET /game/reset

Restarts the game from the initial state.

---

## Educational Purpose

This project demonstrates several important Java and software design concepts:

* classes and objects,
* encapsulation,
* inheritance,
* polymorphism,
* abstract classes,
* enums,
* interfaces,
* exception handling,
* input validation,
* Maven project structure,
* Spring Boot web development,
* separation of responsibilities.

---

## Possible Future Improvements

The project can be extended with additional features, such as:

* assigning individual employees to specific projects,
* hiring new employees during the game,
* more random events,
* difficulty levels,
* saving and loading game state,
* improved scoring system,
* AI-controlled competitor company,
* more advanced project lifecycle management.

---

## Author

Created by Wiktor Papis as a Java decision-making game project.

---