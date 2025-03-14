package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(
    name = "pet", schema = "rainbowletter", uniqueConstraints = [
        UniqueConstraint(name = "UK_favorite_id", columnNames = ["favorite_id"])
    ]
)
open class PetEntity : IHasOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    override var id: Long? = null

    @Column(name = "death_anniversary")
    open var deathAnniversary: LocalDate? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_id", nullable = false)
    open var favorite: FavoriteEntity? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    override val user: UserEntity? = null

    @Size(max = 255)
    @Column(name = "image")
    open var image: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    open var name: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "owner", nullable = false)
    open var owner: String? = null

    @Size(max = 255)
    @Column(name = "personalities")
    open var personalities: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "species", nullable = false)
    open var species: String? = null
}