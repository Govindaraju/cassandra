package com.gimpact.cassandra.client;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;

public class MetaDataFetchClient {

	private Cluster cluster;
	
	public void connectToCassandra(final String node){
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata meta = cluster.getMetadata();
		System.out.println("Connected to the cluster "+meta.getClusterName());
	}
	
	public void close(){
		cluster.shutdown();
	}
	
	public static void main(String args[]){
		MetaDataFetchClient mdc = new MetaDataFetchClient();
		mdc.connectToCassandra("127.0.0.1");
		mdc.close();
	}
}
