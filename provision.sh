#!/usr/bin/env bash

echo "Installing Java dependencies...."
apt-get update > /dev/null 2>&1
apt-get install -y python-software-properties > /dev/null 2>&1
add-apt-repository -y ppa:webupd8team/java > /dev/null 2>&1
apt-get update > /dev/null 2&1
echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
apt-get -y install oracle-java7-installer > /dev/null 2>&1


echo "Installing curl...."
apt-get -y install curl > /dev/null 2>&1

echo "Installing Leiningen..."
sudo -u vagrant -H sh -c "mkdir ~/bin; wget https://raw.github.com/technomancy/leiningen/stable/bin/lein -O ~/bin/lein"
sudo -u vagrant -H sh -c "chmod a+x ~/bin/lein; export PATH=$PATH:~/bin; cd ~/bin; ./lein"

echo "Installing git..."
apt-get install -y git > /dev/null 2>&1

sudo -u vagrant -H sh -c "git config --global alias.co checkout"
sudo -u vagrant -H sh -c "git config --global alias.br branch"
sudo -u vagrant -H sh -c "git config --global alias.ci commit"
sudo -u vagrant -H sh -c "git config --global alias.st status"
sudo -u vagrant -H sh -c "git config --global alias.last 'log -1 HEAD'"

echo "Copying .bash_aliases ..."
sudo cp /vagrant/.bash_aliases /home/vagrant/



