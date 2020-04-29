package com.sudria.demo.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sudria.demo.domain.AnimalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Zoo Management System", description = "Operations availables for zoo management")
public class Controller {


  private AnimalService animalService;
  private ObjectMapper objectMapper;

  public Controller(AnimalService animalService,
      ObjectMapper objectMapper) {
    this.animalService = animalService;
    this.objectMapper = objectMapper;
  }

  @ApiOperation(value = "View a list of available animals", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @RequestMapping(value = "/animals", method = RequestMethod.GET)
  public ResponseEntity<List<AnimalDto>> getAnimals() {
    return new ResponseEntity<>(animalService.getAnimals(), HttpStatus.OK);
  }

  @RequestMapping(value = "/animals", method = RequestMethod.POST)
  public ResponseEntity<AnimalDto> createAnimals(
      @ApiParam(value = "Animal object store in database table", required = true)
      @RequestBody AnimalDto animalDto) {
    animalService.addAnimal(animalDto);
    return new ResponseEntity<AnimalDto>(animalDto, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PUT)
  public ResponseEntity<AnimalDto> replaceAnimals(
      @PathVariable(value = "id") Long id,
      @RequestBody AnimalDto animalDto) {
    animalDto.setId(id);
    animalService.replaceAnimal(animalDto);
    return new ResponseEntity<AnimalDto>(animalDto, HttpStatus.OK);
  }


  @RequestMapping(value = "/animals/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<AnimalDto> deleteAnimals(@PathVariable(value = "id") Long id) {
    animalService.deleteAnimals(id);
    return new ResponseEntity<AnimalDto>(HttpStatus.OK);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PATCH, consumes = "application/json-patch+json")
  public ResponseEntity<AnimalDto> patchAnimals(@PathVariable(value = "id") Long id,
      @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
    AnimalDto animalDto = null;
    try {
      animalDto = animalService.findAnimal(id);
    } catch (NotFoundException e) {
      return new ResponseEntity<AnimalDto>(animalDto, HttpStatus.NOT_FOUND);
    }
    AnimalDto animalDtoPatched = applyPatchToCustomer(patch, animalDto);
    animalService.patchAnimals(animalDtoPatched);
    return new ResponseEntity<AnimalDto>(HttpStatus.OK);
  }

  private AnimalDto applyPatchToCustomer(JsonPatch patch, AnimalDto targetAnimal)
      throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(objectMapper.convertValue(targetAnimal, JsonNode.class));
    return objectMapper.treeToValue(patched, AnimalDto.class);
  }
}
