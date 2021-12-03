package br.com.paulo.projetos.tasksmanagement.filters

import br.com.paulo.projetos.tasksmanagement.authorization
import br.com.paulo.projetos.tasksmanagement.bearer
import br.com.paulo.projetos.tasksmanagement.impl.UserDetailImpl
import br.com.paulo.projetos.tasksmanagement.models.User
import br.com.paulo.projetos.tasksmanagement.utils.JWTUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorizerFilter(authenticationManager: AuthenticationManager, val jwtUtils: JWTUtils
    ) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader(authorization)

        if(authorizationHeader != null && authorizationHeader.startsWith(bearer)) {
            val authorized = getAuthentication(authorizationHeader)

            SecurityContextHolder.getContext().authentication = authorized
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(authorization: String): UsernamePasswordAuthenticationToken {
        val token = authorization.substring(7)

        if(JWTUtils().isValidToken(token)) {
            val idString = JWTUtils().getUsuarioId(token)

            if(!idString.isNullOrBlank() && !idString.isNullOrEmpty()){
                val user = User(idString.toLong(), "Usuário teste", "admin@admin.com", "Admin1234@")
                val userImpl = UserDetailImpl(user)
                return UsernamePasswordAuthenticationToken(userImpl, null, userImpl.authorities)
            }
        }

        throw UsernameNotFoundException("Token inválido!")
    }

}