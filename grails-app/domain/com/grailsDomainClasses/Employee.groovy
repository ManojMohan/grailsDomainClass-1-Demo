package com.grailsDomainClasses

class Employee {

    String firstName
    String lastName
    String email
    String password
    Float salary
    Date dateCreated
    Date lastUpdated

    static transients = ['fullName']

    static belongsTo = [company: Company]

    static hasMany = [projects: Project]

    static constraints = {
        email(unique: true)
        password(size: 5..15, blank: false)
        salary(max: 1000000F)
    }

    String getFullName() {
        return "${firstName} ${lastName}"
    }


}
