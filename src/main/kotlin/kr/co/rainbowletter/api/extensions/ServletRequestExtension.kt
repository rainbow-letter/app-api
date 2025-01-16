package kr.co.rainbowletter.api.extensions

import jakarta.servlet.http.HttpServletRequest

class ServletRequestExtension {
    companion object {
        fun HttpServletRequest.getBearerToken(): String? {
            val bearerToken = this.getHeader("Authorization")
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7)
            }
            return null
        }
    }
}
