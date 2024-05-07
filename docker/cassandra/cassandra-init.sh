until $(docker exec d1r1n1 nodetool status | grep "UN" > /dev/null); do
  echo "Waiting for cassandra-node-1...";
  sleep 1;
done
