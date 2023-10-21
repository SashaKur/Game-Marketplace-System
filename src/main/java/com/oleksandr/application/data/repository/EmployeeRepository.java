package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.employee.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
