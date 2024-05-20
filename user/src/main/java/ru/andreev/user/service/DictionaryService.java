package ru.andreev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreev.user.entity.Dictionary;
import ru.andreev.user.repository.DictionaryRepository;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public Dictionary findDictionaryByCodeAndKey(String dictionaryCode, String dictionaryKey) {
        return dictionaryRepository.findDictionaryByCodeAndKey(dictionaryCode, dictionaryKey);
    }
}
