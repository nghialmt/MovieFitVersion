package com.example.fixproject.Models;

import java.util.List;

public class Trailer {
    private int id;
    private List<Result> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
