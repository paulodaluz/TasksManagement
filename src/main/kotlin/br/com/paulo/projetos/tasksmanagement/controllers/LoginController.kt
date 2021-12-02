package br.com.paulo.projetos.tasksmanagement.controllers

import br.com.paulo.projetos.tasksmanagement.dtos.ErroDTO
import br.com.paulo.projetos.tasksmanagement.dtos.LoginDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.lang.RuntimeException

@RestController
@RequestMapping("api/login")
class LoginController {

    @PostMapping
    fun efetuarLogin(@RequestBody dto: LoginDTO) : ResponseEntity<Any> {
        try {
            if(dto == null || dto.login.isNullOrBlank() || dto.login.isNullOrEmpty()
                || dto.senha.isNullOrEmpty() || dto.senha.isNullOrBlank()) {
                return ResponseEntity(ErroDTO(HttpStatus.BAD_REQUEST.value(),
                    "Parametros inválidos"), HttpStatus.BAD_REQUEST)
            }

            return ResponseEntity("Login autenticado com sucesso", HttpStatus.OK)
        } catch(error: Exception) {
            return ResponseEntity(ErroDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Não foi possivel efetuar o login, tente novamente mais tarde"),
                HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}