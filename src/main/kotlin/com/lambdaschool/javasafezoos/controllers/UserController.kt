package com.lambdaschool.javasafezoos.controllers

import com.lambdaschool.javasafezoos.models.User
import com.lambdaschool.javasafezoos.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URISyntaxException
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController
{

    @Autowired
    private val userService: UserService? = null

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = ["/users"], produces = ["application/json"])
    fun listAllUsers(): ResponseEntity<*>
    {
        val myUsers = userService!!.findAll()
        return ResponseEntity<List<User>>(myUsers, HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = ["/user/{userId}"], produces = ["application/json"])
    fun getUser(@PathVariable userId: Long): ResponseEntity<*>
    {
        val u = userService!!.findUserById(userId)
        return ResponseEntity<Any>(u, HttpStatus.OK)
    }


    @GetMapping(value = ["/getusername"], produces = ["application/json"])
    @ResponseBody
    fun getCurrentUserName(authentication: Authentication): ResponseEntity<*>
    {
        return ResponseEntity(authentication.getPrincipal(), HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = ["/user"], consumes = ["application/json"], produces = ["application/json"])
    @Throws(URISyntaxException::class)
    fun addNewUser(@Valid @RequestBody newuser: User): ResponseEntity<*>
    {
        val userCopy = userService!!.save(newuser)

        // set the location header for the newly created resource
        val responseHeaders = HttpHeaders()
        val newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(userCopy.userid)
                .toUri()
        responseHeaders.location = newUserURI

        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }


    @PutMapping(value = ["/user/{id}"])
    fun updateUser(@RequestBody updateUser: User, @PathVariable id: Long): ResponseEntity<*>
    {
        userService!!.update(updateUser, id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    fun deleteUserById(@PathVariable id: Long): ResponseEntity<*>
    {
        userService!!.delete(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}