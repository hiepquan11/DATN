package com.huynhduc.backend.service.JobportalsPostion;

import com.huynhduc.backend.entity.JobportalsPosition;
import com.huynhduc.backend.repository.JobportalsPositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobportalsPositionService implements JobportalsPositionInterface {

    @Autowired
    private JobportalsPositionRepo positionRepo;

    @Override
    public JobportalsPosition createJobportalsPosition(JobportalsPosition position) {
        return positionRepo.save(position);
    }

    @Override
    public List<JobportalsPosition> getAllJobportalsPositions() {
        List<JobportalsPosition> list = positionRepo.findAll();
        if (list.isEmpty()) {
            throw new RuntimeException("Không có vị trí nào được tìm thấy.");
        }
        return list;
    }

    @Override
    public JobportalsPosition getJobportalsPositionById(int id) {
        return positionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vị trí với id: " + id));
    }

    @Override
    public JobportalsPosition updateJobportalsPosition(int id, JobportalsPosition updatedPosition) {
        JobportalsPosition existing = positionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vị trí với id: " + id));
        existing.setPosition_name(updatedPosition.getPosition_name());
        return positionRepo.save(existing);
    }

    @Override
    public void deleteJobportalsPosition(int id) {
        if (positionRepo.existsById(id)) {
            positionRepo.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy vị trí với id: " + id);
        }
    }
}