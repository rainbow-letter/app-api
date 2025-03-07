package kr.co.rainbowletter.api.auth

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RequestLogFilter : Filter {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun doFilter(
        req: ServletRequest,
        res: ServletResponse,
        chain: FilterChain,
    ) {
        val httpRequest = req as HttpServletRequest

        logger.info("Request: method=${httpRequest.method}, URI=${httpRequest.requestURI}, query=${httpRequest.queryString}")
        logger.info("Headers: ${httpRequest.headerNames.toList().joinToString { "$it=${httpRequest.getHeader(it)}" }}")
        if (isJsonRequest(httpRequest)) {
            logger.info("Body: ${httpRequest.inputStream.readAllBytes()}")
        }

        chain.doFilter(req, res)

        logger.info("Response URI:${httpRequest.requestURI}")
    }

    private fun isJsonRequest(request: HttpServletRequest): Boolean {
        val contentType = request.contentType
        return contentType != null && contentType.contains("application/json", ignoreCase = true)
    }

}