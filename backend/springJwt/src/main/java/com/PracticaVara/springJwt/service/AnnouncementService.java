package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.User;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;
import com.PracticaVara.springJwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public List<Announcement> findAll(){
        return announcementRepository.findAll();
    }
    public Optional<Announcement> findByid(Integer id){
        return announcementRepository.findById(id);
    }

    public Announcement save(Announcement announcement, Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            announcement.setUser(user.get());
            LocalDateTime now = LocalDateTime.now();
            announcement.setCreatedDate(now);
            announcement.setExpirationDate(now.plusDays(30));
            return announcementRepository.save(announcement);
        } else{
            throw new RuntimeException("User not found.");
        }

    }
    public void deleteById(Integer id){
        announcementRepository.deleteById(id);
    }
    public void deleteExpiredAnnouncements(){
        LocalDateTime now = LocalDateTime.now();
        List<Announcement> expiredAnnouncements = announcementRepository.findByExpirationDateBefore(now);
        announcementRepository.deleteAll(expiredAnnouncements);
    }
}
