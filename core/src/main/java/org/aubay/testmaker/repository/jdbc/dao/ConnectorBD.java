package org.jorge2m.testmaker.repository.jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectorBD {
	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
