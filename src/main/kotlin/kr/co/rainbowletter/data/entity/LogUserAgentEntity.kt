package kr.co.rainbowletter.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "log_user_agent", schema = "rainbowletter")
open class LogUserAgentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

    @Size(max = 255)
    @Column(name = "agent")
    open var agent: String? = null

    @Size(max = 255)
    @Column(name = "agent_name")
    open var agentName: String? = null

    @Size(max = 255)
    @Column(name = "agent_version")
    open var agentVersion: String? = null

    @Size(max = 255)
    @Column(name = "device")
    open var device: String? = null

    @Size(max = 255)
    @Column(name = "device_name")
    open var deviceName: String? = null

    @Size(max = 255)
    @Column(name = "event")
    open var event: String? = null

    @Size(max = 255)
    @Column(name = "os")
    open var os: String? = null

    @Size(max = 255)
    @Column(name = "os_name")
    open var osName: String? = null

    @Size(max = 255)
    @Column(name = "os_version")
    open var osVersion: String? = null
}