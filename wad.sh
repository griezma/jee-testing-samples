docker stop wildfly

docker run -d --name wildfly-wad \
    -v $(pwd)/build/libs/:/opt/jboss/wildfly/standalone/deployments/\
    -p 8080:8080 -p 9990:9990 my/wildfly

gradle -t war
