package ru.andreev.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andreev.exchange.entity.Dictionary;
import ru.andreev.exchange.repository.DictionaryRepository;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public Dictionary findDictionaryByCodeAndKey(String dictionaryCode, String dictionaryKey) {
        return dictionaryRepository.findDictionaryByCodeAndKey(dictionaryCode, dictionaryKey);
    }
}
