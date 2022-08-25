package io.testframework.test_framework.repos;

import io.testframework.test_framework.domain.Asset;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssetRepository extends JpaRepository<Asset, UUID> {
}
