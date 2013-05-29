package com.gimpact.cassandra.hector.oldstyle;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * Class to create the following 
 *   	1. schema named songsdb 
 * 		2. tables called songs and playlists.
 * This class uses the Hector APIs and creates them using the pre
 * CQL 3 syntax
 */

public class PlayList {

	private static final String clusterName = "test-cluster";
	private static final String hostAndPort = "localhost:9160";
	private static final String schemaAKAKeySpace = "songsdb";
	private static final String tableAKACF1 = "songs";
	private static final String tableAKACF2 = "playlists";
	private static Cluster cluster;
	private static KeyspaceDefinition keyspaceDefintion;

	private void setupDatabase() {
		cluster = HFactory.getOrCreateCluster(clusterName, hostAndPort); 		// 1. Create or get the handle to the cluster
		cleanUp();														 		// 2. Drop the keyspace by name songsdb if exists already.
		keyspaceDefintion = HFactory
				.createKeyspaceDefinition(schemaAKAKeySpace);			 		// 3. Create a keyspace named songsdb (equivalent to schema in RDBMS)
		
		ColumnFamilyDefinition songsTable = HFactory
				.createColumnFamilyDefinition(schemaAKAKeySpace, tableAKACF1);  // 4. Create two tables named songs and playlists in songsdb keyspace
		ColumnFamilyDefinition playlistsTable = HFactory
				.createColumnFamilyDefinition(schemaAKAKeySpace, tableAKACF2);
		
		cluster.addKeyspace(keyspaceDefintion);									// Defintions happen at the top, and the actual creation happens here
		cluster.addColumnFamily(songsTable);
		cluster.addColumnFamily(playlistsTable);

	}

	private void cleanUp() {
		if (cluster.describeKeyspace(schemaAKAKeySpace) != null) {
			cluster.dropKeyspace(schemaAKAKeySpace);
		}
		// cluster.dropColumnFamily(schemaAKAKeySpace, tableAKACF1);			// This is not required as the keyspace itself gets droped through the above statement.
		// cluster.dropColumnFamily(schemaAKAKeySpace, tableAKACF2);
	}

	public static void main(String args[]) {
		PlayList playList = new PlayList();
		playList.setupDatabase();
		System.out.println("Cluster in use ... " + cluster.getName());
		System.out.println("Schema in use "
				+ cluster.describeKeyspace(schemaAKAKeySpace));
		System.out.println("Created Tables "
				+ cluster.describeKeyspace(schemaAKAKeySpace).getCfDefs());
	}
}
