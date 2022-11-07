package com.example.employeemanager.service;

import com.example.employeemanager.exception.UserNotFoundException;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee){
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees(){
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Long id, Employee employee){
        Employee existingEmployee = employeeRepo.findById(id).orElse(null);
        existingEmployee.setName(employee.getName() == null?existingEmployee.getName():employee.getName());
        existingEmployee.setEmail(employee.getEmail() == null?existingEmployee.getEmail():employee.getEmail());
        existingEmployee.setJobTitle(employee.getJobTitle() == null?existingEmployee.getJobTitle():employee.getJobTitle());
        existingEmployee.setPhone(employee.getPhone() == null?existingEmployee.getPhone():employee.getPhone());
        existingEmployee.setImageUrl(employee.getImageUrl() == null?existingEmployee.getImageUrl():employee.getImageUrl());

        return employeeRepo.save(existingEmployee);

    }

    public Employee findEmployeeById(Long id){
        return employeeRepo.findEmployeeById(id).orElseThrow(() -> new UserNotFoundException("User By ID "+id+"was not found"));
    }

    public Employee deleteEmpl(Long id){
        Employee employee = employeeRepo.findById(id).orElse(null);
        if(employee != null){
            employeeRepo.deleteById(id);
        }
        return employee;
    }

}
