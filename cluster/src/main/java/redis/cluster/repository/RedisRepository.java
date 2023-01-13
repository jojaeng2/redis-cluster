package redis.cluster.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
  private final ValueOperations<String, Object> opsValueOperation;

  public void save(String key, String value) {
    System.out.println("key = " + key);
    System.out.println("value = " + value);
    opsValueOperation.set(key, value);
  }

  public String findValue(String key) {
    return (String) opsValueOperation.get(key);
  }
}
