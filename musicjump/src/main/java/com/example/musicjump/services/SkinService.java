package com.example.musicjump.services;

import com.example.musicjump.DTO.AddSkinDTO;
import com.example.musicjump.models.Skin;
import com.example.musicjump.models.User;
import com.example.musicjump.repos.SkinRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkinService {
    private final SkinRepo skinRepo;
    private final UserService userService;

    public List<Skin> getAllSkins(){
        return skinRepo.findAll();
    }

    public String addSkin(AddSkinDTO addSkinDTO){
        Skin skin = new Skin(addSkinDTO.getName(), addSkinDTO.getCost());
        skinRepo.save(skin);
        userService.setSkinToAllUsers(skin);
        return "redirect:/admin";
    }
}
