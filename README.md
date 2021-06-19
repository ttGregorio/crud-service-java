Este projeto tem como objetivo exemplificar a criação de um crud dentro da stack spring cloud utilizando mongodb para banco de dados, junit para testes unitários, cucumber para testes integrados, jacoco para análise de cobertura de código e jenkins para automação de geração e deploy de imagens docker


Jenkins
Utilizei uma instância ec2 da amazon com amazon linux

1 - Atualize o repositório
sudo yum update -y

2 - Instale o docker:

sudo amazon-linux-extras install docker
<br>
sudo yum install docker
<br>
sudo service docker start
<br>
sudo usermod -a -G docker ec2-user
<br>
sudo groupadd docker
<br>
sudo usermod -aG docker ${USER}
<br>
sudo systemctl restart docker
<br>
sudo chmod 666 /var/run/docker.sock
 
 
 3 - Instale o git
 sudo yum install git

4 - Instale o jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo     https://pkg.jenkins.io/red                                                                                                             hat-stable/jenkins.repo
<br>
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
<br>
sudo yum upgrade
<br>
sudo yum install jenkins java-1.8.0-openjdk-devel -y
<br>
sudo systemctl daemon-reload
<br>
sudo systemctl start jenkins
<br>
sudo systemctl status jenkins
