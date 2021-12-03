package br.com.paulo.projetos.tasksmanagement.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JWTUtils {

    private val securityKey = "SecretSecurityKey"

    fun gerarToken(userId: String) : String {
        return Jwts.builder()
            .setSubject(userId)
            .signWith(SignatureAlgorithm.HS512, securityKey.toByteArray())
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaimsToken(token)

        if(claims != null) {
            val idUsuario = claims.subject
            if(!idUsuario.isNullOrBlank() && !idUsuario.isNullOrEmpty()) {
                return true
            }
        }

        return false
    }

    private fun getClaimsToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(securityKey.toByteArray()).parseClaimsJws(token).body
        } catch (exception: Exception) {
            null
        }
    }

    fun getUsuarioId(token: String): String? {
        val claims = getClaimsToken(token)

        return claims?.subject
    }

}