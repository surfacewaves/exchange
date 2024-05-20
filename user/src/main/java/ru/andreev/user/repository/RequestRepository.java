package ru.andreev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.andreev.user.entity.Dictionary;
import ru.andreev.user.entity.Request;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID>, JpaSpecificationExecutor<Request> {
    List<Request> getRequestsByStatus(Dictionary dictionary);
}
