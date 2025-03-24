package com.example.studentcrud.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.studentcrud.model.Staff;

@SuppressWarnings("unused")
public interface StaffRepository extends JpaRepository<Staff, Long>  {
}