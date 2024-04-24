package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

/*
 * this class handle the business layer of our application
 * it is our model layer
 * 
 * @author Paul Technology
 */
public class ProjectService {
	//private static final String db_file = "projects_schema.sql";
	
	//creating new instance of a data access object
	private ProjectDao projectDao  = new ProjectDao();
	
	//method that calls the DAO class to insert a project row.
	public Project addProject(Project project) {
		return projectDao.insertData(project);
	}

}
