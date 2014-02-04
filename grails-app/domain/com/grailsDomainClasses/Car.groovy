package com.grailsDomainClasses

class Car {

    Engine engine

    static constraints = {
        engine(nullable: true)
    }
}
