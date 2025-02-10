package com.ndyaya.framework.idempotent.config;

import com.ndyaya.framework.idempotent.core.aop.IdempotentAspect;
import com.ndyaya.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.ndyaya.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.ndyaya.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.ndyaya.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import com.ndyaya.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import com.ndyaya.framework.redis.config.NdyayaRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = NdyayaRedisAutoConfiguration.class)
public class NdyayaIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
