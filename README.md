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

5 - Adicione no Jenkins os plugins de Docker, Docker pipeline e Amazon ECR acessando Gerenciar Jenkins, Gerenciar Plugins, Disponíveis

6 - Configure as credenciais globais em Gerenciar Jenkins, Manage Credentials, clique em Jenkins, Global Credentials, e adicione uma nova credencial de nome dockerhub*, com seu usuário e senha do docker hub

7 - Caso utilize o ECR da Amazon, crie uma variável global com uma chave e um secret criados no menu IAM de nome aws_credentials*, clicando em um usuário válido, aba credenciais de segurança, opção credenciais de acesso.

8 - Acesse Global Tool Configuration, clique em maven, adicione uma configuração auto instalada myMaven. faça o mesmo procedimento para Docker, com usuário myDocker

9 - Crie uma nova pipeline, apontando em General, Pipeline from SCM, SCM Git, apontando para este repositório


*Os nomes não precisam ser necessariamente esses. Você pode escolher o nome que quiser e posteriormente ajustar no Jenkinsfile, que foi comitado com esses nomes.
