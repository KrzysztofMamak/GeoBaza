package com.mamak.geobaza.data.repository

import com.mamak.geobaza.data.db.dao.ProjectDao
import com.mamak.geobaza.data.db.entity.ProjectEntity
import io.reactivex.Observable

class ProjectLocalRepo(private val projectDao: ProjectDao) {
    fun getAllProjects(): Observable<List<ProjectEntity>> {
        return Observable.fromCallable {
            projectDao.getAllProjects()
        }
    }

    fun addProjects(projects: List<ProjectEntity>) {
        projectDao.insert(projects)
    }
}