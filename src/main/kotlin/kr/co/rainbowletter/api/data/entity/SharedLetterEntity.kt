package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kr.co.rainbowletter.api.data.entity.enums.RecipientType
import java.time.Instant

@Entity
@Table(
    name = "shared_letter", schema = "rainbowletter", indexes = [
        Index(name = "created_at__IDX", columnList = "created_at")
    ]
)
open class SharedLetterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "recipient_type", columnDefinition = "tinyint UNSIGNED not null")
    open var recipientType: RecipientType? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    open var pet: PetEntity? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: UserEntity? = null

    @Size(max = 56)
    @NotNull
    @Column(name = "content", nullable = false, length = 56)
    open var content: String? = null

    @NotNull
    @Lob
    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @NotNull
    @Lob
    @Column(name = "updated_at")
    open var updatedAt: Instant? = null
}