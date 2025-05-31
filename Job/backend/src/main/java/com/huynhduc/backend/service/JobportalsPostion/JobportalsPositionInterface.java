package com.huynhduc.backend.service.JobportalsPostion;

import com.huynhduc.backend.entity.JobportalsPosition;

import java.util.List;

public interface JobportalsPositionInterface {
    JobportalsPosition createJobportalsPosition(JobportalsPosition position);
    List<JobportalsPosition> getAllJobportalsPositions();
    JobportalsPosition getJobportalsPositionById(int id);
    JobportalsPosition updateJobportalsPosition(int id, JobportalsPosition updatedPosition);
    void deleteJobportalsPosition(int id);
}
