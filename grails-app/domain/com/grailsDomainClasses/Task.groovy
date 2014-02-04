package com.grailsDomainClasses

class Task {

    String name

    static belongsTo = [project: Project]

    static constraints = {
    }
}
