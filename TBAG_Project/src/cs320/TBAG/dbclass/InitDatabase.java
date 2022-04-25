package cs320.TBAG.dbclass;

import cs320.TBAG.database.DatabaseProvider;
import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.database.FakeDatabase;

public class InitDatabase {
	public static void init(int database) {
		if (database == 0) {
			DatabaseProvider.setInstance(new FakeDatabase());
		} else if (database == 1) {
			DatabaseProvider.setInstance(new DerbyDatabase());
		} else {
			throw new IllegalArgumentException("Invalid Database Type");
		}
	}
}