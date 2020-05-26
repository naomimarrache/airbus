package com.sudria.demo.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sudria.demo.domain.Avion;
import com.sudria.demo.domain.AvionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class Controller {

  private AvionService avionService;
  private ObjectMapper objectMapper;

  public Controller(AvionService avionService, ObjectMapper objectMapper) {
    this.avionService = avionService;
    this.objectMapper = objectMapper;
  }


  @ApiOperation(value = "View a list of available avions", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @RequestMapping(value = "/avions", method = RequestMethod.GET)
  public ResponseEntity<List<Avion>> getAvions() {
    return new ResponseEntity<>(avionService.getAvions(), HttpStatus.OK);
  }

  @RequestMapping(value = "/avions/{id}", method = RequestMethod.GET)
  public ResponseEntity<Avion> getAvionsById( @PathVariable(value = "id") Long id) {
    try {
      return new ResponseEntity<>(avionService.getAvions(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Avion Not Found", e);
    }
  }

  @RequestMapping(value = "/avions", method = RequestMethod.POST)
  public ResponseEntity<Avion> createAvions(
      @ApiParam(value = "Avion object store in database table", required = true)
      @RequestBody Avion avion) {
    avionService.addAvion(avion);
    return new ResponseEntity<>(avion, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/avions/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Avion> replaceAvions(
      @PathVariable(value = "id") Long id,
      @RequestBody Avion avion) {
    avion.setId(id);
    avionService.replaceAvion(avion);
    return new ResponseEntity<>(avion, HttpStatus.OK);
  }

  @RequestMapping(value = "/avions/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Avion> deleteAvions(@PathVariable(value = "id") Long id) {
    avionService.deleteAvions(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/avions/{id}", method = RequestMethod.PATCH, consumes = "application/json-patch+json")
  public ResponseEntity<String> patchAvions(
      @PathVariable(value = "id") Long id,
      @RequestBody JsonPatch patch)  {
    try {
      avionService.patchAvions(applyPatchToCustomer(patch, avionService.findAvion(id)));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (JsonPatchException | JsonProcessingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private Avion applyPatchToCustomer(JsonPatch patch, Avion targetAvion)
      throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(objectMapper.convertValue(targetAvion, JsonNode.class));
    return objectMapper.treeToValue(patched, Avion.class);
  }
}
