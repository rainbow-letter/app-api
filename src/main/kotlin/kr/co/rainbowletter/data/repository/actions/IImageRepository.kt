package kr.co.rainbowletter.data.repository.actions

import kr.co.rainbowletter.data.entity.has.HasImage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface IImageRepository<T : HasImage, V> : JpaRepository<T, V> {
    fun findByImageNotContains(imagePrefix: String, pageable: Pageable): Page<T>
}