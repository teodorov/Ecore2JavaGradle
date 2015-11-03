# Ecore2JavaGradle


all ecore models should be placed in a directory named "model"
for each ecore model an associated genmodel should be created.

add the following to the build.gradle

    buildscript {
        repositories {
            maven {
                url "https://dl.bintray.com/ciprian-teodorov/CipT-artefacts/"
            }
        }
        dependencies {
            classpath group: 'org.ensta', name: 'ecore2java',
                      version: '1.0'
        }
    }
    apply plugin: 'org.ensta.ecore2java'