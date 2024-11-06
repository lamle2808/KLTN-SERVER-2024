package com.example.kltn.service.implement;

import com.example.kltn.entity.Avatar;
import com.example.kltn.repository.AvatarRepo;
import com.example.kltn.service.AvatarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AvatartImpl implements AvatarService{
     private final AvatarRepo avatarRepo;

    @Override
    public Avatar addAvatar(Avatar avatar) {
        return avatarRepo.save(avatar);
    }

    @Override
    public boolean check(Long id) {
        return avatarRepo.existsById(id);
    }

    @Override
    public Boolean remove(Long id) {
        try {
            Avatar avatar = avatarRepo.findAvatarById(id);
            avatarRepo.delete(avatar);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Avatar getById(Long id) {
        return avatarRepo.findAvatarById(id);
    }
}
