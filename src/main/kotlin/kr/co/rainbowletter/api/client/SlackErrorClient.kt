package kr.co.rainbowletter.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "slackErrorClient", url = "\${slack.webhook.url.error}")
interface SlackErrorClient {

    @PostMapping
    fun sendSlackMessage(@RequestBody payload: Map<String, String>)
}