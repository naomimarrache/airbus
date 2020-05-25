package com.sudria.demo.application.graphql;

import com.sudria.demo.domain.Animal;
import com.sudria.demo.domain.AnimalService;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimalResolver {
    private AnimalService animalService;

    public AnimalResolver(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GraphQLQuery
    public List<Animal> getAnimals () {
        return animalService.getAnimals();
    }


}
