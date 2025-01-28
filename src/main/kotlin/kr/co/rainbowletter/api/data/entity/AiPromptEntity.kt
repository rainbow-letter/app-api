package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "ai_prompt", schema = "rainbowletter")
open class AiPromptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "config_id", nullable = false)
    open var configId: Long? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "model", nullable = false)
    open var model: String? = null

    @Size(max = 255)
    @Column(name = "parameters")
    open var parameters: String? = null

    @NotNull
    @Lob
    @Column(name = "system", nullable = false)
    open var system: String? = null

    @NotNull
    @Lob
    @Column(name = "user", nullable = false)
    open var user: String? = null

    @NotNull
    @Lob
    @Column(name = "provider", nullable = false)
    open var provider: String? = null

    @NotNull
    @Lob
    @Column(name = "type", nullable = false)
    open var type: String? = null
}