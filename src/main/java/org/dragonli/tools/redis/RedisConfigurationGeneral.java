package org.dragonli.tools.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis configuration used for ui.
 * 
 * @author liwenyu
 */
@Component("ServiceRedisConfigurationGeneral")
@ConditionalOnProperty(value = "service.general.redis-open")
@ConfigurationProperties("service.general.redis-config")
public class RedisConfigurationGeneral extends RedisConfiguration {

}
