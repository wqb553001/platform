//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mh.FlatForm.entity;

public class DependencyBean {
    String groupId;
    String artifactId;
    String version;
    String classifier;

    public DependencyBean() {
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(final String artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    @Override
    public String toString() {
        return "DependencyBean{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", classifier='" + classifier + '\'' +
                '}';
    }
}
