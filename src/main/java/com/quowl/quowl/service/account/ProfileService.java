package com.quowl.quowl.service.account;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProfileService {

    public List<Long> parseFromString(String str) {
        String stringWithoutBrackets = StringUtils.substringBetween(str, "[", "]");
        String stringWithoutSpaces = stringWithoutBrackets.replaceAll("\\s+", "");
        List<Long> result = Stream.of(stringWithoutSpaces.split(",")).map(Long::parseLong).collect(Collectors.toList());

        return result;
    }

}
