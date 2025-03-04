package kr.co.rainbowletter.api.data.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.co.rainbowletter.api.data.entity.LetterEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LetterRepository : JpaRepository<LetterEntity, Long>, KotlinJdslJpqlExecutor