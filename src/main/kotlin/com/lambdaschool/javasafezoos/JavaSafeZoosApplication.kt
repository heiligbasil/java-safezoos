package com.lambdaschool.javasafezoos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class JavaSafeZoosApplication

fun main(args: Array<String>)
{
    runApplication<JavaSafeZoosApplication>(*args)
}
