package com.sudria.demo.application.graphql;

import com.sudria.demo.domain.avion.Avion;
import com.sudria.demo.domain.avion.AvionService;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvionResolver {
    private AvionService avionService;

    public AvionResolver(AvionService avionService) {
        this.avionService = avionService;
    }

    @GraphQLQuery
    public List<Avion> getAvions () {
        return avionService.getAvions();
    }


}
