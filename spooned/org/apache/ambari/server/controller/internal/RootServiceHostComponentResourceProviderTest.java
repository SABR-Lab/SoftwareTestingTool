package org.apache.ambari.server.controller.internal;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
public class RootServiceHostComponentResourceProviderTest {
    @org.junit.Test
    public void testGetResources() throws java.lang.Exception {
        org.apache.ambari.server.controller.spi.Resource.Type type = org.apache.ambari.server.controller.spi.Resource.Type.RootServiceHostComponent;
        org.apache.ambari.server.controller.AmbariManagementController managementController = org.easymock.EasyMock.createMock(org.apache.ambari.server.controller.AmbariManagementController.class);
        org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Host host1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.controller.HostResponse hostResponse1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.HostResponse.class);
        org.apache.ambari.server.controller.RootServiceHostComponentResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.RootServiceHostComponentResponse.class);
        org.apache.ambari.server.controller.AbstractRootServiceResponseFactory factory = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AbstractRootServiceResponseFactory.class);
        java.util.List<org.apache.ambari.server.state.Host> hosts = new java.util.LinkedList<>();
        hosts.add(host1);
        java.util.Set<org.apache.ambari.server.state.Cluster> clusterSet = new java.util.HashSet<>();
        clusterSet.add(cluster);
        java.util.Set<org.apache.ambari.server.controller.RootServiceHostComponentResponse> responseSet = new java.util.HashSet<>();
        responseSet.add(response);
        org.easymock.EasyMock.expect(managementController.getRootServiceResponseFactory()).andReturn(factory).anyTimes();
        org.easymock.EasyMock.expect(managementController.getClusters()).andReturn(clusters).anyTimes();
        org.easymock.EasyMock.expect(clusters.getHosts()).andReturn(hosts).anyTimes();
        org.easymock.EasyMock.expect(factory.getRootServiceHostComponent(((org.apache.ambari.server.controller.RootServiceHostComponentRequest) (org.easymock.EasyMock.anyObject())), org.easymock.EasyMock.anyObject())).andReturn(responseSet).anyTimes();
        org.easymock.EasyMock.expect(clusters.getCluster("Cluster100")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(clusters.getClustersForHost("Host100")).andReturn(clusterSet).anyTimes();
        org.easymock.EasyMock.expect(host1.getHostName()).andReturn("Host100").anyTimes();
        org.easymock.EasyMock.expect(host1.convertToResponse()).andReturn(hostResponse1).anyTimes();
        org.easymock.EasyMock.expect(hostResponse1.getClusterName()).andReturn("Cluster100").anyTimes();
        org.easymock.EasyMock.expect(hostResponse1.getHostname()).andReturn("Host100").anyTimes();
        org.easymock.EasyMock.expect(hostResponse1.getHealthReport()).andReturn("HEALTHY").anyTimes();
        org.easymock.EasyMock.replay(managementController, clusters, cluster, host1, hostResponse1, factory, response);
        org.apache.ambari.server.controller.spi.ResourceProvider provider = org.apache.ambari.server.controller.internal.AbstractControllerResourceProvider.getResourceProvider(type, managementController);
        java.util.Set<java.lang.String> propertyIds = new java.util.HashSet<>();
        propertyIds.add(org.apache.ambari.server.controller.internal.RootServiceHostComponentResourceProvider.SERVICE_NAME_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RootServiceHostComponentResourceProvider.HOST_NAME_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RootServiceHostComponentResourceProvider.COMPONENT_NAME_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RootServiceHostComponentResourceProvider.COMPONENT_STATE_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RootServiceHostComponentResourceProvider.PROPERTIES_PROPERTY_ID);
        propertyIds.add(org.apache.ambari.server.controller.internal.RootServiceHostComponentResourceProvider.COMPONENT_VERSION_PROPERTY_ID);
        org.apache.ambari.server.controller.spi.Request request = org.apache.ambari.server.controller.utilities.PropertyHelper.getReadRequest(propertyIds);
        provider.getResources(request, null);
        org.easymock.EasyMock.verify(managementController, clusters, cluster, host1, hostResponse1, factory, response);
    }
}
