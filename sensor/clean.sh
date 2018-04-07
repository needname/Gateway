#!/bin/bash

sudo kill -9 $(ps -ax|grep "./sensor1"| grep -v "grep"|awk '{print $1}')

sudo kill -9 $(ps -ax|grep "./sensor2"| grep -v "grep"|awk '{print $1}')

