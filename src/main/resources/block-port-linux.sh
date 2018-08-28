#!/usr/bin/env bash

sudo iptables -A OUTPUT -p tcp --destination-port $1 -j DROP
sudo iptables -A INPUT -p tcp --destination-port $1 -j DROP
sudo iptables -S
