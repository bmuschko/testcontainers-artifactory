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
    static final ArtifactoryContainer artifactoryContainer = new ArtifactoryContainer(DockerImageName.parse(ArtifactoryContainer.OSS_DOCKER_IMAGE_NAME.getUnversionedPart() + ":" + ARTIFACTORY_CONTAINER_IMAGE_TAG));

    Artifactory createArtifactoryClient() {
        return ArtifactoryClientBuilder.create()
                .setUrl(artifactoryContainer.getHttpHostAddress())
                .setUsername(artifactoryContainer.getUsername())
                .setPassword(artifactoryContainer.getPassword())
                .build();
    }
}
