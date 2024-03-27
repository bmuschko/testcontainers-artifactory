package com.bmuschko.testcontainers.artifactory;

import com.github.dockerjava.api.model.Bind;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public class ArtifactoryContainer extends GenericContainer<ArtifactoryContainer> {
    private static final String REPO_NAME = "releases-docker.jfrog.io";
    public static DockerImageName OSS_DOCKER_IMAGE_NAME = DockerImageName.parse(REPO_NAME + "/jfrog/artifactory-oss");
    public static DockerImageName PRO_DOCKER_IMAGE_NAME = DockerImageName.parse(REPO_NAME + "/jfrog/artifactory-pro");

   public ArtifactoryContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(OSS_DOCKER_IMAGE_NAME, PRO_DOCKER_IMAGE_NAME);
        withExposedPorts(8081, 8082);
        withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withBinds(Bind.parse(("artifactory-data:/var/opt/jfrog/artifactory"))));
        waitingFor(Wait.forLogMessage(".*All services started successfully.*", 1).withStartupTimeout(Duration.ofSeconds(300)));
    }

    public String getHttpHostAddress() {
        return "http://" + getHost() + ":" + getFirstMappedPort() + "/artifactory";
    }

    public String getUsername() {
       return "admin";
    }

    public String getPassword() {
       return "password";
    }
}
