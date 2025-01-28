package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Entity
@Table(name = "ai_config", schema = "rainbowletter")
open class AiConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "useabtest", nullable = false)
    open var useabtest: Boolean? = false

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @NotNull
    @Lob
    @Column(name = "select_prompt", nullable = false)
    open var selectPrompt: String? = null
}