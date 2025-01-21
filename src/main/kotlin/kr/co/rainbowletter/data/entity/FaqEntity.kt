package kr.co.rainbowletter.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "faq", schema = "rainbowletter")
open class FaqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "visibility", nullable = false)
    open var visibility: Boolean? = false

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "sequence")
    open var sequence: Long? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "summary", nullable = false, length = 30)
    open var summary: String? = null

    @NotNull
    @Lob
    @Column(name = "detail", nullable = false)
    open var detail: String? = null
}