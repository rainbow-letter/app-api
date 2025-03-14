package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.exception.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import kotlin.reflect.jvm.jvmName

class RepositoryExtension {
    companion object {
        inline fun <reified TEntity, TId : Any> JpaRepository<TEntity, TId>.findByIdOrThrow(id: TId): TEntity =
            this.findById(id).orElseThrow {
                throw EntityNotFoundException(TEntity::class.jvmName, id)
            }
    }
}