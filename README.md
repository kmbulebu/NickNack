[![Build Status](https://drone.io/github.com/kmbulebu/NickNack/status.png)](https://drone.io/github.com/kmbulebu/NickNack/latest)
#NickNack 
NickNack is an event driven automation engine for the "Internet of Things." Most homes are now equipped with at least one internet connected TV, appliance, security system, stereo receiver, or other device. Additionally, we find Home Theater PC software, Internet services, such as email and weather updates. Nick Nack aims to offer an easy way to create meaningful connections between these providers and more.

A list of included providers is available [here] (./PROVIDERS.md).

## Prerequisites
* Java JRE 7 or higher

## Download
* [Gzip tarball (Linux, OSX, etc)] (http://repo1.maven.org/maven2/com/github/kmbulebu/nicknack/nicknack-server-assembly/0.0.5/nicknack-server-assembly-0.0.5-distribution.tar.gz)
* [Zip (Windows)] (http://repo1.maven.org/maven2/com/github/kmbulebu/nicknack/nicknack-server-assembly/0.0.5/nicknack-server-assembly-0.0.5-distribution.zip)

## Installing
### Linux 
1. Download nicknack distribution. `wget -Onicknack.tgz "http://repo1.maven.org/maven2/com/github/kmbulebu/nicknack/nicknack-server-assembly/0.0.5/nicknack-server-assembly-0.0.5-distribution.tar.gz`
2. Unpack. `tar xzf nicknack.tgz`
3. Start. `nicknack/bin/nicknack.sh start`
4. Visit (http://127.0.0.1:8085/).

## Configuring providers
Web based configuration is not yet available. Until then, you can configure NickNack providers by editing the `nicknack/conf/nicknack_config.xml` file. 
