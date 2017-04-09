FROM jboss/wildfly:10.1.0.Final

COPY helloworld-rs.war $JBOSS_HOME/standalone/deployments/

COPY standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml

COPY drivers/postgresql $JBOSS_HOME/modules/system/layers/base/org/postgresql

USER root

RUN chown -R jboss:jboss $JBOSS_HOME

USER jboss
