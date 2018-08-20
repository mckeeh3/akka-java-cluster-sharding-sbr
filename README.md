## Akka Java Cluster Example

### Akka Split Brain Resolver (SBR) 

This project provides an example of running an Akka cluster with SBR enabled. The example code uses cluster sharding to run a collection of actors across an Akka cluster. 

To see an example of SBR in action, follow the steps below to start up a cluster of 2 or more nodes. Once the cluster is up and running stop one of the nodes by killing the node JVM. On Linux systems or a Mac use the kill -9 PID command to terminate one of the cluster node JVMs. 

With SBR, when one or more nodes abruptly leaves a cluster the remaining cluster nodes will down the unreachable node or nodes. SBR logs node downing events in the log file of the 
[cluster leader](https://doc.akka.io/docs/akka/current/common/cluster.html#leader)
node. Search the leader log file for the term "SBR".

See the 
[SBR documentation](https://developer.lightbend.com/docs/akka-commercial-addons/current/split-brain-resolver.html)
for information on the mechanics of SBR and instructions for enabling it.

### Installation and Running

~~~~bash
git clone https://github.com/mckeeh3/akka-java-cluster-sharding.git
cd akka-java-cluster-sharding
mvn compile
~~~~
The following Maven command runs a signle JVM with 3 Akka actor systems on ports 2551, 2552, and a radmonly selected port.
~~~~bash
mvn exec:java
~~~~
To run on specific ports use the following `-D` option for passing in command line arguements.
~~~~bash
mvn exec:java -Dexec.args="2551"
~~~~
The default no arguments is equilevalant to the following.
~~~~bash
mvn exec:java -Dexec.args="2551 2552 0"
~~~~
A common way to run tests is to start single JVMs in multiple command windows. This simulates running a multi-node Akka cluster.
For example, run the following 4 commands in 4 command windows.
~~~~bash
mvn exec:java -Dexec.args="2551" > /tmp/$(basename $PWD)-1.log
~~~~
~~~~bash
mvn exec:java -Dexec.args="2552" > /tmp/$(basename $PWD)-2.log
~~~~
~~~~bash
mvn exec:java -Dexec.args="0" > /tmp/$(basename $PWD)-3.log
~~~~
~~~~bash
mvn exec:java -Dexec.args="0" > /tmp/$(basename $PWD)-4.log
~~~~
This runs a 4 node Akka cluster starting 2 nodes on ports 2551 and 2552, which are the cluster seed nodes as configured and the `application.conf` file.
And 2 nodes on randomly selected port numbers.
The optional redirect `> /tmp/$(basename $PWD)-4.log` is an example for pushing the log output to filenames based on the project direcctory name.

For convenience, in a Linux command shell define the following aliases.

~~~~bash
alias p1='cd ~/akka-java/akka-java-cluster'
alias p2='cd ~/akka-java/akka-java-cluster-aware'
alias p3='cd ~/akka-java/akka-java-cluster-singleton'
alias p4='cd ~/akka-java/akka-java-cluster-sharding'
alias p5='cd ~/akka-java/akka-java-cluster-persistence'
alias p6='cd ~/akka-java/akka-java-cluster-persistence-query'

alias m1='clear ; mvn exec:java -Dexec.args="2551" > /tmp/$(basename $PWD)-1.log'
alias m2='clear ; mvn exec:java -Dexec.args="2552" > /tmp/$(basename $PWD)-2.log'
alias m3='clear ; mvn exec:java -Dexec.args="0" > /tmp/$(basename $PWD)-3.log'
alias m4='clear ; mvn exec:java -Dexec.args="0" > /tmp/$(basename $PWD)-4.log'
~~~~

The p1-6 alias commands are shortcuts for cd'ing into one of the six project directories.
The m1-4 alias commands start and Akka node with the appropriate port. Stdout is also redirected to the /tmp directory.

### Description

TODO
