package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "notification", schema = "rainbowletter")
open class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "code", nullable = false)
    open var code: Int? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "receiver", nullable = false, length = 50)
    open var receiver: String? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "sender", nullable = false, length = 50)
    open var sender: String? = null

    @Size(max = 100)
    @NotNull
    @Column(name = "title", nullable = false, length = 100)
    open var title: String? = null

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "status_message", nullable = false)
    open var statusMessage: String? = null

    @Lob
    @Column(name = "type")
    open var type: String? = null
}