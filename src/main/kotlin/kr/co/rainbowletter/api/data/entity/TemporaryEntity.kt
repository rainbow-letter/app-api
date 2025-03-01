package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "temporary", schema = "rainbowletter")
open class TemporaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @NotNull
    @Column(name = "pet_id", nullable = false)
    open var petId: Long? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @NotNull
    @Column(name = "user_id", nullable = false)
    open var userId: Long? = null

    @Size(max = 36)
    @NotNull
    @Column(name = "session_id", nullable = false, length = 36)
    open var sessionId: String? = null

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null
}