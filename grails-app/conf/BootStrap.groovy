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
        println "############################ GETTERS ############################"
        println "Getter: ${company.getName()} Property: ${company.name}"
        println "########################################################"
    }

    void companyToString() {
        println "######################### TO STRING() ###############################"
        println "toString of Company ${createCompany()}"
        println "########################################################"
    }

    void transientExample() {
        println "######################## TRANSIENT ################################"
        println new Employee(firstName: "Manoj", lastName: "Mohan").fullName
        println "########################################################"
    }

    void timestampExample() {
        Company company = createCompany()
        Employee employee = new Employee(company: company, firstName: "Manoj", lastName: "Mohan", email: "manoj@intelligrape.com", password: "password", salary: 200000F)
        println "########################### TIMESTAMP #############################"
        println "Timestamps before save Datecreated: ${employee.dateCreated} -- lastUpdated: ${employee.lastUpdated}"
        employee.save(flush: true)
        println "Timestamps after save Datecreated: ${employee.dateCreated} -- lastUpdated: ${employee.lastUpdated}"
        println "########################################################"
    }

    void validation() {
        println "########################## VALIDATION ##############################"
        Company company = createCompany()
        Employee employee = new Employee(firstName: "Manoj", lastName: "Mohan", company: company, email: 'manoj+1@intelligrape.com', salary: 2000000F)
        println "Validate employee ${employee.save()}"
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

        println "##################### ONE TO ONE ###################################"
        println "Engine of Car -: ${car.engine}"
        println "Car of Engine -: ${engine.car}"
        println "########################################################"

    }

    void oneToManyNoOwner() {
        println "#################### ONE TO MANY (NO OWNER) ####################################"
        println "Project count BEFORE save  ${Project.count()}"
        println "Task count BEFORE save  ${Task.count()}"
        Project project = new Project(name: "Project")
        Task task = new Task(name: "Test")
        println "Project tasks BEFORE adding task -: ${project.tasks}"

        project.addToTasks(task)
        project.save(flush: true)
        println "Task count AFTER project saved: ${Task.count()}"
        println "Project tasks AFTER save -: ${project.tasks}"

        println "########################################################"
        project.delete(flush: true)
        println "Project count AFTER delete -: ${Project.count()}"
        println "Task count AFTER project delete  -: ${Task.count()}"
        println "########################################################"
    }

    void oneToManyOwner() {
        println "###################### ONE TO MANY (BELONGS TO) ##################################"
        println "Project count before save  ${Project.count()}"
        Project project = new Project(name: "Project")
        Task task = new Task(name: "Test", project: project)
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
        println "####################### MANY TO MANY #################################"
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
