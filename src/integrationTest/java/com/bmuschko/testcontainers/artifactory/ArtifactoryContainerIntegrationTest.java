package com.bmuschko.testcontainers.artifactory;

import org.jfrog.artifactory.client.ArtifactorySystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArtifactoryContainerIntegrationTest extends AbstractIntegrationTest {
    @Test
    void getSystemInformation() {
        ArtifactorySystem artifactorySystem = createArtifactoryClient().system();
        Assertions.assertTrue(artifactorySystem.ping());
        Assertions.assertEquals(ARTIFACTORY_CONTAINER_IMAGE_TAG, artifactorySystem.version().getVersion());
        Assertions.assertNotNull(artifactorySystem.configuration());
    }
}
