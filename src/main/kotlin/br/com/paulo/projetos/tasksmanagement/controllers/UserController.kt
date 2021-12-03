package br.com.paulo.projetos.tasksmanagement.controllers

import br.com.paulo.projetos.tasksmanagement.models.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/usuario")
class UserController {

    @GetMapping
    fun getUser() = User(1, "Usuário teste", "admin@admin.com", "")
}