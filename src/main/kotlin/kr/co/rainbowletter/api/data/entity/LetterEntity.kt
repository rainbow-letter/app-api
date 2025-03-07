package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(
    name = "letter", schema = "rainbowletter", indexes = [
        Index(name = "idx_user_id", columnList = "user_id")
    ]
)
open class LetterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "number", nullable = false)
    open var number: Int? = null

    @Column(name = "created_at")
    open var createdAt: LocalDateTime? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    open val pet: PetEntity? = null

    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open val user: UserEntity? = null

    @NotNull
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    open val reply: List<ReplyEntity>? = null

    @Size(max = 20)
    @NotNull
    @Column(name = "summary", nullable = false, length = 20)
    open var summary: String? = null

    @Size(max = 36)
    @NotNull
    @Column(name = "share_link", nullable = false, length = 36)
    open var shareLink: String? = null

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Size(max = 255)
    @Column(name = "image")
    open var image: String? = null

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null
}