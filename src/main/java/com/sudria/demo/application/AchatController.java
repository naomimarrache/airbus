package com.sudria.demo.application;

import com.sudria.demo.domain.achat.Achat;
import com.sudria.demo.domain.achat.AchatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AchatController {

  private AchatService achatService;

  public AchatController(AchatService achatService) {
    this.achatService = achatService;
  }

  @RequestMapping(value = "/avions/{id}/achats", method = RequestMethod.GET)
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity<List<Achat>> getAchats(@PathVariable(value = "id") Long avionId) {
    return new ResponseEntity<List<Achat>>(achatService.findAchatsByAvionId(avionId), HttpStatus.OK);
  }

  @RequestMapping(value = "/avions/{id}/achats", method = RequestMethod.POST)
  public ResponseEntity<Achat> createAchats(
      @PathVariable(value = "id") Long avionId,
      @RequestBody Achat achat)  {
    achat = achatService.addAchats(avionId, achat);
    return new ResponseEntity<>(achat, HttpStatus.CREATED);
  }
}
