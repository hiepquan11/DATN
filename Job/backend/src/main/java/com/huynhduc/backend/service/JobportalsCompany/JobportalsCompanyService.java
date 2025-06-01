package com.huynhduc.backend.service.JobportalsCompany;

import com.huynhduc.backend.entity.JobportalsCompany;
import com.huynhduc.backend.repository.JobportalsCompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobportalsCompanyService implements JobportalsCompanyInterface {

    @Autowired
    private JobportalsCompanyRepo companyRepo;

    @Override
    public JobportalsCompany createCompany(JobportalsCompany company) {
        return companyRepo.save(company);
    }

    @Override
    public List<JobportalsCompany> getAllCompanies() {
        List<JobportalsCompany> companies = companyRepo.findAll();
        if (companies.isEmpty()) {
            throw new RuntimeException("Không có công ty nào được tìm thấy.");
        }
        return companies;
    }

    @Override
    public JobportalsCompany getCompanyById(int id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công ty với id: " + id));
    }

    @Override
    public JobportalsCompany updateCompany(int id, JobportalsCompany updatedCompany) {
        JobportalsCompany existing = companyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công ty với id: " + id));

        existing.setCompany_name(updatedCompany.getCompany_name());
        existing.setField_operation(updatedCompany.getField_operation());
        existing.setCompany_size(updatedCompany.getCompany_size());
        existing.setPhone_number(updatedCompany.getPhone_number());
        existing.setTax_id_number(updatedCompany.getTax_id_number());
        existing.setCompany_website_url(updatedCompany.getCompany_website_url());
        existing.setAddress(updatedCompany.getAddress());
        existing.setCompany_description(updatedCompany.getCompany_description());
        existing.setCompany_cover_image(updatedCompany.getCompany_cover_image());

        existing.setRecruiter(updatedCompany.getRecruiter());
        existing.setCity(updatedCompany.getCity());

        return companyRepo.save(existing);
    }

    @Override
    public void deleteCompany(int id) {
        if (companyRepo.existsById(id)) {
            companyRepo.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy công ty với id: " + id);
        }
    }

    @Override
    public Page<JobportalsCompany> getCompaniesWithFilters(Pageable pageable) {
        return companyRepo.findAll(pageable);
    }
}
