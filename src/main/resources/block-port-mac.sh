#!/usr/bin/env bash

(sudo pfctl -sr 2>/dev/null; echo "block drop quick on lo0 proto tcp from any to any port = $1") | sudo pfctl -ef - 2>/dev/null

sudo pfctl -sr 2>/dev/null