package kr.co.rainbowletter.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "slackClient", url = "\${slack.webhook.url}")
interface SlackFeignClient {

    @PostMapping
    fun sendSlackMessage(@RequestBody payload: Map<String, String>)
}