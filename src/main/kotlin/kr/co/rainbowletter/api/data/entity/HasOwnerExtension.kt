package kr.co.rainbowletter.api.data.entity

import kr.co.rainbowletter.api.exception.EntityNotFoundException
import kotlin.reflect.jvm.jvmName

class HasOwnerExtension {
    companion object {
        fun <T : IHasOwner> T.throwIfDenied(userId: Long): T {
            this.user?.id == userId || throw EntityNotFoundException(LetterEntity::class.jvmName, this.id!!)
            return this
        }

        fun <T : IHasOwner> T.throwIfDenied(user: UserEntity) = this.throwIfDenied(user.id!!)
    }
}