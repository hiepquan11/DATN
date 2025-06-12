package com.huynhduc.backend.service.CV;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.huynhduc.backend.entity.JobportalsCirriculumvitae;
import com.huynhduc.backend.entity.JobportalsJobseekerprofile;
import com.huynhduc.backend.entity.JobportalsUser;
import com.huynhduc.backend.repository.CvRepo;
import com.huynhduc.backend.repository.JobportalsUserRepo;
import com.huynhduc.backend.repository.SeekerProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CvService implements CVInterface {

    @Autowired
    private CvRepo cvRepo;

    @Autowired
    private JobportalsUserRepo userRepo;

    @Autowired
    private SeekerProfileRepo profileRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<JobportalsCirriculumvitae> findCvSeekerProfile(int seekerProfileId) {
        JobportalsUser user = userRepo.findById(seekerProfileId);
        if (user == null) throw new RuntimeException("User not found with id: " + seekerProfileId);

        JobportalsJobseekerprofile profile = profileRepo.findBySeekerId(user.getId());
        if (profile == null) throw new RuntimeException("Seeker profile not found for user: " + seekerProfileId);

        return cvRepo.findBySeekerProfile(profile.getId());
    }

    public JobportalsCirriculumvitae uploadCvFile(int userId, MultipartFile file) {
        JobportalsUser user = userRepo.findById(userId);
        if (user == null) throw new RuntimeException("User not found with id: " + userId);

        JobportalsJobseekerprofile profile = profileRepo.findBySeekerId(user.getId());
        if (profile == null) throw new RuntimeException("Seeker profile not found for user: " + userId);

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "auto",
                            "public_id", "cv_" + userId + "_" + System.currentTimeMillis(),
                            "overwrite", true
                    ));

            String url = (String) uploadResult.get("secure_url");

            JobportalsCirriculumvitae cv = new JobportalsCirriculumvitae();
            cv.setProfile(profile);
            cv.setCvUrl(url);

            return cvRepo.save(cv);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload CV to Cloudinary", e);
        }
    }

    @Override
    public JobportalsCirriculumvitae deleteCv(int id) {
        Optional<JobportalsCirriculumvitae> optionalCv = cvRepo.findById(id);
        if (optionalCv.isEmpty()) throw new RuntimeException("CV not found with id: " + id);

        JobportalsCirriculumvitae cv = optionalCv.get();

        try {
            String publicId = extractPublicIdFromUrl(cv.getCvUrl());
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "auto"));
        } catch (Exception e) {
            System.err.println("Error deleting from Cloudinary: " + e.getMessage());
        }

        cvRepo.delete(cv);
        return cv;
    }

    private String extractPublicIdFromUrl(String url) {
        if (url == null || !url.contains("/")) return null;
        String[] parts = url.split("/");
        String fileWithExt = parts[parts.length - 1];
        String fileName = fileWithExt.split("\\.")[0];
        return "cv/job_seekers/" + fileName;
    }
}
