package com.university.techcorp.events;

import com.university.techcorp.domain.Company;

public interface GameEvent {

    void apply(Company company);

    String getName();

    String getDescription();
}