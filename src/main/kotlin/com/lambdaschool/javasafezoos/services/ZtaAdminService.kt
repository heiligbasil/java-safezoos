package com.lambdaschool.javasafezoos.services

import com.lambdaschool.javasafezoos.models.Animal
import com.lambdaschool.javasafezoos.models.Zoo
import com.lambdaschool.javasafezoos.views.CountOfAnimalsInZoos

interface ZtaService
{
    fun findAll(): MutableList<Zoo>

    fun findZooById(zooid: Long): Zoo

    fun findZooByName(zooname: String): Zoo

    fun findAnimalByType(animaltype: String): Animal

    fun getCountOfAnimalsInZoos(): MutableList<CountOfAnimalsInZoos>
}

interface AdminService
{
    fun delete(zooid: Long)

    fun update(zooData: Zoo, zooid: Long): Zoo

    fun create(zooData: Zoo): Zoo
}