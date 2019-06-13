package com.lambdaschool.javasafezoos.controllers

import com.lambdaschool.javasafezoos.models.Role
import com.lambdaschool.javasafezoos.services.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URISyntaxException
import javax.validation.Valid

@RestController
@RequestMapping("/roles")
class RolesController
{
    @Autowired
    internal var roleService: RoleService? = null

    @GetMapping(value = ["/roles"], produces = ["application/json"])
    fun listRoles(): ResponseEntity<*>
    {
        val allRoles = roleService!!.findAll()
        return ResponseEntity<List<Role>>(allRoles, HttpStatus.OK)
    }

    @GetMapping(value = ["/role/{roleId}"], produces = ["application/json"])
    fun getRole(@PathVariable roleId: Long): ResponseEntity<*>
    {
        val r = roleService!!.findRoleById(roleId)
        return ResponseEntity<Any>(r, HttpStatus.OK)
    }

    @PostMapping(value = ["/role"])
    @Throws(URISyntaxException::class)
    fun addNewRole(@Valid @RequestBody newRole: Role): ResponseEntity<*>
    {
        val roleCopy = roleService!!.save(newRole)

        // set the location header for the newly created resource
        val responseHeaders = HttpHeaders()
        val newRoleURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{roleid}")
                .buildAndExpand(roleCopy.getRoleid())
                .toUri()
        responseHeaders.location = newRoleURI

        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }


    @DeleteMapping("/role/{id}")
    fun deleteRoleById(@PathVariable id: Long): ResponseEntity<*>
    {
        roleService!!.delete(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}
