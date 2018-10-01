package fr.ensta

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class Ecore2JavaPluginTest {
    @Test
    public void ecore2javaPluginAddsEcore2JavaTaskToProject() {
        Project project = ProjectBuilder.builder().build()

        //project.pluginsManager.apply 'org.ensta.ecore2java'

        //assertTrue(project.tasks.ecore2java instanceof Ecore2JavaTask)
    }
}
