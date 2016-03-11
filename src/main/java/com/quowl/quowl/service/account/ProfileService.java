package com.quowl.quowl.service.account;

import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.repository.user.ProfileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProfileService
        implements com.quowl.quowl.web.beans.Service<ProfileInfo, Long> {

    @Inject
    private ProfileRepository profileRepository;

    public List<Long> parseFromString(String str) {
        String stringWithoutBrackets = StringUtils.substringBetween(str, "[", "]");
        String stringWithoutSpaces = stringWithoutBrackets.replaceAll("\\s+", "");
        List<Long> result = Stream.of(stringWithoutSpaces.split(",")).map(Long::parseLong).collect(Collectors.toList());

        return result;
    }

    @Override
    public void save(ProfileInfo object) {
        profileRepository.save(object);
    }

    @Override
    public void delete(ProfileInfo object) {
        profileRepository.delete(object);
    }

    @Override
    public void delete(Long aLong) {
        profileRepository.delete(aLong);
    }

    @Override
    public List<ProfileInfo> findAll() {

        return profileRepository.findAll();
    }

    @Override
    public ProfileInfo findOne(Long aLong) {
        return profileRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return profileRepository.exists(aLong);
    }




}
