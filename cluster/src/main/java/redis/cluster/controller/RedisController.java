package redis.cluster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import redis.cluster.repository.RedisRepository;

@RequiredArgsConstructor
@Controller
public class RedisController {

  private final RedisRepository repository;

  @GetMapping("/api/{key}/")
  public ResponseEntity getValue(@PathVariable("key") String key) {
    return new ResponseEntity<>(repository.findValue(key), HttpStatus.OK);
  }

  @PostMapping("/api/{key}/{value}")
  public ResponseEntity saveValue(@PathVariable("key") String key, @PathVariable("value") String value) {
    repository.save(key, value);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
