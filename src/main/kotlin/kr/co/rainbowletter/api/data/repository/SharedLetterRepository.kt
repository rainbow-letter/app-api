package kr.co.rainbowletter.api.data.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SharedLetterRepository : JpaRepository<SharedLetterEntity, Long>, KotlinJdslJpqlExecutor