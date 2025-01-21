package kr.co.rainbowletter.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "log_event", schema = "rainbowletter")
open class LogEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @NotNull
    @Column(name = "resource", nullable = false)
    open var resource: Long? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Column(name = "user_id")
    open var userId: Long? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "category", nullable = false)
    open var category: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "event", nullable = false)
    open var event: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "message", nullable = false)
    open var message: String? = null

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null
}