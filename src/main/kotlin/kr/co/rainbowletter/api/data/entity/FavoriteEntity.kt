package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Entity
@Table(name = "favorite", schema = "rainbowletter")
open class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "can_increase", nullable = false)
    open var canIncrease: Boolean? = false

    @NotNull
    @Column(name = "day_increase_count", nullable = false)
    open var dayIncreaseCount: Int? = null

    @NotNull
    @Column(name = "total", nullable = false)
    open var total: Int? = null

    @NotNull
    @Column(name = "last_increased_at", nullable = false)
    open var lastIncreasedAt: Instant? = null
}