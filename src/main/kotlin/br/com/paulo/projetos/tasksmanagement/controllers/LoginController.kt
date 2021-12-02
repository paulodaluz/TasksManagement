package br.com.paulo.projetos.tasksmanagement.controllers

import br.com.paulo.projetos.tasksmanagement.dtos.ErroDTO
import br.com.paulo.projetos.tasksmanagement.dtos.LoginDTO
import br.com.paulo.projetos.tasksmanagement.dtos.LoginRespostaDTO
import br.com.paulo.projetos.tasksmanagement.utils.JWTUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
@RequestMapping("api/login")
class LoginController {

    private val LOGIN_TESTE = "admin@admin.com"
    private val SENHA_TESTE = "Admin1234@"

    @PostMapping
    fun efetuarLogin(@RequestBody dto: LoginDTO) : ResponseEntity<Any> {
        try {
            if(dto == null || dto.login.isNullOrBlank() || dto.login.isNullOrEmpty()
                || dto.senha.isNullOrEmpty() || dto.senha.isNullOrBlank()
                || dto.login != LOGIN_TESTE || dto.senha != SENHA_TESTE
            ) {
                return ResponseEntity(ErroDTO(HttpStatus.BAD_REQUEST.value(),
                    "Parametros inválidos"), HttpStatus.BAD_REQUEST)
            }

            val userId = 1
            val tokenJwt = JWTUtils().gerarToken(userId.toString())

            val usuarioTeste = LoginRespostaDTO("Usuário de teste", LOGIN_TESTE, tokenJwt);
            return ResponseEntity(usuarioTeste, HttpStatus.OK)
        } catch(error: Exception) {
            return ResponseEntity(ErroDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Não foi possivel efetuar o login, tente novamente mais tarde"),
                HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}