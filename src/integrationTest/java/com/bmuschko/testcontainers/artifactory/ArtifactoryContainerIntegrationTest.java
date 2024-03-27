package com.bmuschko.testcontainers.artifactory;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.jfrog.artifactory.client.ArtifactorySystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ArtifactoryContainerIntegrationTest {
    private static final String ARTIFACTORY_CONTAINER_IMAGE_TAG = "7.77.5";

    @Container
    private static final ArtifactoryContainer artifactoryContainer = new ArtifactoryContainer(DockerImageName.parse(ArtifactoryContainer.OSS_DOCKER_IMAGE_NAME.getUnversionedPart() + ":" + ARTIFACTORY_CONTAINER_IMAGE_TAG));

    @Test
    void getSystemVersion() {
        Artifactory artifactory = ArtifactoryClientBuilder.create()
                .setUrl(artifactoryContainer.getHttpHostAddress())
                .setUsername(artifactoryContainer.getUsername())
                .setPassword(artifactoryContainer.getPassword())
                .build();
        ArtifactorySystem artifactorySystem = artifactory.system();
        Assertions.assertTrue(artifactorySystem.ping());
        Assertions.assertEquals(ARTIFACTORY_CONTAINER_IMAGE_TAG, artifactorySystem.version().getVersion());
    }
}
