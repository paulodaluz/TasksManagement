package br.com.paulo.projetos.tasksmanagement.utils

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
}