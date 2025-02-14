package org.apache.ambari.server.controller;
import javax.persistence.RollbackException;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.captureBoolean;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.partialMockBuilder;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
public class AmbariManagementControllerImplTest {
    private static final org.apache.ambari.server.security.ldap.AmbariLdapDataPopulator ldapDataPopulator = org.easymock.EasyMock.createMock(org.apache.ambari.server.security.ldap.AmbariLdapDataPopulator.class);

    private static final org.apache.ambari.server.state.Clusters clusters = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Clusters.class);

    private static final org.apache.ambari.server.actionmanager.ActionDBAccessorImpl actionDBAccessor = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionDBAccessorImpl.class);

    private static final org.apache.ambari.server.api.services.AmbariMetaInfo ambariMetaInfo = org.easymock.EasyMock.createMock(org.apache.ambari.server.api.services.AmbariMetaInfo.class);

    private static final org.apache.ambari.server.security.authorization.Users users = org.easymock.EasyMock.createMock(org.apache.ambari.server.security.authorization.Users.class);

    private static final org.apache.ambari.server.controller.AmbariSessionManager sessionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.AmbariSessionManager.class);

    @org.junit.BeforeClass
    public static void setupAuthentication() {
        org.apache.ambari.server.security.authorization.internal.InternalAuthenticationToken authenticationToken = new org.apache.ambari.server.security.authorization.internal.InternalAuthenticationToken("admin");
        authenticationToken.setAuthenticated(true);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @org.junit.Before
    public void before() throws java.lang.Exception {
        org.easymock.EasyMock.reset(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.actionDBAccessor, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.users, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager);
    }

    @org.junit.Test
    public void testgetAmbariServerURI() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.easymock.EasyMock.replay(injector);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, null, injector);
        class AmbariConfigsSetter {
            public void setConfigs(org.apache.ambari.server.controller.AmbariManagementController controller, java.lang.String masterProtocol, java.lang.String masterHostname, java.lang.Integer masterPort) throws java.lang.Exception {
                java.lang.Class<?> c = controller.getClass();
                java.lang.reflect.Field f = c.getDeclaredField("masterProtocol");
                f.setAccessible(true);
                f.set(controller, masterProtocol);
                f = c.getDeclaredField("masterHostname");
                f.setAccessible(true);
                f.set(controller, masterHostname);
                f = c.getDeclaredField("masterPort");
                f.setAccessible(true);
                f.set(controller, masterPort);
            }
        }
        AmbariConfigsSetter ambariConfigsSetter = new AmbariConfigsSetter();
        ambariConfigsSetter.setConfigs(controller, "http", "hostname", 8080);
        org.junit.Assert.assertEquals("http://hostname:8080/jdk_path", controller.getAmbariServerURI("/jdk_path"));
        ambariConfigsSetter.setConfigs(controller, "https", "somesecuredhost", 8443);
        org.junit.Assert.assertEquals("https://somesecuredhost:8443/mysql_path", controller.getAmbariServerURI("/mysql_path"));
        ambariConfigsSetter.setConfigs(controller, "https", "othersecuredhost", 8443);
        org.junit.Assert.assertEquals("https://othersecuredhost:8443/oracle/ojdbc/", controller.getAmbariServerURI("/oracle/ojdbc/"));
        ambariConfigsSetter.setConfigs(controller, "http", "hostname", 8080);
        org.junit.Assert.assertEquals("http://hostname:8080/jdk_path?query", controller.getAmbariServerURI("/jdk_path?query"));
        org.easymock.EasyMock.verify(injector);
    }

    @org.junit.Test
    public void testGetClusters() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.ClusterRequest request1 = new org.apache.ambari.server.controller.ClusterRequest(null, "cluster1", "1", java.util.Collections.emptySet());
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.controller.ClusterResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterResponse.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(cluster.convertToResponse()).andReturn(response);
        org.apache.ambari.server.security.encryption.CredentialStoreService credentialStoreService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.encryption.CredentialStoreService.class);
        org.easymock.EasyMock.expect(credentialStoreService.isInitialized(org.easymock.EasyMock.anyObject(org.apache.ambari.server.security.encryption.CredentialStoreType.class))).andReturn(true).anyTimes();
        org.easymock.EasyMock.replay(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, response, credentialStoreService);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        java.lang.reflect.Field f = controller.getClass().getDeclaredField("credentialStoreService");
        f.setAccessible(true);
        f.set(controller, credentialStoreService);
        java.util.Set<org.apache.ambari.server.controller.ClusterResponse> setResponses = controller.getClusters(setRequests);
        org.junit.Assert.assertEquals(1, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, response, credentialStoreService);
    }

    @org.junit.Test
    public void testGetClientHostForRunningAction_componentIsNull() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = null;
        org.easymock.EasyMock.replay(cluster, service, injector);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        java.lang.String host = controller.getClientHostForRunningAction(cluster, service, component);
        org.junit.Assert.assertNull(host);
        org.easymock.EasyMock.verify(cluster, service, injector);
    }

    @org.junit.Test
    public void testGetClientHostForRunningAction_componentMapIsEmpty() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> hostMap = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(hostMap);
        org.easymock.EasyMock.replay(cluster, service, component, injector);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        java.lang.String host = controller.getClientHostForRunningAction(cluster, service, component);
        org.easymock.EasyMock.verify(cluster, service, component, injector);
        org.junit.Assert.assertNull(host);
    }

    @org.junit.Test
    public void testGetClientHostForRunningAction_returnsHelathyHost() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> hostMap = org.easymock.EasyMock.createNiceMock(java.util.Map.class);
        java.util.Set<java.lang.String> hostsSet = org.easymock.EasyMock.createNiceMock(java.util.Set.class);
        org.easymock.EasyMock.expect(hostMap.isEmpty()).andReturn(false);
        org.easymock.EasyMock.expect(hostMap.keySet()).andReturn(hostsSet);
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(hostMap).times(2);
        org.easymock.EasyMock.replay(cluster, service, component, injector, actionManager, hostMap, hostsSet);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("filterHostsForAction").addMockedMethod("getHealthyHost").withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).createMock();
        org.easymock.EasyMock.expect(controller.getHealthyHost(hostsSet)).andReturn("healthy_host");
        controller.filterHostsForAction(hostsSet, service, cluster, org.apache.ambari.server.controller.spi.Resource.Type.Cluster);
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.replay(controller);
        java.lang.String host = controller.getClientHostForRunningAction(cluster, service, component);
        org.junit.Assert.assertEquals("healthy_host", host);
        org.easymock.EasyMock.verify(controller, cluster, service, component, injector, hostMap);
    }

    @org.junit.Test
    public void testGetClientHostForRunningAction_clientComponent() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service");
        org.easymock.EasyMock.expect(service.getServiceComponent("component")).andReturn(component);
        org.easymock.EasyMock.expect(service.getDesiredStackId()).andReturn(stackId);
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn("stack");
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn("1.0");
        org.apache.ambari.server.state.ServiceInfo serviceInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceInfo.class);
        org.apache.ambari.server.state.ComponentInfo compInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.easymock.EasyMock.expect(serviceInfo.getClientComponent()).andReturn(compInfo);
        org.easymock.EasyMock.expect(compInfo.getName()).andReturn("component");
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(java.util.Collections.singletonMap("host", null));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getService("stack", "1.0", "service")).andReturn(serviceInfo);
        org.easymock.EasyMock.replay(injector, cluster, service, component, serviceInfo, compInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackId);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        org.apache.ambari.server.state.ServiceComponent resultComponent = controller.getClientComponentForRunningAction(cluster, service);
        org.junit.Assert.assertNotNull(resultComponent);
        org.junit.Assert.assertEquals(component, resultComponent);
        org.easymock.EasyMock.verify(injector, cluster, service, component, serviceInfo, compInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackId);
    }

    @org.junit.Test
    public void testGetClientHostForRunningAction_clientComponentThrowsException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.ServiceComponent component1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service");
        org.easymock.EasyMock.expect(service.getServiceComponent("component")).andThrow(new org.apache.ambari.server.ServiceComponentNotFoundException("cluster", "service", "component"));
        org.easymock.EasyMock.expect(service.getDesiredStackId()).andReturn(stackId);
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn("stack");
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn("1.0");
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> componentsMap = new java.util.HashMap<>();
        componentsMap.put("component1", component1);
        componentsMap.put("component2", component2);
        org.easymock.EasyMock.expect(service.getServiceComponents()).andReturn(componentsMap);
        org.easymock.EasyMock.expect(component1.getServiceComponentHosts()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(component2.getServiceComponentHosts()).andReturn(java.util.Collections.singletonMap("anyHost", null));
        org.apache.ambari.server.state.ServiceInfo serviceInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceInfo.class);
        org.apache.ambari.server.state.ComponentInfo compInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ComponentInfo.class);
        org.easymock.EasyMock.expect(serviceInfo.getClientComponent()).andReturn(compInfo);
        org.easymock.EasyMock.expect(compInfo.getName()).andReturn("component");
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getService("stack", "1.0", "service")).andReturn(serviceInfo);
        org.easymock.EasyMock.replay(injector, cluster, service, component1, component2, serviceInfo, compInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackId);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        org.apache.ambari.server.state.ServiceComponent resultComponent = controller.getClientComponentForRunningAction(cluster, service);
        org.junit.Assert.assertNotNull(resultComponent);
        org.junit.Assert.assertEquals(component2, resultComponent);
        org.easymock.EasyMock.verify(injector, cluster, service, component1, component2, serviceInfo, compInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackId);
    }

    @org.junit.Test
    public void testGetClientHostForRunningAction_noClientComponent() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.ServiceComponent component1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service");
        org.easymock.EasyMock.expect(service.getDesiredStackId()).andReturn(stackId);
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn("stack");
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn("1.0");
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> componentsMap = new java.util.HashMap<>();
        componentsMap.put("component1", component1);
        componentsMap.put("component2", component2);
        org.easymock.EasyMock.expect(service.getServiceComponents()).andReturn(componentsMap);
        org.easymock.EasyMock.expect(component1.getServiceComponentHosts()).andReturn(java.util.Collections.emptyMap());
        org.easymock.EasyMock.expect(component2.getServiceComponentHosts()).andReturn(java.util.Collections.singletonMap("anyHost", null));
        org.apache.ambari.server.state.ServiceInfo serviceInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceInfo.class);
        org.easymock.EasyMock.expect(serviceInfo.getClientComponent()).andReturn(null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getService("stack", "1.0", "service")).andReturn(serviceInfo);
        org.easymock.EasyMock.replay(injector, cluster, service, component1, component2, serviceInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackId);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        org.apache.ambari.server.state.ServiceComponent resultComponent = controller.getClientComponentForRunningAction(cluster, service);
        org.junit.Assert.assertNotNull(resultComponent);
        org.junit.Assert.assertEquals(component2, resultComponent);
        org.easymock.EasyMock.verify(injector, cluster, service, component1, component2, serviceInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackId);
    }

    @org.junit.Test
    public void testGetClusters___ClusterNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.ClusterRequest request1 = new org.apache.ambari.server.controller.ClusterRequest(null, "cluster1", "1", java.util.Collections.emptySet());
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andThrow(new org.apache.ambari.server.ClusterNotFoundException("cluster1"));
        org.easymock.EasyMock.replay(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        try {
            controller.getClusters(setRequests);
            org.junit.Assert.fail("expected ClusterNotFoundException");
        } catch (org.apache.ambari.server.ClusterNotFoundException e) {
        }
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters);
    }

    @org.junit.Test
    public void testGetClusters___OR_Predicate_ClusterNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Cluster cluster2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.controller.ClusterResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterResponse.class);
        org.apache.ambari.server.controller.ClusterResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterResponse.class);
        org.apache.ambari.server.controller.ClusterRequest request1 = new org.apache.ambari.server.controller.ClusterRequest(null, "cluster1", "1", java.util.Collections.emptySet());
        org.apache.ambari.server.controller.ClusterRequest request2 = new org.apache.ambari.server.controller.ClusterRequest(null, "cluster2", "1", java.util.Collections.emptySet());
        org.apache.ambari.server.controller.ClusterRequest request3 = new org.apache.ambari.server.controller.ClusterRequest(null, "cluster3", "1", java.util.Collections.emptySet());
        org.apache.ambari.server.controller.ClusterRequest request4 = new org.apache.ambari.server.controller.ClusterRequest(null, "cluster4", "1", java.util.Collections.emptySet());
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        setRequests.add(request4);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andThrow(new org.apache.ambari.server.ClusterNotFoundException("cluster1"));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster2")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster3")).andReturn(cluster2);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster4")).andThrow(new org.apache.ambari.server.ClusterNotFoundException("cluster4"));
        org.easymock.EasyMock.expect(cluster.convertToResponse()).andReturn(response);
        org.easymock.EasyMock.expect(cluster2.convertToResponse()).andReturn(response2);
        org.apache.ambari.server.security.encryption.CredentialStoreService credentialStoreService = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.encryption.CredentialStoreService.class);
        org.easymock.EasyMock.expect(credentialStoreService.isInitialized(org.easymock.EasyMock.anyObject(org.apache.ambari.server.security.encryption.CredentialStoreType.class))).andReturn(true).anyTimes();
        org.easymock.EasyMock.replay(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, cluster2, response, response2, credentialStoreService);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        java.lang.reflect.Field f = controller.getClass().getDeclaredField("credentialStoreService");
        f.setAccessible(true);
        f.set(controller, credentialStoreService);
        java.util.Set<org.apache.ambari.server.controller.ClusterResponse> setResponses = controller.getClusters(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, cluster2, response, response2, credentialStoreService);
    }

    @org.junit.Test
    public void testValidateClusterName() throws java.lang.Exception {
        org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("clustername");
        org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("CLUSTERNAME");
        org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("clustername123");
        org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("cluster-name");
        org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("cluster_name");
        try {
            org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName(null);
            junit.framework.Assert.fail("IllegalArgumentException not thrown");
        } catch (java.lang.IllegalArgumentException e) {
        }
        try {
            org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("clusternameclusternameclusternameclusternameclusternameclusternameclusternameclusternameclusternameclustername");
            junit.framework.Assert.fail("IllegalArgumentException not thrown");
        } catch (java.lang.IllegalArgumentException e) {
        }
        try {
            org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("clustername@#$%");
            junit.framework.Assert.fail("IllegalArgumentException not thrown");
        } catch (java.lang.IllegalArgumentException e) {
        }
        try {
            org.apache.ambari.server.controller.AmbariManagementControllerImpl.validateClusterName("");
            junit.framework.Assert.fail("IllegalArgumentException not thrown");
        } catch (java.lang.IllegalArgumentException e) {
        }
    }

    @org.junit.Test
    public void testUpdateClusters() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        org.apache.ambari.server.controller.ConfigurationRequest configurationRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ConfigurationRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(clusterRequest);
        java.util.List<org.apache.ambari.server.controller.ConfigurationRequest> configRequests = new java.util.ArrayList<>();
        configRequests.add(configurationRequest);
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.AgentConfigsHolder agentConfigsHolder = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).addMockedMethod("updateData").createMock();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, null, kerberosHelper, m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(m_metadataHolder.get()).andReturn(metadataHolder).anyTimes();
        org.easymock.EasyMock.expect(metadataHolder.updateData(org.easymock.EasyMock.anyObject())).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(m_agentConfigsHolder.get()).andReturn(agentConfigsHolder).anyTimes();
        agentConfigsHolder.updateData(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.util.List.class));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.expect(clusterRequest.getClusterName()).andReturn("clusterNew").times(5);
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(clusterRequest.getDesiredConfig()).andReturn(configRequests);
        org.easymock.EasyMock.expect(configurationRequest.getVersionTag()).andReturn(null).times(1);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("clusterOld").times(1);
        cluster.setClusterName("clusterNew");
        org.easymock.EasyMock.expectLastCall();
        configurationRequest.setVersionTag(org.easymock.EasyMock.anyObject(java.lang.String.class));
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, configurationRequest, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).addMockedMethod("getClusterMetadataOnConfigsUpdate").createMock();
        controller.updateClusters(setRequests, null);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, configurationRequest, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
    }

    @org.junit.Test
    @org.junit.Ignore
    public void testUpdateClustersWithNullConfigPropertyValues() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        org.apache.ambari.server.state.Config config = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Config.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, kerberosHelper);
        org.easymock.EasyMock.expect(clusterRequest.getClusterName()).andReturn("clusterNew").anyTimes();
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).anyTimes();
        org.apache.ambari.server.controller.ConfigurationRequest configReq = new org.apache.ambari.server.controller.ConfigurationRequest();
        final java.util.Map<java.lang.String, java.lang.String> configReqProps = com.google.common.collect.Maps.newHashMap();
        configReqProps.put("p1", null);
        configReq.setProperties(configReqProps);
        org.easymock.EasyMock.expect(clusterRequest.getDesiredConfig()).andReturn(com.google.common.collect.ImmutableList.of(configReq)).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("clusterOld").anyTimes();
        org.easymock.EasyMock.expect(cluster.getConfigPropertiesTypes(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(com.google.common.collect.Maps.newHashMap()).anyTimes();
        org.easymock.EasyMock.expect(config.getType()).andReturn("config-type").anyTimes();
        org.easymock.EasyMock.expect(config.getProperties()).andReturn(configReqProps).anyTimes();
        org.easymock.EasyMock.expect(config.getPropertiesAttributes()).andReturn(new java.util.HashMap<>()).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredConfigByType(org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(config).anyTimes();
        cluster.addSessionAttributes(org.easymock.EasyMock.anyObject());
        org.easymock.EasyMock.expectLastCall().once();
        cluster.setClusterName("clusterNew");
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, config, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        controller.updateClusters(setRequests, null);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, config, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberosNotInvoked() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.AgentConfigsHolder agentConfigsHolder = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).addMockedMethod("updateData").createMock();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, null, kerberosHelper, m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(m_metadataHolder.get()).andReturn(metadataHolder).anyTimes();
        org.easymock.EasyMock.expect(metadataHolder.updateData(org.easymock.EasyMock.anyObject())).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(m_agentConfigsHolder.get()).andReturn(agentConfigsHolder).anyTimes();
        agentConfigsHolder.updateData(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.util.List.class));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).addMockedMethod("getClusterMetadataOnConfigsUpdate").createMock();
        controller.updateClusters(setRequests, null);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberosReenable() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.AgentConfigsHolder agentConfigsHolder = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).addMockedMethod("updateData").createMock();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, null, kerberosHelper, m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(m_metadataHolder.get()).andReturn(metadataHolder).anyTimes();
        org.easymock.EasyMock.expect(metadataHolder.updateData(org.easymock.EasyMock.anyObject())).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(m_agentConfigsHolder.get()).andReturn(agentConfigsHolder).anyTimes();
        agentConfigsHolder.updateData(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.util.List.class));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(clusterRequest.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.KERBEROS).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.expect(cluster.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.KERBEROS).anyTimes();
        org.easymock.EasyMock.expect(kerberosHelper.shouldExecuteCustomOperations(org.apache.ambari.server.state.SecurityType.KERBEROS, null)).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getForceToggleKerberosDirective(org.easymock.EasyMock.anyObject())).andReturn(false).once();
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).addMockedMethod("getClusterMetadataOnConfigsUpdate").createMock();
        controller.updateClusters(setRequests, null);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberosEnable() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.AgentConfigsHolder agentConfigsHolder = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).addMockedMethod("updateData").createMock();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, null, kerberosHelper, m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(m_metadataHolder.get()).andReturn(metadataHolder).anyTimes();
        org.easymock.EasyMock.expect(metadataHolder.updateData(org.easymock.EasyMock.anyObject())).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(m_agentConfigsHolder.get()).andReturn(agentConfigsHolder).anyTimes();
        agentConfigsHolder.updateData(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.util.List.class));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(clusterRequest.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.KERBEROS).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.expect(cluster.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.NONE).anyTimes();
        org.easymock.EasyMock.expect(kerberosHelper.shouldExecuteCustomOperations(org.apache.ambari.server.state.SecurityType.KERBEROS, null)).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getForceToggleKerberosDirective(null)).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getManageIdentitiesDirective(null)).andReturn(null).once();
        org.easymock.EasyMock.expect(kerberosHelper.toggleKerberos(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.SecurityType.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.internal.RequestStageContainer.class), org.easymock.EasyMock.anyBoolean())).andReturn(null).once();
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).addMockedMethod("getClusterMetadataOnConfigsUpdate").createMock();
        controller.updateClusters(setRequests, null);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberosDisable_Default() throws java.lang.Exception {
        testUpdateClustersToggleKerberosDisable(null);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberosDisable_NoManageIdentities() throws java.lang.Exception {
        testUpdateClustersToggleKerberosDisable(java.lang.Boolean.FALSE);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberosDisable_ManageIdentities() throws java.lang.Exception {
        testUpdateClustersToggleKerberosDisable(java.lang.Boolean.TRUE);
    }

    private void testUpdateClustersToggleKerberosDisable(java.lang.Boolean manageIdentities) throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.easymock.Capture<java.lang.Boolean> manageIdentitiesCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.AgentConfigsHolder agentConfigsHolder = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).addMockedMethod("updateData").createMock();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, null, kerberosHelper, m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(m_metadataHolder.get()).andReturn(metadataHolder).anyTimes();
        org.easymock.EasyMock.expect(metadataHolder.updateData(org.easymock.EasyMock.anyObject())).andReturn(true).anyTimes();
        org.easymock.EasyMock.expect(m_agentConfigsHolder.get()).andReturn(agentConfigsHolder).anyTimes();
        agentConfigsHolder.updateData(org.easymock.EasyMock.anyLong(), org.easymock.EasyMock.anyObject(java.util.List.class));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(clusterRequest.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.NONE).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.expect(cluster.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.KERBEROS).anyTimes();
        org.easymock.EasyMock.expect(kerberosHelper.shouldExecuteCustomOperations(org.apache.ambari.server.state.SecurityType.NONE, null)).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getForceToggleKerberosDirective(org.easymock.EasyMock.anyObject())).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getManageIdentitiesDirective(org.easymock.EasyMock.anyObject())).andReturn(manageIdentities).once();
        org.easymock.EasyMock.expect(kerberosHelper.toggleKerberos(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.SecurityType.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.internal.RequestStageContainer.class), org.easymock.EasyMock.captureBoolean(manageIdentitiesCapture))).andReturn(null).once();
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).addMockedMethod("getClusterMetadataOnConfigsUpdate").createMock();
        controller.updateClusters(setRequests, null);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(manageIdentities, manageIdentitiesCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_metadataHolder, metadataHolder, m_agentConfigsHolder, agentConfigsHolder);
    }

    @org.junit.Test
    public void testUpdateClustersToggleKerberos_Fail() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.apache.ambari.server.controller.KerberosHelper kerberosHelper = org.easymock.EasyMock.createStrictMock(org.apache.ambari.server.controller.KerberosHelper.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.MetadataHolder metadataHolder = org.easymock.EasyMock.createMock(org.apache.ambari.server.agent.stomp.MetadataHolder.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.agent.stomp.AgentConfigsHolder agentConfigsHolder = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.agent.stomp.AgentConfigsHolder.class).addMockedMethod("updateData").createMock();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, null, kerberosHelper, m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(clusterRequest.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.NONE).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.expect(cluster.getResourceId()).andReturn(1L).times(3);
        org.easymock.EasyMock.expect(cluster.getSecurityType()).andReturn(org.apache.ambari.server.state.SecurityType.KERBEROS).anyTimes();
        org.easymock.EasyMock.expect(cluster.getCurrentStackVersion()).andReturn(null).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(null).anyTimes();
        cluster.setCurrentStackVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class));
        org.easymock.EasyMock.expectLastCall().once();
        org.easymock.EasyMock.expect(kerberosHelper.shouldExecuteCustomOperations(org.apache.ambari.server.state.SecurityType.NONE, null)).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getForceToggleKerberosDirective(org.easymock.EasyMock.anyObject())).andReturn(false).once();
        org.easymock.EasyMock.expect(kerberosHelper.getManageIdentitiesDirective(org.easymock.EasyMock.anyObject())).andReturn(null).once();
        org.easymock.EasyMock.expect(kerberosHelper.toggleKerberos(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Cluster.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.SecurityType.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.controller.internal.RequestStageContainer.class), org.easymock.EasyMock.anyBoolean())).andThrow(new java.lang.IllegalArgumentException("bad args!")).once();
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_agentConfigsHolder, agentConfigsHolder, m_metadataHolder, metadataHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = org.easymock.EasyMock.partialMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).withConstructor(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).addMockedMethod("getClusterMetadataOnConfigsUpdate").createMock();
        try {
            controller.updateClusters(setRequests, null);
            junit.framework.Assert.fail("IllegalArgumentException not thrown");
        } catch (java.lang.IllegalArgumentException e) {
        }
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager, kerberosHelper, m_agentConfigsHolder, agentConfigsHolder, m_metadataHolder, metadataHolder);
    }

    @org.junit.Test
    public void testUpdateClusters__RollbackException() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.actionmanager.ActionManager actionManager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.controller.ClusterRequest clusterRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ClusterRequest.class);
        java.util.Set<org.apache.ambari.server.controller.ClusterRequest> setRequests = java.util.Collections.singleton(clusterRequest);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.easymock.EasyMock.expect(clusterRequest.getClusterName()).andReturn("clusterNew").times(5);
        org.easymock.EasyMock.expect(clusterRequest.getClusterId()).andReturn(1L).times(4);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClusterById(1L)).andReturn(cluster).times(1);
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("clusterOld").times(1);
        cluster.setClusterName("clusterNew");
        org.easymock.EasyMock.expectLastCall().andThrow(new javax.persistence.RollbackException());
        org.easymock.EasyMock.replay(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(actionManager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        try {
            controller.updateClusters(setRequests, null);
            org.junit.Assert.fail("Expected RollbackException");
        } catch (javax.persistence.RollbackException e) {
        }
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(actionManager, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, clusterRequest, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager);
    }

    @org.junit.Test
    public void testGetHostComponents() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(componentHost)).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder = org.easymock.EasyMock.createMock(com.google.inject.Provider.class);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), m_metadataHolder, m_agentConfigsHolder);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", host);
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1");
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1");
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost);
            }
        });
        org.easymock.EasyMock.expect(componentHost.convertToResponse(null)).andReturn(response);
        org.easymock.EasyMock.expect(componentHost.getHostName()).andReturn("host1").anyTimes();
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(componentHost, host)).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF);
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, response, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost, m_agentConfigsHolder, m_metadataHolder);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(1, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, response, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost, m_agentConfigsHolder, m_metadataHolder);
    }

    @org.junit.Test
    public void testGetHostComponents___ServiceComponentHostNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster));
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1");
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1").anyTimes();
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(null);
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        try {
            controller.getHostComponents(setRequests);
            org.junit.Assert.fail("expected ServiceComponentHostNotFoundException");
        } catch (org.apache.ambari.server.ServiceComponentHostNotFoundException e) {
        }
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component);
    }

    @org.junit.Test
    public void testGetHostComponents___ServiceComponentHostFilteredByState() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        request1.setState("INSTALLED");
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", host);
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("cl1");
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1");
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1").anyTimes();
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost1);
            }
        });
        org.easymock.EasyMock.expect(componentHost1.getState()).andReturn(org.apache.ambari.server.state.State.INSTALLED);
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost1, response1);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> responses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertTrue(responses.size() == 1);
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost1, response1);
    }

    @org.junit.Test
    public void testGetHostComponents___ServiceComponentHostFilteredByMaintenanceState() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        request1.setMaintenanceState("ON");
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.IMPLIED_FROM_SERVICE).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster));
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", host);
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn("cl1");
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1");
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1").anyTimes();
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost1);
            }
        });
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost1, response1);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> responses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertTrue(responses.size() == 1);
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost1, response1);
    }

    @org.junit.Test
    public void testGetHostComponents___OR_Predicate_ServiceComponentHostNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper stateHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(stateHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", host);
            }
        }).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request2 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component2", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request3 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component3", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, stateHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster).times(3);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service).times(3);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component1);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1").anyTimes();
        org.easymock.EasyMock.expect(component1.getName()).andReturn("component1");
        org.easymock.EasyMock.expect(component1.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost1);
            }
        });
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component2")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component2")).andReturn(component2);
        org.easymock.EasyMock.expect(component2.getName()).andReturn("component2");
        org.easymock.EasyMock.expect(component2.getServiceComponentHosts()).andReturn(null);
        org.easymock.EasyMock.expect(componentHost2.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component3")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component3")).andReturn(component3);
        org.easymock.EasyMock.expect(component3.getName()).andReturn("component3");
        org.easymock.EasyMock.expect(component3.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost2);
            }
        });
        org.easymock.EasyMock.expect(componentHost2.convertToResponse(null)).andReturn(response2);
        org.easymock.EasyMock.replay(stateHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component1, component2, component3, componentHost1, componentHost2, response1, response2);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component1, component2, component3, componentHost1, componentHost2, response1, response2);
    }

    @org.junit.Test
    public void testGetHostComponents___OR_Predicate_ServiceNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request2 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component2", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request3 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component3", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster).times(3);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster)).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stack).anyTimes();
        org.easymock.EasyMock.expect(stack.getStackName()).andReturn("stackName").anyTimes();
        org.easymock.EasyMock.expect(stack.getStackVersion()).andReturn("stackVersion").anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", host);
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1").atLeastOnce();
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component1);
        org.easymock.EasyMock.expect(component1.getName()).andReturn("component1");
        org.easymock.EasyMock.expect(component1.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost1);
            }
        });
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component2")).andThrow(new org.apache.ambari.server.ServiceNotFoundException("cluster1", "service2"));
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component3")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component3")).andReturn(component3);
        org.easymock.EasyMock.expect(component3.getName()).andReturn("component3");
        org.easymock.EasyMock.expect(component3.getServiceComponentHosts()).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>() {
            {
                put("host1", componentHost2);
            }
        });
        org.easymock.EasyMock.expect(componentHost2.convertToResponse(null)).andReturn(response2);
        org.easymock.EasyMock.expect(componentHost2.getHostName()).andReturn("host1");
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component1, component2, component3, componentHost1, componentHost2, response1, response2);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component1, component2, component3, componentHost1, componentHost2, response1, response2);
    }

    @org.junit.Test
    public void testGetHostComponents___OR_Predicate_ServiceComponentNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.Service service2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request2 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component2", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request3 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component3", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster).times(3);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andReturn(java.util.Collections.singleton(cluster)).anyTimes();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(com.google.common.collect.ImmutableMap.<java.lang.String, org.apache.ambari.server.state.Host>builder().put("host1", host).build()).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stack).anyTimes();
        org.easymock.EasyMock.expect(stack.getStackName()).andReturn("stackName").anyTimes();
        org.easymock.EasyMock.expect(stack.getStackVersion()).andReturn("stackVersion").anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1").anyTimes();
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1");
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(com.google.common.collect.ImmutableMap.<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>builder().put("host1", componentHost1).build());
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(cluster.getService("service2")).andReturn(service2);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component2")).andReturn(service2);
        org.easymock.EasyMock.expect(service2.getName()).andReturn("service2");
        org.easymock.EasyMock.expect(service2.getServiceComponent("component2")).andThrow(new org.apache.ambari.server.ServiceComponentNotFoundException("cluster1", "service2", "component2"));
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component3")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component3")).andReturn(component3);
        org.easymock.EasyMock.expect(component3.getName()).andReturn("component3");
        org.easymock.EasyMock.expect(component3.getServiceComponentHosts()).andReturn(com.google.common.collect.ImmutableMap.<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost>builder().put("host1", componentHost2).build());
        org.easymock.EasyMock.expect(componentHost2.convertToResponse(null)).andReturn(response2);
        org.easymock.EasyMock.expect(componentHost2.getHostName()).andReturn("host1");
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, service2, component, component2, component3, componentHost1, componentHost2, response1, response2);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, service2, component, component2, component3, componentHost1, componentHost2, response1, response2);
    }

    @org.junit.Test
    public void testGetHostComponents___OR_Predicate_HostNotFoundException_hostProvidedInQuery() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        final org.apache.ambari.server.state.Host host = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.Service service2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        final org.apache.ambari.server.state.ServiceComponentHost componentHost2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", null, null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request2 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component2", "host2", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request3 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component3", null, null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster).times(3);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", host);
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stack).anyTimes();
        org.easymock.EasyMock.expect(stack.getStackName()).andReturn("stackName").anyTimes();
        org.easymock.EasyMock.expect(stack.getStackVersion()).andReturn("stackVersion").anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1").anyTimes();
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1");
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(java.util.Collections.singletonMap("foo", componentHost1));
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host2")).andThrow(new org.apache.ambari.server.HostNotFoundException("host2"));
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component3")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component3")).andReturn(component3);
        org.easymock.EasyMock.expect(component3.getName()).andReturn("component3");
        org.easymock.EasyMock.expect(component3.getServiceComponentHosts()).andReturn(java.util.Collections.singletonMap("foo", componentHost2));
        org.easymock.EasyMock.expect(componentHost2.convertToResponse(null)).andReturn(response2);
        org.easymock.EasyMock.expect(componentHost2.getHostName()).andReturn("host1");
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, service2, component, component2, component3, componentHost1, componentHost2, response1, response2);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        junit.framework.Assert.assertNotNull(setResponses);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, host, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, service2, component, component2, component3, componentHost1, componentHost2, response1, response2);
    }

    @org.junit.Test
    public void testGetHostComponents___OR_Predicate_HostNotFoundException_hostProvidedInURL() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request2 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component2", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request3 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component3", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getClustersForHost("host1")).andThrow(new org.apache.ambari.server.HostNotFoundException("host1"));
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        try {
            controller.getHostComponents(setRequests);
            org.junit.Assert.fail("expected exception");
        } catch (org.apache.ambari.server.AmbariException e) {
        }
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo);
    }

    @org.junit.Test
    public void testGetHostComponents___OR_Predicate_ClusterNotFoundException() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", "host1", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request2 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component2", "host2", null);
        org.apache.ambari.server.controller.ServiceComponentHostRequest request3 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component3", "host1", null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        setRequests.add(request2);
        setRequests.add(request3);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andThrow(new org.apache.ambari.server.ClusterNotFoundException("cluster1"));
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        try {
            controller.getHostComponents(setRequests);
            org.junit.Assert.fail("expected exception");
        } catch (org.apache.ambari.server.ParentObjectNotFoundException e) {
        }
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo);
    }

    @org.junit.Test
    public void testGetHostComponents___NullHostName() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.state.ServiceComponentHost componentHost2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, "component1", null, null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> mapHostComponents = new java.util.HashMap<>();
        mapHostComponents.put("foo", componentHost1);
        mapHostComponents.put("bar", componentHost2);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class));
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getService("service1")).andReturn(service);
        org.easymock.EasyMock.expect(component.getName()).andReturn("component1").anyTimes();
        org.easymock.EasyMock.expect(cluster.getServiceByComponentName("component1")).andReturn(service);
        org.easymock.EasyMock.expect(service.getServiceComponent("component1")).andReturn(component);
        org.easymock.EasyMock.expect(service.getName()).andReturn("service1");
        org.easymock.EasyMock.expect(component.getServiceComponentHosts()).andReturn(mapHostComponents);
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost2.convertToResponse(null)).andReturn(response2);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(componentHost2.getHostName()).andReturn("host1");
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, response1, response2, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost1, componentHost2);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(2, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, response1, response2, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, component, componentHost1, componentHost2);
    }

    @org.junit.Test
    public void testGetHostComponents___NullHostName_NullComponentName() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.state.StackId stack = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.Service service2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent component1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponent component2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost componentHost1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.state.ServiceComponentHost componentHost2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.state.ServiceComponentHost componentHost3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response1 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response2 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.ServiceComponentHostResponse response3 = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.ServiceComponentHostResponse.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.easymock.EasyMock.expect(maintHelper.getEffectiveState(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.ServiceComponentHost.class), org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.Host.class))).andReturn(org.apache.ambari.server.state.MaintenanceState.OFF).anyTimes();
        org.apache.ambari.server.controller.ServiceComponentHostRequest request1 = new org.apache.ambari.server.controller.ServiceComponentHostRequest("cluster1", null, null, null, null);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostRequest> setRequests = new java.util.HashSet<>();
        setRequests.add(request1);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> mapServices = new java.util.HashMap<>();
        mapServices.put("foo", service1);
        mapServices.put("bar", service2);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> mapHostComponents = new java.util.HashMap<>();
        mapHostComponents.put("foo", componentHost1);
        mapHostComponents.put("bar", componentHost2);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("cluster1")).andReturn(cluster);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getHostsForCluster(((java.lang.String) (org.easymock.EasyMock.anyObject())))).andReturn(new java.util.HashMap<java.lang.String, org.apache.ambari.server.state.Host>() {
            {
                put("host1", org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Host.class));
            }
        }).anyTimes();
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(mapServices);
        org.easymock.EasyMock.expect(service1.getServiceComponents()).andReturn(java.util.Collections.singletonMap("foo", component1));
        org.easymock.EasyMock.expect(service2.getServiceComponents()).andReturn(java.util.Collections.singletonMap("bar", component2));
        org.easymock.EasyMock.expect(component1.getName()).andReturn("component1").anyTimes();
        org.easymock.EasyMock.expect(component2.getName()).andReturn("component2").anyTimes();
        org.easymock.EasyMock.expect(component1.getServiceComponentHosts()).andReturn(mapHostComponents);
        org.easymock.EasyMock.expect(componentHost1.convertToResponse(null)).andReturn(response1);
        org.easymock.EasyMock.expect(componentHost2.convertToResponse(null)).andReturn(response2);
        org.easymock.EasyMock.expect(componentHost1.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(componentHost2.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(componentHost3.getHostName()).andReturn("host1");
        org.easymock.EasyMock.expect(component2.getServiceComponentHosts()).andReturn(java.util.Collections.singletonMap("foobar", componentHost3));
        org.easymock.EasyMock.expect(componentHost3.convertToResponse(null)).andReturn(response3);
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, response1, response2, response3, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service1, service2, component1, component2, componentHost1, componentHost2, componentHost3);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.util.Set<org.apache.ambari.server.controller.ServiceComponentHostResponse> setResponses = controller.getHostComponents(setRequests);
        org.junit.Assert.assertSame(controller, controllerCapture.getValue());
        org.junit.Assert.assertEquals(3, setResponses.size());
        org.junit.Assert.assertTrue(setResponses.contains(response1));
        org.junit.Assert.assertTrue(setResponses.contains(response2));
        org.junit.Assert.assertTrue(setResponses.contains(response3));
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, response1, response2, response3, stack, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service1, service2, component1, component2, componentHost1, componentHost2, componentHost3);
    }

    @org.junit.Test
    public void testPopulateServicePackagesInfo() throws java.lang.Exception {
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.controller.MaintenanceStateHelper maintHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MaintenanceStateHelper.class);
        org.apache.ambari.server.state.ServiceInfo serviceInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceInfo.class);
        java.util.Map<java.lang.String, java.lang.String> hostParams = new java.util.HashMap<>();
        java.lang.String osFamily = "testOSFamily";
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceOsSpecific> osSpecifics = new java.util.HashMap<>();
        org.apache.ambari.server.state.ServiceOsSpecific.Package package1 = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        package1.setName("testrpm1");
        org.apache.ambari.server.state.ServiceOsSpecific.Package package2 = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        package2.setName("testrpm2");
        org.apache.ambari.server.state.ServiceOsSpecific.Package package3 = new org.apache.ambari.server.state.ServiceOsSpecific.Package();
        package3.setName("testrpm3");
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packageList1 = new java.util.ArrayList<>();
        packageList1.add(package1);
        java.util.List<org.apache.ambari.server.state.ServiceOsSpecific.Package> packageList2 = new java.util.ArrayList<>();
        packageList2.add(package2);
        packageList2.add(package3);
        org.apache.ambari.server.state.ServiceOsSpecific osSpecific1 = new org.apache.ambari.server.state.ServiceOsSpecific("testOSFamily");
        osSpecific1.addPackages(packageList1);
        org.apache.ambari.server.state.ServiceOsSpecific osSpecific2 = new org.apache.ambari.server.state.ServiceOsSpecific("testOSFamily1,testOSFamily,testOSFamily2");
        osSpecific2.addPackages(packageList2);
        osSpecifics.put("testOSFamily", osSpecific1);
        osSpecifics.put("testOSFamily1,testOSFamily,testOSFamily2", osSpecific2);
        org.easymock.EasyMock.expect(serviceInfo.getOsSpecifics()).andReturn(osSpecifics);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, null, maintHelper, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class), null, null);
        org.apache.ambari.server.state.stack.OsFamily osFamilyMock = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.stack.OsFamily.class);
        org.easymock.EasyMock.expect(osFamilyMock.isVersionedOsFamilyExtendedByVersionedFamily("testOSFamily", "testOSFamily")).andReturn(true).times(3);
        org.easymock.EasyMock.replay(maintHelper, injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, serviceInfo, osFamilyMock);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.NestedTestClass nestedTestClass = this.new NestedTestClass(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector, osFamilyMock);
        org.apache.ambari.server.state.ServiceOsSpecific serviceOsSpecific = nestedTestClass.populateServicePackagesInfo(serviceInfo, hostParams, osFamily);
        org.junit.Assert.assertEquals(3, serviceOsSpecific.getPackages().size());
    }

    @org.junit.Test
    public void testCreateDefaultHostParams() throws java.lang.Exception {
        java.lang.String clusterName = "c1";
        java.lang.String SOME_STACK_NAME = "SomeStackName";
        java.lang.String SOME_STACK_VERSION = "1.0";
        java.lang.String MYSQL_JAR = "MYSQL_JAR";
        java.lang.String JAVA_HOME = "javaHome";
        java.lang.String JDK_NAME = "jdkName";
        java.lang.String JCE_NAME = "jceName";
        java.lang.String OJDBC_JAR_NAME = "OjdbcJarName";
        java.lang.String SERVER_DB_NAME = "ServerDBName";
        java.util.Map<org.apache.ambari.server.state.PropertyInfo, java.lang.String> notManagedHdfsPathMap = new java.util.HashMap<>();
        org.apache.ambari.server.state.PropertyInfo propertyInfo1 = new org.apache.ambari.server.state.PropertyInfo();
        propertyInfo1.setName("1");
        org.apache.ambari.server.state.PropertyInfo propertyInfo2 = new org.apache.ambari.server.state.PropertyInfo();
        propertyInfo2.setName("2");
        notManagedHdfsPathMap.put(propertyInfo1, "/tmp");
        notManagedHdfsPathMap.put(propertyInfo2, "/apps/falcon");
        java.util.Set<java.lang.String> notManagedHdfsPathSet = new java.util.HashSet<>(java.util.Arrays.asList("/tmp", "/apps/falcon"));
        com.google.gson.Gson gson = new com.google.gson.Gson();
        org.apache.ambari.server.actionmanager.ActionManager manager = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.actionmanager.ActionManager.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.configuration.Configuration configuration = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.configuration.Configuration.class);
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repositoryVersionEntity = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class);
        org.apache.ambari.server.state.ConfigHelper configHelper = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ConfigHelper.class);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.DesiredConfig> desiredConfigs = new java.util.HashMap<>();
        org.easymock.EasyMock.expect(cluster.getClusterName()).andReturn(clusterName);
        org.easymock.EasyMock.expect(cluster.getDesiredStackVersion()).andReturn(stackId);
        org.easymock.EasyMock.expect(cluster.getDesiredConfigs()).andReturn(desiredConfigs);
        org.easymock.EasyMock.expect(stackId.getStackName()).andReturn(SOME_STACK_NAME).anyTimes();
        org.easymock.EasyMock.expect(stackId.getStackVersion()).andReturn(SOME_STACK_VERSION).anyTimes();
        org.easymock.EasyMock.expect(configuration.getMySQLJarName()).andReturn(MYSQL_JAR);
        org.easymock.EasyMock.expect(configuration.getJavaHome()).andReturn(JAVA_HOME);
        org.easymock.EasyMock.expect(configuration.getJDKName()).andReturn(JDK_NAME);
        org.easymock.EasyMock.expect(configuration.getJCEName()).andReturn(JCE_NAME);
        org.easymock.EasyMock.expect(configuration.getOjdbcJarName()).andReturn(OJDBC_JAR_NAME);
        org.easymock.EasyMock.expect(configuration.getServerDBName()).andReturn(SERVER_DB_NAME);
        org.easymock.EasyMock.expect(configuration.getJavaVersion()).andReturn(8);
        org.easymock.EasyMock.expect(configuration.areHostsSysPrepped()).andReturn("true");
        org.easymock.EasyMock.expect(configuration.getGplLicenseAccepted()).andReturn(false);
        org.easymock.EasyMock.expect(configuration.getDatabaseConnectorNames()).andReturn(new java.util.HashMap<>()).anyTimes();
        org.easymock.EasyMock.expect(configuration.getPreviousDatabaseConnectorNames()).andReturn(new java.util.HashMap<>()).anyTimes();
        org.easymock.EasyMock.expect(repositoryVersionEntity.getVersion()).andReturn("1234").anyTimes();
        org.easymock.EasyMock.expect(repositoryVersionEntity.getStackId()).andReturn(stackId).anyTimes();
        org.easymock.EasyMock.expect(configHelper.getPropertiesWithPropertyType(stackId, org.apache.ambari.server.state.PropertyInfo.PropertyType.NOT_MANAGED_HDFS_PATH, cluster, desiredConfigs)).andReturn(notManagedHdfsPathMap);
        org.easymock.EasyMock.expect(configHelper.filterInvalidPropertyValues(notManagedHdfsPathMap, org.apache.ambari.server.agent.ExecutionCommand.KeyNames.NOT_MANAGED_HDFS_PATH_LIST)).andReturn(notManagedHdfsPathSet);
        org.easymock.EasyMock.replay(manager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, cluster, injector, stackId, configuration, repositoryVersionEntity, configHelper);
        org.apache.ambari.server.controller.AmbariManagementControllerImpl ambariManagementControllerImpl = org.easymock.EasyMock.createMockBuilder(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class).addMockedMethod("getRcaParameters").withConstructor(manager, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector).createNiceMock();
        org.easymock.EasyMock.expect(ambariManagementControllerImpl.getRcaParameters()).andReturn(new java.util.HashMap<>());
        org.easymock.EasyMock.replay(ambariManagementControllerImpl);
        java.lang.Class<?> amciClass = org.apache.ambari.server.controller.AmbariManagementControllerImpl.class;
        java.lang.reflect.Field f = amciClass.getDeclaredField("configs");
        f.setAccessible(true);
        f.set(ambariManagementControllerImpl, configuration);
        org.apache.ambari.server.controller.AmbariCustomCommandExecutionHelper helper = new org.apache.ambari.server.controller.AmbariCustomCommandExecutionHelper();
        java.lang.Class<?> helperClass = org.apache.ambari.server.controller.AmbariCustomCommandExecutionHelper.class;
        f = helperClass.getDeclaredField("managementController");
        f.setAccessible(true);
        f.set(helper, ambariManagementControllerImpl);
        f = helperClass.getDeclaredField("configs");
        f.setAccessible(true);
        f.set(helper, configuration);
        f = helperClass.getDeclaredField("configHelper");
        f.setAccessible(true);
        f.set(helper, configHelper);
        f = helperClass.getDeclaredField("gson");
        f.setAccessible(true);
        f.set(helper, gson);
        java.util.Map<java.lang.String, java.lang.String> defaultHostParams = helper.createDefaultHostParams(cluster, repositoryVersionEntity.getStackId());
        org.junit.Assert.assertEquals(16, defaultHostParams.size());
        org.junit.Assert.assertEquals(MYSQL_JAR, defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.DB_DRIVER_FILENAME));
        org.junit.Assert.assertEquals(SOME_STACK_NAME, defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.STACK_NAME));
        org.junit.Assert.assertEquals(SOME_STACK_VERSION, defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.STACK_VERSION));
        org.junit.Assert.assertEquals("true", defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.HOST_SYS_PREPPED));
        org.junit.Assert.assertEquals("8", defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.JAVA_VERSION));
        org.junit.Assert.assertNotNull(defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.NOT_MANAGED_HDFS_PATH_LIST));
        org.junit.Assert.assertTrue(defaultHostParams.get(org.apache.ambari.server.agent.ExecutionCommand.KeyNames.NOT_MANAGED_HDFS_PATH_LIST).contains("/tmp"));
    }

    @org.junit.Test
    public void testSynchronizeLdapUsersAndGroupsHookDisabled() throws java.lang.Exception {
        testSynchronizeLdapUsersAndGroups(false, false);
    }

    @org.junit.Test
    public void testSynchronizeLdapUsersAndGroupsHookEnabled() throws java.lang.Exception {
        testSynchronizeLdapUsersAndGroups(false, true);
    }

    @org.junit.Test
    public void testSynchronizeLdapUsersAndGroupsPostProcessExistingUsersHookDisabled() throws java.lang.Exception {
        testSynchronizeLdapUsersAndGroups(true, false);
    }

    @org.junit.Test
    public void testSynchronizeLdapUsersAndGroupsPostProcessExistingUsersHookEnabled() throws java.lang.Exception {
        testSynchronizeLdapUsersAndGroups(true, true);
    }

    private void testSynchronizeLdapUsersAndGroups(boolean postProcessExistingUsers, boolean postUserCreationHookEnabled) throws java.lang.Exception {
        boolean collectIgnoredUsers = postProcessExistingUsers && postUserCreationHookEnabled;
        java.util.Set<java.lang.String> userSet = new java.util.HashSet<>();
        userSet.add("user1");
        java.util.Set<java.lang.String> groupSet = new java.util.HashSet<>();
        groupSet.add("group1");
        com.google.inject.Injector injector = com.google.inject.Guice.createInjector(com.google.inject.util.Modules.override(new org.apache.ambari.server.orm.InMemoryDefaultTestModule()).with(new org.apache.ambari.server.controller.AmbariManagementControllerImplTest.MockModule()));
        org.apache.ambari.server.security.ldap.LdapBatchDto ldapBatchDto = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.security.ldap.LdapBatchDto.class);
        org.easymock.Capture<org.apache.ambari.server.security.ldap.LdapBatchDto> ldapBatchDtoCapture = org.easymock.EasyMock.newCapture();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator.synchronizeAllLdapUsers(org.easymock.EasyMock.capture(ldapBatchDtoCapture), org.easymock.EasyMock.eq(collectIgnoredUsers))).andReturn(ldapBatchDto);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator.synchronizeAllLdapGroups(org.easymock.EasyMock.capture(ldapBatchDtoCapture), org.easymock.EasyMock.eq(collectIgnoredUsers))).andReturn(ldapBatchDto);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator.synchronizeExistingLdapUsers(org.easymock.EasyMock.capture(ldapBatchDtoCapture), org.easymock.EasyMock.eq(collectIgnoredUsers))).andReturn(ldapBatchDto);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator.synchronizeExistingLdapGroups(org.easymock.EasyMock.capture(ldapBatchDtoCapture), org.easymock.EasyMock.eq(collectIgnoredUsers))).andReturn(ldapBatchDto);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator.synchronizeLdapUsers(org.easymock.EasyMock.eq(userSet), org.easymock.EasyMock.capture(ldapBatchDtoCapture), org.easymock.EasyMock.eq(collectIgnoredUsers))).andReturn(ldapBatchDto);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator.synchronizeLdapGroups(org.easymock.EasyMock.eq(groupSet), org.easymock.EasyMock.capture(ldapBatchDtoCapture), org.easymock.EasyMock.eq(collectIgnoredUsers))).andReturn(ldapBatchDto);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.users.processLdapSync(org.easymock.EasyMock.capture(ldapBatchDtoCapture));
        org.easymock.EasyMock.expectLastCall().anyTimes();
        org.easymock.EasyMock.replay(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.actionDBAccessor, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.users, ldapBatchDto);
        org.apache.ambari.server.configuration.Configuration configs = injector.getInstance(org.apache.ambari.server.configuration.Configuration.class);
        configs.setProperty(org.apache.ambari.server.configuration.Configuration.POST_USER_CREATION_HOOK_ENABLED.getKey(), java.lang.String.valueOf(postUserCreationHookEnabled));
        org.apache.ambari.server.controller.AmbariManagementControllerImpl controller = injector.getInstance(org.apache.ambari.server.controller.AmbariManagementControllerImpl.class);
        org.apache.ambari.server.controller.LdapSyncRequest userRequest = new org.apache.ambari.server.controller.LdapSyncRequest(org.apache.ambari.server.orm.entities.LdapSyncSpecEntity.SyncType.ALL, postProcessExistingUsers);
        org.apache.ambari.server.controller.LdapSyncRequest groupRequest = new org.apache.ambari.server.controller.LdapSyncRequest(org.apache.ambari.server.orm.entities.LdapSyncSpecEntity.SyncType.ALL, postProcessExistingUsers);
        controller.synchronizeLdapUsersAndGroups(userRequest, groupRequest);
        userRequest = new org.apache.ambari.server.controller.LdapSyncRequest(org.apache.ambari.server.orm.entities.LdapSyncSpecEntity.SyncType.EXISTING, postProcessExistingUsers);
        groupRequest = new org.apache.ambari.server.controller.LdapSyncRequest(org.apache.ambari.server.orm.entities.LdapSyncSpecEntity.SyncType.EXISTING, postProcessExistingUsers);
        controller.synchronizeLdapUsersAndGroups(userRequest, groupRequest);
        userRequest = new org.apache.ambari.server.controller.LdapSyncRequest(org.apache.ambari.server.orm.entities.LdapSyncSpecEntity.SyncType.SPECIFIC, userSet, postProcessExistingUsers);
        groupRequest = new org.apache.ambari.server.controller.LdapSyncRequest(org.apache.ambari.server.orm.entities.LdapSyncSpecEntity.SyncType.SPECIFIC, groupSet, postProcessExistingUsers);
        controller.synchronizeLdapUsersAndGroups(userRequest, groupRequest);
        org.easymock.EasyMock.verify(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.users, ldapBatchDto);
    }

    private void setAmbariMetaInfo(org.apache.ambari.server.api.services.AmbariMetaInfo metaInfo, org.apache.ambari.server.controller.AmbariManagementController controller) throws java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        java.lang.Class<?> c = controller.getClass();
        java.lang.reflect.Field f = c.getDeclaredField("ambariMetaInfo");
        f.setAccessible(true);
        f.set(controller, metaInfo);
    }

    private class MockModule implements com.google.inject.Module {
        @java.lang.Override
        public void configure(com.google.inject.Binder binder) {
            binder.bind(org.apache.ambari.server.security.ldap.AmbariLdapDataPopulator.class).toInstance(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ldapDataPopulator);
            binder.bind(org.apache.ambari.server.state.Clusters.class).toInstance(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters);
            binder.bind(org.apache.ambari.server.actionmanager.ActionDBAccessorImpl.class).toInstance(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.actionDBAccessor);
            binder.bind(org.apache.ambari.server.mpack.MpackManagerFactory.class).toInstance(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.mpack.MpackManagerFactory.class));
            binder.bind(org.apache.ambari.server.api.services.AmbariMetaInfo.class).toInstance(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo);
            binder.bind(org.apache.ambari.server.security.authorization.Users.class).toInstance(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.users);
            binder.bind(org.apache.ambari.server.controller.AmbariSessionManager.class).toInstance(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.sessionManager);
        }
    }

    private class NestedTestClass extends org.apache.ambari.server.controller.AmbariManagementControllerImpl {
        public NestedTestClass(org.apache.ambari.server.actionmanager.ActionManager actionManager, org.apache.ambari.server.state.Clusters clusters, com.google.inject.Injector injector, org.apache.ambari.server.state.stack.OsFamily osFamilyMock) throws java.lang.Exception {
            super(actionManager, clusters, injector);
            osFamily = osFamilyMock;
        }
    }

    @org.junit.Test
    public void testVerifyRepositories() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.apache.ambari.server.configuration.Configuration configuration = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.configuration.Configuration.class);
        java.lang.String[] suffices = new java.lang.String[]{ "/repodata/repomd.xml" };
        org.easymock.EasyMock.expect(configuration.getRepoValidationSuffixes("redhat6")).andReturn(suffices);
        org.easymock.EasyMock.replay(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, configuration);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.lang.Class<?> c = controller.getClass();
        java.lang.reflect.Field f = c.getDeclaredField("configs");
        f.setAccessible(true);
        f.set(controller, configuration);
        java.util.Set<org.apache.ambari.server.controller.RepositoryRequest> requests = new java.util.HashSet<>();
        org.apache.ambari.server.controller.RepositoryRequest request = new org.apache.ambari.server.controller.RepositoryRequest("stackName", "stackVersion", "redhat6", "repoId", "repo_name");
        request.setBaseUrl("file:///some/repo");
        requests.add(request);
        try {
            controller.verifyRepositories(requests);
            junit.framework.Assert.fail("IllegalArgumentException is expected");
        } catch (java.lang.IllegalArgumentException e) {
            junit.framework.Assert.assertEquals("Could not access base url . file:///some/repo/repodata/repomd.xml . ", e.getMessage());
        }
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, configuration);
    }

    @org.junit.Test
    public void testRegisterRackChange() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createStrictMock(com.google.inject.Injector.class);
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.apache.ambari.server.state.ServiceComponent serviceComponent = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponent.class);
        org.apache.ambari.server.state.ServiceComponentHost serviceComponentHost = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.ServiceComponentHost.class);
        org.apache.ambari.server.state.StackId stackId = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackId.class);
        org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture = org.easymock.EasyMock.newCapture();
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.constructorInit(injector, controllerCapture, org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.apache.ambari.server.state.RepositoryInfo dummyRepoInfo = new org.apache.ambari.server.state.RepositoryInfo();
        dummyRepoInfo.setRepoName("repo_name");
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("c1")).andReturn(cluster).anyTimes();
        org.easymock.EasyMock.expect(service.getName()).andReturn("HDFS").anyTimes();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponent> serviceComponents = new java.util.HashMap<>();
        serviceComponents.put("NAMENODE", serviceComponent);
        org.easymock.EasyMock.expect(service.getServiceComponents()).andReturn(serviceComponents).anyTimes();
        java.util.Map<java.lang.String, org.apache.ambari.server.state.ServiceComponentHost> schMap = new java.util.HashMap<>();
        schMap.put("host1", serviceComponentHost);
        org.easymock.EasyMock.expect(serviceComponent.getServiceComponentHosts()).andReturn(schMap).anyTimes();
        serviceComponentHost.setRestartRequired(true);
        java.util.Set<java.lang.String> services = new java.util.HashSet<>();
        services.add("HDFS");
        org.apache.ambari.server.state.ServiceInfo serviceInfo = new org.apache.ambari.server.state.ServiceInfo();
        serviceInfo.setRestartRequiredAfterRackChange(true);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getService(service)).andReturn(serviceInfo);
        java.util.Map<java.lang.String, org.apache.ambari.server.state.Service> serviceMap = new java.util.HashMap<>();
        serviceMap.put("HDFS", service);
        org.easymock.EasyMock.expect(cluster.getServices()).andReturn(serviceMap).anyTimes();
        org.easymock.EasyMock.replay(injector, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, serviceComponent, serviceComponentHost, stackId);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        controller.registerRackChange("c1");
        org.easymock.EasyMock.verify(injector, cluster, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, service, serviceComponent, serviceComponentHost, stackId);
    }

    @org.junit.Test
    public void testCreateClusterWithRepository() throws java.lang.Exception {
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.apache.ambari.server.orm.entities.RepositoryVersionEntity repoVersion = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.entities.RepositoryVersionEntity.class);
        org.apache.ambari.server.orm.dao.RepositoryVersionDAO repoVersionDAO = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.orm.dao.RepositoryVersionDAO.class);
        org.easymock.EasyMock.expect(repoVersionDAO.findByStackAndVersion(org.easymock.EasyMock.anyObject(org.apache.ambari.server.state.StackId.class), org.easymock.EasyMock.anyObject(java.lang.String.class))).andReturn(repoVersion).anyTimes();
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(injector.getInstance(com.google.gson.Gson.class)).andReturn(null);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.KerberosHelper.class)).andReturn(org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.KerberosHelper.class));
        org.apache.ambari.server.state.StackId stackId = new org.apache.ambari.server.state.StackId("HDP-2.1");
        org.apache.ambari.server.state.Cluster cluster = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Cluster.class);
        org.apache.ambari.server.state.Service service = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.Service.class);
        org.easymock.EasyMock.expect(service.getDesiredStackId()).andReturn(stackId).atLeastOnce();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters.getCluster("c1")).andReturn(cluster).atLeastOnce();
        org.apache.ambari.server.state.StackInfo stackInfo = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.state.StackInfo.class);
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getStack("HDP", "2.1")).andReturn(stackInfo).atLeastOnce();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getCommonWidgetsDescriptorFile()).andReturn(null).once();
        org.easymock.EasyMock.replay(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackInfo, cluster, service, repoVersionDAO, repoVersion);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        java.lang.Class<?> c = controller.getClass();
        java.lang.reflect.Field f = c.getDeclaredField("repositoryVersionDAO");
        f.setAccessible(true);
        f.set(controller, repoVersionDAO);
        java.util.Properties p = new java.util.Properties();
        p.setProperty("", "");
        org.apache.ambari.server.configuration.Configuration configuration = new org.apache.ambari.server.configuration.Configuration(p);
        f = c.getDeclaredField("configs");
        f.setAccessible(true);
        f.set(controller, configuration);
        org.apache.ambari.server.controller.ClusterRequest cr = new org.apache.ambari.server.controller.ClusterRequest(null, "c1", "HDP-2.1", null);
        controller.createCluster(cr);
        org.easymock.EasyMock.verify(injector, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, stackInfo, cluster, repoVersionDAO, repoVersion);
    }

    @org.junit.Test
    public void testRegisterMpacks() throws java.lang.Exception {
        org.apache.ambari.server.controller.MpackRequest mpackRequest = org.easymock.EasyMock.createNiceMock(org.apache.ambari.server.controller.MpackRequest.class);
        org.apache.ambari.server.controller.RequestStatusResponse response = new org.apache.ambari.server.controller.RequestStatusResponse(new java.lang.Long(201));
        org.apache.ambari.server.state.Mpack mpack = new org.apache.ambari.server.state.Mpack();
        mpack.setResourceId(((long) (100)));
        mpack.setModules(new java.util.ArrayList<org.apache.ambari.server.state.Module>());
        mpack.setPrerequisites(new java.util.HashMap<java.lang.String, java.lang.String>());
        mpack.setRegistryId(new java.lang.Long(100));
        mpack.setVersion("3.0");
        mpack.setMpackUri("abc.tar.gz");
        mpack.setDescription("Test mpack");
        mpack.setName("testMpack");
        org.apache.ambari.server.controller.MpackResponse mpackResponse = new org.apache.ambari.server.controller.MpackResponse(mpack);
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.registerMpack(mpackRequest)).andReturn(mpackResponse);
        org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.init();
        org.easymock.EasyMock.expectLastCall();
        org.easymock.EasyMock.replay(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, injector);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        junit.framework.Assert.assertEquals(mpackResponse, controller.registerMpack(mpackRequest));
    }

    @org.junit.Test
    public void testGetPacklets() throws java.lang.Exception {
        java.lang.Long mpackId = new java.lang.Long(100);
        java.util.ArrayList<org.apache.ambari.server.state.Module> packletArrayList = new java.util.ArrayList<>();
        org.apache.ambari.server.state.Module samplePacklet = new org.apache.ambari.server.state.Module();
        com.google.inject.Injector injector = org.easymock.EasyMock.createNiceMock(com.google.inject.Injector.class);
        samplePacklet.setVersion("3.0.0");
        samplePacklet.setName("NIFI");
        samplePacklet.setDefinition("nifi.tar.gz");
        packletArrayList.add(samplePacklet);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(null).atLeastOnce();
        org.easymock.EasyMock.expect(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo.getModules(mpackId)).andReturn(packletArrayList).atLeastOnce();
        org.easymock.EasyMock.replay(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, injector);
        org.apache.ambari.server.controller.AmbariManagementController controller = new org.apache.ambari.server.controller.AmbariManagementControllerImpl(null, org.apache.ambari.server.controller.AmbariManagementControllerImplTest.clusters, injector);
        setAmbariMetaInfo(org.apache.ambari.server.controller.AmbariManagementControllerImplTest.ambariMetaInfo, controller);
        junit.framework.Assert.assertEquals(packletArrayList, controller.getModules(mpackId));
    }

    public static void constructorInit(com.google.inject.Injector injector, org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture, com.google.gson.Gson gson, org.apache.ambari.server.controller.MaintenanceStateHelper maintenanceStateHelper, org.apache.ambari.server.controller.KerberosHelper kerberosHelper, com.google.inject.Provider<org.apache.ambari.server.agent.stomp.MetadataHolder> m_metadataHolder, com.google.inject.Provider<org.apache.ambari.server.agent.stomp.AgentConfigsHolder> m_agentConfigsHolder) {
        injector.injectMembers(org.easymock.EasyMock.capture(controllerCapture));
        org.easymock.EasyMock.expect(injector.getInstance(com.google.gson.Gson.class)).andReturn(gson);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(maintenanceStateHelper);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.KerberosHelper.class)).andReturn(kerberosHelper);
    }

    public static void constructorInit(com.google.inject.Injector injector, org.easymock.Capture<org.apache.ambari.server.controller.AmbariManagementController> controllerCapture, org.apache.ambari.server.controller.KerberosHelper kerberosHelper) {
        injector.injectMembers(org.easymock.EasyMock.capture(controllerCapture));
        org.easymock.EasyMock.expect(injector.getInstance(com.google.gson.Gson.class)).andReturn(null);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.MaintenanceStateHelper.class)).andReturn(null);
        org.easymock.EasyMock.expect(injector.getInstance(org.apache.ambari.server.controller.KerberosHelper.class)).andReturn(kerberosHelper);
    }
}
