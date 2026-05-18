package com.university.techcorp.events;

import com.university.techcorp.domain.Company;

public class ClientBonusEvent implements GameEvent {

    private double bonus;

    public ClientBonusEvent() {
        this.bonus = 3000;
    }

    public ClientBonusEvent(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative.");
        }

        this.bonus = bonus;
    }

    @Override
    public void apply(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        company.addCash(bonus);
    }

    @Override
    public String getName() {
        return "Client Bonus";
    }

    @Override
    public String getDescription() {
        return "A satisfied client paid an extra bonus of " + bonus + ".";
    }
}