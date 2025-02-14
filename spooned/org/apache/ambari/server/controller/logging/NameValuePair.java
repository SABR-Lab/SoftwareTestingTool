package org.apache.ambari.server.controller.logging;
public class NameValuePair {
    private java.lang.String name;

    private java.lang.String value;

    public NameValuePair() {
    }

    public NameValuePair(java.lang.String name, java.lang.String value) {
        this.name = name;
        this.value = value;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("value")
    public java.lang.String getValue() {
        return value;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("value")
    public void setValue(java.lang.String value) {
        this.value = value;
    }
}
