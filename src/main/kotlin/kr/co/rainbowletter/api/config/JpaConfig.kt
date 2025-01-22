package kr.co.rainbowletter.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["kr.co.rainbowletter.data.repository"])
@EntityScan(basePackages = ["kr.co.rainbowletter.data.entity"])
@Configuration
class JpaConfig