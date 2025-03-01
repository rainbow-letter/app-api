package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "ai_prompt_option", schema = "rainbowletter")
open class AiPromptOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "frequency_penalty", nullable = false)
    open var frequencyPenalty: Double? = null

    @NotNull
    @Column(name = "max_tokens", nullable = false)
    open var maxTokens: Int? = null

    @NotNull
    @Column(name = "presence_penalty", nullable = false)
    open var presencePenalty: Double? = null

    @NotNull
    @Column(name = "temperature", nullable = false)
    open var temperature: Double? = null

    @NotNull
    @Column(name = "topp", nullable = false)
    open var topp: Double? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @NotNull
    @Column(name = "prompt_id", nullable = false)
    open var promptId: Long? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 255)
    @Column(name = "stop")
    open var stop: String? = null
}