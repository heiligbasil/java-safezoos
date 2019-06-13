package com.lambdaschool.javasafezoos.controllers

import com.lambdaschool.javasafezoos.models.Animal
import com.lambdaschool.javasafezoos.models.Zoo
import com.lambdaschool.javasafezoos.services.AdminService
import com.lambdaschool.javasafezoos.services.ZtaService
import com.lambdaschool.javasafezoos.views.CountOfAnimalsInZoos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/zta"])
class ZtaController
{
    @Autowired
    lateinit var ztaService: ZtaService

    @GetMapping(value = ["/zoos"], produces = ["application/json"])
    fun listAllZoos(): ResponseEntity<*>
    {
        val listOfZoos: MutableList<Zoo> = ztaService.findAll()
        return ResponseEntity(listOfZoos, HttpStatus.OK)
    }

    @GetMapping(value = ["/zoo/{zooid}"], produces = ["application/json"])
    fun listZooById(@PathVariable zooid: Long): ResponseEntity<*>
    {
        val listOfOrds: Zoo = ztaService.findZooById(zooid)
        return ResponseEntity(listOfOrds, HttpStatus.OK)
    }


    @GetMapping(value = ["/zoo/name/{zooname}"], produces = ["application/json"])
    fun findZooByName(@PathVariable zooname: String): ResponseEntity<*>
    {
        val zoo: Zoo = ztaService.findZooByName(zooname)
        return ResponseEntity<Any>(zoo, HttpStatus.OK)
    }

    @GetMapping(value = ["/animal/{animaltype}"], produces = ["application/json"])
    fun findAnimalByType(@PathVariable animaltype: String): ResponseEntity<*>
    {
        val animal: Animal = ztaService.findAnimalByType(animaltype)
        return ResponseEntity<Any>(animal, HttpStatus.OK)
    }

    @GetMapping(value = ["/animals"], produces = ["application/json"])
    fun countOfAnimalsInZoos(): ResponseEntity<*>
    {
        return ResponseEntity<MutableList<CountOfAnimalsInZoos>>(ztaService.getCountOfAnimalsInZoos(), HttpStatus.OK)
    }
}

@RestController
@RequestMapping(value = ["/admin"])
class AdminController
{
    @Autowired
    lateinit var adminService: AdminService

    @DeleteMapping(value = ["/zoos/{zooid}"])
    fun deleteZooById(@PathVariable zooid: Long): ResponseEntity<*>
    {
        adminService.delete(zooid)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping(value = ["/zoo/{zooid}"])
    fun updateCustomerById(@RequestBody zooData: Zoo, @PathVariable zooid: Long): ResponseEntity<*>
    {
        adminService.update(zooData, zooid)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping(value = ["/zoo"], consumes = ["application/json"], produces = ["application/json"])
    fun addNewZoo(@Valid @RequestBody zooData: Zoo): ResponseEntity<*>
    {
        var zooCopy: Zoo = zooData
        zooCopy = adminService.create(zooCopy)

        val responseHeaders = HttpHeaders()
        val newURI: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{zooid}").buildAndExpand(zooCopy.zooid).toUri()

        responseHeaders.location = newURI

        return ResponseEntity<Any>(null, responseHeaders, HttpStatus.CREATED)
    }
}