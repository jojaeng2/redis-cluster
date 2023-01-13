package redis.cluster.config;

import io.lettuce.core.ReadFrom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  /**
   * 어플리케이션에서 사용할 redisTemplate 설정
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    return redisTemplate;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory(){
    LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
        .readFrom(ReadFrom.REPLICA_PREFERRED) // 복제본 노드에서 읽지 만 사용할 수없는 경우 마스터에서 읽습니다.
        .build();
    // 모든 클러스터(master, slave) 정보를 적는다. (해당 서버중 접속되는 서버에서 cluster nodes 명령어를 통해 모든 클러스터 정보를 읽어오기에 다운 됐을 경우를 대비하여 모든 노드 정보를 적어두는편이 좋다.)
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration()
        .clusterNode("localhost", 6300)
        .clusterNode("localhost", 6301)
        .clusterNode("localhost", 6302)
        .clusterNode("localhost", 6400)
        .clusterNode("localhost", 6401)
        .clusterNode("localhost", 6402);
    return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
  }

  @Bean
  public ValueOperations<String, Object> opsValueOperation(RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForValue();
  }
}
