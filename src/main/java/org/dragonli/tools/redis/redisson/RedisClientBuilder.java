package org.dragonli.tools.redis.redisson;

import org.dragonli.tools.redis.RedisConfiguration;
import org.dragonli.tools.redis.RedisConfigurationGeneral;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedisClientBuilder {

	public RedissonClient buildRedissionClient(RedisConfiguration rc) {
		Config config = new Config().setCodec(StringCodec.INSTANCE);
		if ("single".equals(rc.getMode())) {
			config.useSingleServer().setAddress(rc.getNodes().get(0));
			return Redisson.create(config);
		} else if ("cluster".equals(rc.getMode())) {
			ClusterServersConfig csc = config.useClusterServers();
			rc.getNodes().forEach(node -> {
				csc.addNodeAddress(node);
			});
			return Redisson.create(config);
		}
		throw new IllegalArgumentException("Invalid mode: " + rc.getMode());
	}

	@Bean
	@ConditionalOnProperty(value = "service.general.redis-open")
	public RedissonClient createRedisClient(@Autowired RedisConfigurationGeneral rc) {
		return buildRedissionClient(rc);
	}
}
