package kr.co.rainbowletter.api.letter.service

import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.Chat
import io.github.sashirestela.openai.domain.chat.ChatMessage.*
import io.github.sashirestela.openai.domain.chat.ChatRequest
import kr.co.rainbowletter.api.data.entity.PromptEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

interface IAIFactory {
    fun run(
        prompt: PromptEntity,
        variables: Map<String, String>
    ): Chat
}

@Service
class GptFactory(
    @Value("\${gpt.key}") private val apiKey: String,
    @Value("\${gpt.model}") private val model: String,
) : IAIFactory {
    private val client by lazy {
        SimpleOpenAI.builder()
            .apiKey(apiKey)
            .build()
    }

    override fun run(
        prompt: PromptEntity,
        variables: Map<String, String>
    ): Chat {
        val requestBuilder = ChatRequest.builder().model(model)
        prompt.messages?.forEach { o ->
            val content = replace(o.content!!, variables)
            requestBuilder.message(
                when (o.role) {
                    0.toByte() -> SystemMessage.of(content)
                    1.toByte() -> UserMessage.of(content)
                    2.toByte() -> AssistantMessage.of(content)
                    else -> throw IllegalArgumentException("Unexpected role: ${o.role}")
                }
            )
        } ?: throw RuntimeException("prompt messages not found")

        val request = requestBuilder
            .temperature(0.0)
            .maxCompletionTokens(300)
            .build()

        return client.chatCompletions().create(request).join()
    }

    private fun replace(
        content: String,
        variables: Map<String, String>
    ): String {
        var result = content
        variables.forEach { (k, v) ->
            result = result.replace("{{$k}}", v)
        }
        return result
    }
}