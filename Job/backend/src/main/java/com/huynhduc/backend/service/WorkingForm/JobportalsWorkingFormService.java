package com.huynhduc.backend.service.WorkingForm;

import com.huynhduc.backend.entity.JobportalsWorkingform;
import com.huynhduc.backend.repository.JobportalsWorkingFormRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsWorkingFormService implements JobportalsWorkingFormInterface {

    @Autowired
    private JobportalsWorkingFormRepo workingFormRepository;

    @Override
    public List<JobportalsWorkingform> getAllWorkingForms() {
        List<JobportalsWorkingform> workingForms = workingFormRepository.findAll();
        if (workingForms.isEmpty()) {
            throw new RuntimeException("Không có hình thức làm việc nào được tìm thấy.");
        }
        return workingForms;
    }

    @Override
    public JobportalsWorkingform getWorkingFormById(int id) {
        return workingFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình thức làm việc với id: " + id));
    }

    @Override
    public JobportalsWorkingform createWorkingForm(JobportalsWorkingform workingForm) {
        return workingFormRepository.save(workingForm);
    }

    @Override
    public JobportalsWorkingform updateWorkingForm(int id, JobportalsWorkingform updatedWorkingForm) {
        JobportalsWorkingform existing = workingFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình thức làm việc với id: " + id));

        existing.setWorking_form_name(updatedWorkingForm.getWorking_form_name());

        return workingFormRepository.save(existing);
    }

    @Override
    public void deleteWorkingForm(int id) {
        if (workingFormRepository.existsById(id)) {
            workingFormRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy hình thức làm việc với id: " + id);
        }
    }
}
