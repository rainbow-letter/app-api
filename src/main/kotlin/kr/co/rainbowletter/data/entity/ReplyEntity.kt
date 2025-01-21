package kr.co.rainbowletter.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import java.time.Instant

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
    open var createdAt: Instant? = null

    @Column(name = "inspection_time")
    open var inspectionTime: Instant? = null

    @NotNull
    @Column(name = "letter_id", nullable = false)
    open var letterId: Long? = null

    @NotNull
    @Column(name = "pet_id", nullable = false)
    open var petId: Long? = null

    @Column(name = "submit_time")
    open var submitTime: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 20)
    @NotNull
    @Column(name = "summary", nullable = false, length = 20)
    open var summary: String? = null

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    open var content: String? = null

    @NotNull
    @Lob
    @Column(name = "prompta", nullable = false)
    open var prompta: String? = null

    @NotNull
    @Lob
    @Column(name = "prompt_type", nullable = false)
    open var promptType: String? = null

    @NotNull
    @Lob
    @Column(name = "read_status", nullable = false)
    open var readStatus: String? = null

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null

    @Size(max = 4000)
    @NotNull
    @ColumnDefault("''")
    @Column(name = "promptb", nullable = false, length = 4000)
    open var promptb: String? = null
}