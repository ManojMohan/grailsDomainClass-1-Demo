import com.grailsDomainClasses.Car
import com.grailsDomainClasses.Company
import com.grailsDomainClasses.Employee
import com.grailsDomainClasses.Engine
import com.grailsDomainClasses.Project
import com.grailsDomainClasses.Task

class BootStrap {

    def init = { servletContext ->
        getter()
        companyToString()
        transientExample()
        timestampExample()
        validation()
        oneToOne()
        oneToManyNoOwner()
        oneToManyOwner()
        manyToMany()
    }

    void getter() {
        Company company = createCompany()
        println "########################################################"
        println "Getter ${company.getName()} Property ${company.name}"
        println "########################################################"
    }

    void companyToString() {
        println "########################################################"
        println "toString of Company ${createCompany()}"
        println "########################################################"
    }

    void transientExample() {
        println "########################################################"
        println new Employee(firstName: "Manoj", lastName: "Mohan").fullName
        println "########################################################"
    }

    void timestampExample() {
        Employee employee = new Employee(firstName: "Manoj", lastName: "Mohan")
        println "########################################################"
        println "Timestamps before save Datecreated: ${employee.dateCreated} -- lastUpdated: ${employee.lastUpdated}"
        employee.save()
        println "Timestamps after save Datecreated: ${employee.dateCreated} -- lastUpdated: ${employee.lastUpdated}"
        println "########################################################"
    }

    void validation() {
        Company company = createCompany()
        Employee employee = new Employee(firstName: "Manoj", lastName: "Mohan", company: company, email: 'manoj+1@intelligrape.com', salary: 2000000F)
        println "Validate employee ${employee.save()}"
        println "########################################################"
        employee.errors.allErrors.each {
            println it
        }
        println "########################################################"

        //For more validation examples go through the classes and documentation
    }

    void oneToOne() {
        Car car = new Car()
        car.save()
        Engine engine = new Engine(car: car)
        engine.save()

        println "########################################################"
        println "Engine of Car -: ${car.engine}"
        println "Car of Engine -: ${engine.car}"
        println "########################################################"

    }

    void oneToManyNoOwner() {
        println "########################################################"
        println "Project count before save  ${Project.count()}"
        Project project = new Project(name: "Project")
        project.save()
        project.refresh()
        Task task = new Task(name: "Test")

        println "Task count before save  ${Task.count()}"
        println "Project tasks before adding task -: ${project.tasks}"
        project.addToTasks(task)
        project.save(flush: true)
        println "Project tasks after save -: ${project.tasks}"
        println "########################################################"
        project.delete(flush: true)
        println "Project count after delete -: ${Project.count()}"
        println "Task count after project delete  -: ${Task.count()}"
        println "########################################################"

    }

    void oneToManyOwner() {
        println "########################################################"
        println "Project count before save  ${Project.count()}"
        Project project = new Project(name: "Project")
        Task task = new Task(name: "Test")

        println "Task count before save  ${Task.count()}"
        println "Project tasks before adding task -: ${project.tasks}"
        project.addToTasks(task)
        project.save()
        println "Project tasks after save -: ${project.tasks}"
        println "########################################################"
        project.delete(flush: true)
        println "Project count after delete -: ${Project.count()}"
        println "Task count after project delete  -: ${Task.count()}"
        println "########################################################"

    }

    void manyToMany() {
        println "########################################################"
        Employee employee = new Employee(firstName: "Manoj", lastName: "Mohan", company: createCompany(), email: 'manoj+1@intelligrape.com', password: "123411", salary: 20000F)
        Project project = new Project(name: "Project 1")
        println "Before adding project to employee ${employee.projects}"
        println "Before adding project to employee ${project.employees}"
        employee.addToProjects(project)
        employee.save()
        println "After adding project to employee ${employee.projects}"
        println "After adding project to employee ${project.employees}"
        println "########################################################"
    }

    Company createCompany() {
        return new Company(name: "Intelligrape").save(flush: true)
    }


    def destroy = {
    }
}
