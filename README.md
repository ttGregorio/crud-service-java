Este projeto tem como objetivo exemplificar a criação de um crud dentro da stack spring cloud utilizando mongodb para banco de dados, junit para testes unitários, cucumber para testes integrados, jacoco para análise de cobertura de código e jenkins para automação de geração e deploy de imagens docker


Jenkins
Utilizei uma instância ec2 da amazon com amazon linux

1 - Atualize o repositório
sudo yum update -y

2 - Instale o docker
sudo amazon-linux-extras install docker
sudo yum install docker
sudo service docker start
 sudo usermod -a -G docker ec2-user
 sudo groupadd docker
 sudo usermod -aG docker ${USER}
 sudo systemctl restart docker
 sudo chmod 666 /var/run/docker.sock
 
 
 3 - Instale o git
 sudo yum install git

4 - Instale o jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo     https://pkg.jenkins.io/red                                                                                                             hat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
sudo yum upgrade
sudo yum install jenkins java-1.8.0-openjdk-devel -y
sudo systemctl daemon-reload
sudo systemctl start jenkins
sudo systemctl status jenkins
