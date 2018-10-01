# Ecore2JavaGradle


Generate the java source code from an ecore model using the .genmodel file.
The task takes the genmodel file as input (**genModelPath** argument), and produces the code in the **outputDir**.

add the following to the build.gradle

    buildscript {
        repositories {
            maven { 
                url 'https://dl.bintray.com/ciprian-teodorov/CipT-artefacts/' } }
        dependencies {
            classpath group: 'fr.ensta', name: 'ecore2java', version: '1.5' } }

    apply plugin: 'fr.ensta.ecore2java'
    
    ecore2java {
        genModelPath = file('model/XXX.genmodel')
        outputDir = file('generated')
    }
