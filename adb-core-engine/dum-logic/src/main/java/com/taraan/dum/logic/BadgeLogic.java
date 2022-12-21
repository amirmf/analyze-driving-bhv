package com.taraan.dum.logic;

import com.taraan.dum.common.FileTypesUtils;
import com.taraan.dum.da.BadgeDa;
import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.dto.badge.BadgePage;
import com.taraan.dum.mappers.BadgeMapper;
import com.taraan.dum.model.hibernate.Badge;
import com.taraan.dum.model.hibernate.DateRange;
import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Component
public class BadgeLogic {
    @Autowired
    private BadgeDa badgeDa;
    @Value("${iconPath}")
    private String iconPath;
    @Value("${iconUrl}")
    private String iconUrl;

    public void createBadge(BadgeDto badgeDto) throws IOException {
        Badge badge = new Badge();
        badge.setCode(badgeDto.getCode());
        badge.setDescription(badgeDto.getDescription());
        badge.setIcon("");
        badge.setName(badgeDto.getName());
        badge.setDateRange(new DateRange(new Date()));
        badgeDa.save(badge);
    }

    public void updatePictureBadge(InputStream inputStream, Long id) throws IOException {
        Badge badge = badgeDa.get(id);
        final byte[] data = IOUtils.toByteArray(inputStream);
        final String fileType = FileTypesUtils.checkType(data);
        final String fileName = System.currentTimeMillis() + "." + fileType;
        final File file = new File(iconPath + fileName);
        FileUtils.writeByteArrayToFile(file, data);
        badge.setIcon(fileName);
        badgeDa.update(badge);
    }

    public void updateBadge(BadgeDto badgeDto) {
        Badge badge = badgeDa.get(badgeDto.getId());
        badge.setCode(badgeDto.getCode());
        badge.setDescription(badgeDto.getDescription());
        badge.setName(badgeDto.getName());
        badgeDa.update(badge);
    }

    public void deleteBadge(Long id) {
        Badge badge = badgeDa.get(id);
        badge.getDateRange().setTo(new Date());
        badgeDa.update(badge);
    }

    public BadgeDto getBadge(Long id) {
        Badge badge = badgeDa.get(id);
        return BadgeMapper.getDto(badge);
    }

    public BadgePage getBadges() {
        List<Badge> badges = badgeDa.get(false, null, null, 0L, 1000L);
        return new BadgePage(BadgeMapper.getDtos(badges), badgeDa.getCount());
    }

    public BadgePage getBadges(boolean all, String name, String code, Long from, Long size) {
        List<Badge> badges = badgeDa.get(all, name, code, from, size);
        return new BadgePage(BadgeMapper.getDtos(badges), badgeDa.getCount(all, name, code));
    }
}
