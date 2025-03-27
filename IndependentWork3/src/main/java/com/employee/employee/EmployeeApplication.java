package com.employee.employee;

import com.employee.employee.core.entity.Department;
import com.employee.employee.core.entity.Employee;
import com.employee.employee.core.repository.DepartmentRepository;
import com.employee.employee.core.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EmployeeApplication.class, args);
		EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
		DepartmentRepository departmentRepository = context.getBean(DepartmentRepository.class);

		Department department = Department.builder()
				.name("rndm")
				.build();

		department = departmentRepository.save(department);

		Department department_2 = Department.builder().name("rndm2").build();
		department_2 = departmentRepository.save(department_2);

		Employee employee_1 = Employee.builder()
				.name("Testers_1")
				.position("Middle")
				.department(department)
				.build();
		employeeRepository.save(employee_1);

		Employee employee_2 = Employee.builder()
				.name("Testers_2")
				.position("Senior")
				.department(department)
				.build();
		employeeRepository.save(employee_2);

		Employee employee_3 = Employee.builder()
				.name("Testers_3")
				.position("Junior")
				.department(department_2)
				.build();
		employeeRepository.save(employee_3);
	}

}
