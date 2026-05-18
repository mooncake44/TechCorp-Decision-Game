package com.university.techcorp.events;

import com.university.techcorp.domain.Company;

public class MarketSlowdownEvent implements GameEvent {

    private double cashLoss;

    public MarketSlowdownEvent() {
        this.cashLoss = 5000;
    }

    public MarketSlowdownEvent(double cashLoss) {
        if (cashLoss < 0) {
            throw new IllegalArgumentException("Cash loss cannot be negative.");
        }

        this.cashLoss = cashLoss;
    }

    @Override
    public void apply(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        company.reduceCash(cashLoss);
    }

    @Override
    public String getName() {
        return "Market Slowdown";
    }

    @Override
    public String getDescription() {
        return "The market slowed down. The company lost " + cashLoss + " cash.";
    }
}