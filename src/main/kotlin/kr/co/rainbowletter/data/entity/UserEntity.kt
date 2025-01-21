package kr.co.rainbowletter.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(
    name = "user", schema = "rainbowletter", uniqueConstraints = [
        UniqueConstraint(name = "UK_email", columnNames = ["email"])
    ]
)
open class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @NotNull
    @Column(name = "last_changed_password", nullable = false)
    open var lastChangedPassword: Instant? = null

    @Column(name = "last_logged_in")
    open var lastLoggedIn: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 20)
    @Column(name = "phone_number", length = 20)
    open var phoneNumber: String? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    open var email: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    open var password: String? = null

    @NotNull
    @Lob
    @Column(name = "provider", nullable = false)
    open var provider: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "provider_id", nullable = false)
    open var providerId: String? = null

    @NotNull
    @Lob
    @Column(name = "role", nullable = false)
    open var role: String? = null

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    open var status: String? = null
}