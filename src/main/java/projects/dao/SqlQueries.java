package projects.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

/*
 * this class will handle all queries
 * the class will most communicate with the Db
 * and pass it to the Dao class
 * 
 * @author Paul Technology
 */

@SuppressWarnings("unused")
public class SqlQueries extends DaoBase{
	
	//private variables
	
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "projects";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";
		
	
	
	/* This method that insert data
	 * 
	 * The method takes a project as string and insert into the Db
	 * The method will return 1 when insert is successful
	 */
	public Project insertData (Project project) {
		
		String sql1 = 
				"INSERT INTO " + PROJECT_TABLE + ""
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
				
		//Fetch database connection and prepare statement object
		
		try(Connection conn = DbConnection.connectDB()) {
			startTransaction(conn);
		
		//prepared statement ps
		try(PreparedStatement ps = conn.prepareStatement(sql1)){
			setParameter(ps, 1, project.getProjectName(), String.class);
			setParameter(ps, 2, project.getEstimatedHours(), BigDecimal.class);
			setParameter(ps, 3, project.getActualHours(), BigDecimal.class);
			setParameter(ps, 4, project.getDifficulty(), Integer.class);
			setParameter(ps, 5, project.getNotes(), String.class);
			
			//execute query
			ps.executeUpdate();
			
			//get last created project Id
			Integer projectId = getLastInsertId(conn, PROJECT_TABLE);
			
			//commit
			commitTransaction(conn);
			
			project.setProjectId(projectId);
			
			return project;
			
		} catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
		} catch(SQLException e) {
			throw new DbException(e);
		}
		
		
		
	}

}
