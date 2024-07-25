package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.repository.AnnouncementRepository;

import java.util.List;
import java.util.Optional;

public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> findAll(){
        return announcementRepository.findAll();
    }
    public Optional<Announcement> findByid(Integer id){
        return announcementRepository.findById(id);
    }

    public Announcement save(Announcement announcement){
        return announcementRepository.save(announcement);
    }
    public void deleteById(Integer id){
        announcementRepository.deleteById(id);
    }
}
