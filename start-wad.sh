[ "$(docker inspect wildfly | grep Running)" ] && docker stop wildfly-wad

# is using same ports therefore stopping it
docker stop wildfly

DEPLOY_DIR=/opt/jboss/wildfly/standalone/deployments/

mkdir -p wad-deploy

docker run --rm -d --name wildfly-wad \
    -v $(pwd)/wad-deploy/:$DEPLOY_DIR \
    -p 8080:8080 -p 9990:9990 my/wildfly

gradle -t wad

echo Docker container "wildfly-wad" has been created.
echo Appliation: http://localhost:8080
echo Administration: http://localhost:9990 '(admin/admin)'
echo Client login: clientapp/secret
echo Stop container: 'docker stop wildfly-wad'
