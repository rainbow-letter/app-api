package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "prompt", schema = "rainbowletter_dev")
open class PromptEntity {
    @Id
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    open var name: String? = null

    @NotNull
    @Column(name = "is_default", nullable = false)
    open var isDefault: Boolean? = null

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "prompt_id")
    open val messages: List<PromptMessageEntity>? = null

    @CreatedDate
    @Column(name = "created_at")
    open var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime? = null
}