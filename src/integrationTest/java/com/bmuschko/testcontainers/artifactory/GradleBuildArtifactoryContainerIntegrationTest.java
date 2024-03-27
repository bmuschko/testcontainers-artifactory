package com.bmuschko.testcontainers.artifactory;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.MavenRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class GradleBuildArtifactoryContainerFunctionalTest {
    private static final String ARTIFACTORY_CONTAINER_IMAGE_TAG = "7.77.5";

    @Container
    private static final ArtifactoryContainer artifactoryContainer = new ArtifactoryContainer(DockerImageName.parse(ArtifactoryContainer.OSS_DOCKER_IMAGE_NAME.getUnversionedPart() + ":" + ARTIFACTORY_CONTAINER_IMAGE_TAG));

    @Test
    void canResolveDependenciesFromRemoteMavenRepository() {
        Artifactory artifactory = ArtifactoryClientBuilder.create()
                .setUrl(artifactoryContainer.getHttpHostAddress())
                .setUsername(artifactoryContainer.getUsername())
                .setPassword(artifactoryContainer.getPassword())
                .build();
        MavenRepositorySettings settings = new MavenRepositorySettingsImpl();
        Repository repository = artifactory.repositories()
                .builders()
                .localRepositoryBuilder()
                .key("maven")
                .description("local Maven repository")
                .repositorySettings(settings)
                .build();
        String result = artifactory.repositories().create(2, repository);
        System.out.println(result);
    }
}
