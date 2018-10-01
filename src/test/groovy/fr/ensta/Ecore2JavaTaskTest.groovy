package fr.ensta

import fr.ensta.Ecore2JavaTask
import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class Ecore2JavaTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('ecore2java', type: Ecore2JavaTask)
        assertTrue(task instanceof Ecore2JavaTask)
    }
}
