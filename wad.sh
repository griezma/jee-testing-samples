[ "$(docker inspect wildfly-wad | grep Running)" ] && docker stop wildfly-wad

DEPLOY_DIR=/opt/jboss/wildfly/standalone/deployments/

mkdir -p wad-deploy

docker run --rm -d --name wildfly-wad \
    -v $(pwd)/wad-deploy/:$DEPLOY_DIR \
    -p 8080:8080 -p 9990:9990 my/wildfly

gradle -t wad
