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

## Technologies Used

* Java 17
* Spring Boot
* Maven
* HTML
* JavaScript
* GitHub Codespaces

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