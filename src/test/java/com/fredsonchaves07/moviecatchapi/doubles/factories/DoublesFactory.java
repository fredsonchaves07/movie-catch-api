package com.fredsonchaves07.moviecatchapi.doubles.factories;

import com.fredsonchaves07.moviecatchapi.doubles.InMemoryUserRepository;

public class DoublesFactory {

    public static InMemoryUserRepository getInMemoryUserRepository() {
        return new InMemoryUserRepository();
    }
}
