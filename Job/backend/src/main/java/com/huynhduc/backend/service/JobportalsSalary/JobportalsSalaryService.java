package com.huynhduc.backend.service.JobportalsSalary;

import com.huynhduc.backend.entity.JobportalsSalary;
import com.huynhduc.backend.repository.JobportalsSalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsSalaryService implements JobportalsSalaryInterface {

    @Autowired
    private JobportalsSalaryRepo salaryRepository;

    @Override
    public JobportalsSalary createSalary(JobportalsSalary salary) {
        return salaryRepository.save(salary);
    }

    @Override
    public List<JobportalsSalary> getAllSalaries() {
        List<JobportalsSalary> salaries = salaryRepository.findAll();
        if (salaries.isEmpty()) {
            throw new RuntimeException("Không có mức lương nào được tìm thấy.");
        }
        return salaries;
    }

    @Override
    public JobportalsSalary getSalaryById(int id) {
        return salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mức lương với id: " + id));
    }

    @Override
    public JobportalsSalary updateSalary(int id, JobportalsSalary updatedSalary) {
        JobportalsSalary existing = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mức lương với id: " + id));

        existing.setSalary_name(updatedSalary.getSalary_name());

        return salaryRepository.save(existing);
    }

    @Override
    public void deleteSalary(int id) {
        if (salaryRepository.existsById(id)) {
            salaryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy mức lương với id: " + id);
        }
    }
}
