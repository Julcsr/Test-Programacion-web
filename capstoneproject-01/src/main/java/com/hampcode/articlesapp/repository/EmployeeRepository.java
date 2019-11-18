package com.hampcode.articlesapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.hampcode.articlesapp.model.Employee;


@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{

	@Query(value="SELECT * FROM employees WHERE employee_dni=?",nativeQuery = true)
	public List<Employee> fechfindByDni(String dni);
	
	Page<Employee> findAll(Pageable pageable);
	//List<Employee> findByDni(String dni);
	
}
