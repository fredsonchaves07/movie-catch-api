package com.fredsonchaves07.moviecatchapi.domain.entities.records;

import java.util.Objects;

public record MovieSeries(
        String id,
        String name,
        String urlImage
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieSeries that = (MovieSeries) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
