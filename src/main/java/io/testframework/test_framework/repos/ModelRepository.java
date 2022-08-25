package io.testframework.test_framework.repos;

import io.testframework.test_framework.domain.Model;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModelRepository extends JpaRepository<Model, UUID> {
}
