package kr.co.rainbowletter.api.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(
    name = "reply", schema = "rainbowletter", indexes = [
        Index(name = "idx_letter_id", columnList = "letter_id")
    ]
)
open class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @NotNull
    @Column(name = "inspection", nullable = false)
    open var inspection: Boolean? = false

    @Column(name = "created_at")
    open var createdAt: LocalDateTime? = null

    @Column(name = "inspection_time")
    open var inspectionTime: LocalDateTime? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "letter_id", nullable = false)
    open var letter: LetterEntity? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    open var pet: PetEntity? = null

    @Column(name = "submit_time")
    open var submitTime: LocalDateTime? = null

    @Column(name = "updated_at")
    open var updatedAt: LocalDateTime? = null

    @Size(max = 20)
    @NotNull
    @Column(name = "summary", nullable = false, length = 20)
    open var summary: String? = null

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @NotNull
    @Column(name = "prompta", nullable = false)
    open var promptA: String? = null

    @NotNull
    @Column(name = "promptb", nullable = false, length = 4000)
    open var promptB: String? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prompt_type", nullable = false)
    open var promptType: PromptType? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    open var status: ReplyStatus? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "read_status", nullable = false)
    open var readStatus: ReplyReadStatus? = null
}