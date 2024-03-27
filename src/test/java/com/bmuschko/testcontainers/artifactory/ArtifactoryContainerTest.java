package com.bmuschko.testcontainers.artifactory;

import org.junit.jupiter.api.Test;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

public class ArtifactoryContainerTest {
    @Test
    void failOnIncompatibleDockerImageName() {
        Throwable thrown = assertThrows(
                IllegalStateException.class,
                () -> new ArtifactoryContainer(DockerImageName.parse("releases-docker.jfrog.io/jfrog/artifactory:3.4.5")),
                "Incompatible container image name should have thrown exception"
        );
        assertTrue(thrown.getMessage().contains("Failed to verify that image"));
    }

    @Test
    void canInstantiateForCompatibleDockerImageName() {
        assertNotNull(new ArtifactoryContainer(DockerImageName.parse("releases-docker.jfrog.io/jfrog/artifactory-oss:3.4.5")));
        assertNotNull(new ArtifactoryContainer(DockerImageName.parse("releases-docker.jfrog.io/jfrog/artifactory-pro:3.4.5")));
    }

    @Test
    void returnDefaultCredentials() {
        ArtifactoryContainer artifactoryContainer = new ArtifactoryContainer(DockerImageName.parse("releases-docker.jfrog.io/jfrog/artifactory-oss:3.4.5"));
        assertEquals("admin", artifactoryContainer.getUsername());
        assertEquals("password", artifactoryContainer.getPassword());
    }
}
