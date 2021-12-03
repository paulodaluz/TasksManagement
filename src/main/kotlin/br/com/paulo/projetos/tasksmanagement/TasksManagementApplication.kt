package br.com.paulo.projetos.tasksmanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class TasksManagementApplication

fun main(args: Array<String>) {
	runApplication<TasksManagementApplication>(*args)
}
