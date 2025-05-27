package com.huynhduc.backend.service.WorkingForm;

import com.huynhduc.backend.entity.JobportalsWorkingform;

import java.util.List;

public interface JobportalsWorkingFormInterface {
    List<JobportalsWorkingform> getAllWorkingForms();
    JobportalsWorkingform getWorkingFormById(int id);
    JobportalsWorkingform createWorkingForm(JobportalsWorkingform workingForm);
    JobportalsWorkingform updateWorkingForm(int id, JobportalsWorkingform workingForm);
    void deleteWorkingForm(int id);
}
