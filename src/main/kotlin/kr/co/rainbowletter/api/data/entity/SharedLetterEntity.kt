package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kr.co.rainbowletter.api.data.entity.enums.RecipientType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(
    name = "shared_letter", schema = "rainbowletter", indexes = [
        Index(name = "created_at__IDX", columnList = "created_at")
    ]
)
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "UPDATE shared_letter SET deleted_at = current_timestamp WHERE id = ?")
@EntityListeners(AuditingEntityListener::class)
open class SharedLetterEntity : IHasOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    override var id: Long? = null

    @Column(name = "recipient_type", columnDefinition = "tinyint UNSIGNED not null")
    open var recipientType: RecipientType? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    open var pet: PetEntity? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    override var user: UserEntity? = null

    @Size(max = 56)
    @NotNull
    @Column(name = "content", nullable = false, length = 56)
    open var content: String? = null

    @CreatedDate
    @Column(name = "created_at")
    open var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    open var deletedAt: LocalDateTime? = null
}