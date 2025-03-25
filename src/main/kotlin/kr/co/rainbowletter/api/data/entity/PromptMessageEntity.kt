package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "prompt_message", schema = "rainbowletter_dev")
open class PromptMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prompt_id", nullable = false)
    open var prompt: PromptEntity? = null

    @NotNull
    @Column(name = "role", nullable = false)
    open var role: Byte? = null

    @Size(max = 1000)
    @Column(name = "content", length = 1000)
    open var content: String? = null

    @CreatedDate
    @Column(name = "created_at")
    open var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime? = null
}