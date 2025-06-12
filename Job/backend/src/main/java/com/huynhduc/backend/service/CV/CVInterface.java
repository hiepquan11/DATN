package com.huynhduc.backend.service.CV;

import com.huynhduc.backend.entity.JobportalsCirriculumvitae;

import java.util.List;

public interface CVInterface {

    List<JobportalsCirriculumvitae> findCvSeekerProfile(int id);
    JobportalsCirriculumvitae deleteCv(int id);

}
