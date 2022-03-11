package br.com.paulo.projetos.tasksmanagement.controllers

import br.com.paulo.projetos.tasksmanagement.dtos.ErroDTO
import br.com.paulo.projetos.tasksmanagement.models.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/usuario")
class UserController {

    @PostMapping
    fun createUser(@RequestBody usuario: User): ResponseEntity<Any> {
        try {
            val erros = mutableListOf<String>()

            if(usuario == null) {
                return ResponseEntity(ErroDTO(HttpStatus.BAD_REQUEST.value(), "Parâmetros de entrada não enviados"), HttpStatus.BAD_REQUEST)
            }

            if(usuario.name.isNullOrEmpty() || usuario.name.isNullOrBlank()
                || usuario.name.length < 2) {
                erros.add("Nome inválido")
            }

            if(usuario.email.isNullOrEmpty() || usuario.email.isNullOrBlank()
                || usuario.email.length < 5) {
                erros.add("Email inválido")
            }

            if(usuario.password.isNullOrEmpty() || usuario.password.isNullOrBlank()
                || usuario.password.length < 4) {
                erros.add("Senha inválida")
            }

            if(erros.size > 0) {
                return ResponseEntity(ErroDTO(HttpStatus.BAD_REQUEST.value(), null, erros), HttpStatus.BAD_REQUEST)
            }

            return ResponseEntity(usuario, HttpStatus.OK)
        } catch(error: Exception) {
                return ResponseEntity(
                    ErroDTO(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Não foi possivel cadastrar o usuário, tente novamente mais tarde"),
                        HttpStatus.INTERNAL_SERVER_ERROR
                    )
            }
    }
}