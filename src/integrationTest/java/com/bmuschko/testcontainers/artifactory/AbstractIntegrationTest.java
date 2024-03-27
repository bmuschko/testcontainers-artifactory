package com.bmuschko.testcontainers.artifactory;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractIntegrationTest {
    static final String ARTIFACTORY_CONTAINER_IMAGE_TAG = "7.77.5";

    @Container
    static final ArtifactoryContainer ARTIFACTORY_CONTAINER = new ArtifactoryContainer(DockerImageName.parse(ArtifactoryContainer.OSS_DOCKER_IMAGE_NAME.getUnversionedPart() + ":" + ARTIFACTORY_CONTAINER_IMAGE_TAG));

    Artifactory createArtifactoryClient() {
        return ArtifactoryClientBuilder.create()
                .setUrl(ARTIFACTORY_CONTAINER.getHttpHostAddress())
                .setUsername(ARTIFACTORY_CONTAINER.getUsername())
                .setPassword(ARTIFACTORY_CONTAINER.getPassword())
                .build();
    }
}
