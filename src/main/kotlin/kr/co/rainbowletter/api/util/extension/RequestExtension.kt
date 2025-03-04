package kr.co.rainbowletter.api.util.extension

import jakarta.servlet.http.HttpServletRequest

fun HttpServletRequest.getUrlWithoutQuery(): String {
    return "${this.scheme}://${this.serverName}" +
            if (this.serverPort == 80 || this.serverPort == 443) "" else ":${this.serverPort}" +
                    this.requestURI
}

fun Any.toQueryString(): String {
    return this::class.members
        .filterIsInstance<kotlin.reflect.KProperty1<Any, *>>() // 프로퍼티만 필터링
        .mapNotNull { prop ->
            val value = prop.get(this)
            if (value != null) "${prop.name}=$value" else null
        }
        .joinToString("&")
}
