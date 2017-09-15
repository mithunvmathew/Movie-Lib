package tech.joes.repositories;

import org.springframework.data.repository.CrudRepository;

import tech.joes.models.Movie;

import java.util.List;

/**
 * Created by joe on 05/04/2017.
 */
public interface MovieRepository extends CrudRepository<Movie,Integer> {
    List<Movie> findMoviesByReleaseYear(Integer year);
}
