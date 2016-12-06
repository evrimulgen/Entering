package com.mics.main;

import java.io.IOException;

import com.mics.scp.StoreSCP;

public class Main {

	public static void main(String[] args) throws IOException {
		StoreSCP storeSCP = new StoreSCP();
		storeSCP.startSCPServer();
	}

}
