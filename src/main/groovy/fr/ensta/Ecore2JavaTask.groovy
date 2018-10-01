package fr.ensta

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Ecore2JavaTask extends DefaultTask {
    String description 	= 'Generate Java source code from ECORE model.'
    File genModelPath
    File outputDir

    @TaskAction
    def generateJava() {
        Ecore2Java e2j = new Ecore2Java(getLogger(), outputDir);
        e2j.generate(genModelPath)
    }
}
