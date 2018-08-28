#!/usr/bin/env bash

sudo iptables -D OUTPUT -p tcp --destination-port $1 -j DROP
sudo iptables -D INPUT -p tcp --destination-port $1 -j DROP
sudo iptables -S
