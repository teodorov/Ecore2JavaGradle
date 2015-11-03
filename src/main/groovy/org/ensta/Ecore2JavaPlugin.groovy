package org.ensta

import org.gradle.api.Project
import org.gradle.api.Plugin

class Ecore2JavaPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('ecore2java', type: Ecore2JavaTask)
    }
}
