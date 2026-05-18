package com.university.techcorp.events;

import com.university.techcorp.domain.Company;

public class ProductivityBoostEvent implements GameEvent {

    private int reputationGain;

    public ProductivityBoostEvent() {
        this.reputationGain = 5;
    }

    public ProductivityBoostEvent(int reputationGain) {
        this.reputationGain = reputationGain;
    }

    @Override
    public void apply(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        company.addReputation(reputationGain);
    }

    @Override
    public String getName() {
        return "Productivity Boost";
    }

    @Override
    public String getDescription() {
        return "The team worked very efficiently. Reputation increased by " + reputationGain + ".";
    }
}