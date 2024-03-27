package com.bmuschko.testcontainers.artifactory;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.MavenRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl;
import org.junit.jupiter.api.Test;

public class GradleBuildArtifactoryContainerIntegrationTest extends AbstractIntegrationTest {
    @Test
    void canResolveDependenciesFromRemoteMavenRepository() {
        Artifactory artifactory = createArtifactoryClient();
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
