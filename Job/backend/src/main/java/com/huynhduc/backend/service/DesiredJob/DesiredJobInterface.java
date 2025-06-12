package com.huynhduc.backend.service.DesiredJob;

import com.huynhduc.backend.entity.JobportalsDesiredjob;
import com.huynhduc.backend.repository.JobportalsDesiredJobRepo;

public interface DesiredJobInterface {
    JobportalsDesiredjob findBySeeker(int id);
}
