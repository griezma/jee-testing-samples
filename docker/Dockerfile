FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh -u admin -p admin --silent
RUN /opt/jboss/wildfly/bin/add-user.sh -a -u clientapp -p secret -g guest --silent
ENV DEPLOYMENT_DIR /opt/jboss/wildfly/standalone/deployments/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c", "standalone-full.xml" ]
