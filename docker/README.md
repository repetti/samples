# Docker (docker.com)
an operating-system-level virtualization on Linux.

# Install (Ubuntu)
```
sudo sh -c "echo deb https://get.docker.io/ubuntu docker main\ > /etc/apt/sources.list.d/docker.list"
sudo apt-get update
sudo apt-get install lxc-docker
```

* Start ubuntu container with bash
``` 
sudo docker run -i -t ubuntu /bin/bash
```

* Add user to docker group (https://docs.docker.com/installation/debian/)
```
groupadd docker
sudo gpasswd -a ${USER} docker
service docker restart
```

## Images
* Hadoop https://github.com/sequenceiq/hadoop-docker
* Spark https://github.com/sequenceiq/docker-spark
* OpenVPN 
 https://github.com/jpetazzo/dockvpn
 https://github.com/kylemanna/docker-openvpn

### See
* https://docker.com/
* https://en.wikipedia.org/wiki/Docker_%28software%29
* http://ganges.usc.edu/pgroupW/images/a/a9/Serializarion_Framework.pdf serialization framework overview


