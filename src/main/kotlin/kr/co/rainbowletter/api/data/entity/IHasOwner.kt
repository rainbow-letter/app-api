package kr.co.rainbowletter.api.data.entity

interface IHasOwner {
    val id: Long?
    val user: UserEntity?
}

