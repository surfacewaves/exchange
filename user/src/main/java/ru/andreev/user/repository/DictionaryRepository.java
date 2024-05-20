package ru.andreev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.andreev.user.entity.Dictionary;

import java.util.UUID;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, UUID>, JpaSpecificationExecutor<Dictionary> {

    Dictionary findDictionaryByCodeAndKey(String dictionaryCode, String dictionaryKey);
}
