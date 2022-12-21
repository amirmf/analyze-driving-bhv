package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.dto.badge.BadgePage;
import com.taraan.dum.logic.BadgeLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Transactional
public class BadgeAssembler {
    @Autowired
    private BadgeLogic badgeLogic;

    public void createBadge(BadgeDto badgeDto) throws IOException {
        badgeLogic.createBadge(badgeDto);
    }

    public void updateBadgePicture(InputStream inputStream, Long badgeDto) throws IOException {
        badgeLogic.updatePictureBadge(inputStream, badgeDto);
    }

    public BadgeDto getBadge(Long id) {
        return badgeLogic.getBadge(id);
    }

    public void updateBadge(BadgeDto badgeDto) {
        badgeLogic.updateBadge(badgeDto);
    }

    public void deleteBadge(Long id) {
        badgeLogic.deleteBadge(id);
    }

    public BadgePage getBadges() {
        return badgeLogic.getBadges();
    }

    public BadgePage getBadges(boolean all, String name, String code, Long from, Long size) {
        return badgeLogic.getBadges(all, name, code, from, size);
    }
}
