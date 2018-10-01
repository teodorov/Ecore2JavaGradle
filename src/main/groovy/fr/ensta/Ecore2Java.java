package fr.ensta;

import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Map;

//https://git-st.inf.tu-dresden.de/afehn/
// ecore2java-gradle/blob/master/src/main/kotlin/ecore2java/Ecore2JavaGenerator.kt

public class Ecore2Java {
    File outputDirectory;
    Logger logger;

    Monitor monitor;

    public static void main(String args[]) {
        Ecore2Java e2j = new Ecore2Java(Logging.getLogger(Ecore2Java.class), new File("./fcr"));
        e2j.generate(new File("/Users/ciprian/Playfield/repositories/fiacre-language/FiacreLanguage/model/fiacre.genmodel"));
    }

    public Ecore2Java(Logger logger, File outputDirectory) {
        init();
        this.logger = logger;
        this.monitor = new Monitor(logger);
        this.outputDirectory = outputDirectory;

    }

    boolean isInitialized = false;
    void init() {
        if (isInitialized) return;
        EPackage.Registry.INSTANCE.put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
        EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
        GeneratorAdapterFactory.Descriptor.Registry.INSTANCE.addDescriptor
                (GenModelPackage.eNS_URI, GenModelGeneratorAdapterFactory.DESCRIPTOR);
        Map<String, Object> map = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        map.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new EcoreResourceFactoryImpl());

        isInitialized = true;
    }

    public void generate(File genModelFile) {
        PrintStream oldErr = System.err;
        try {
            System.setErr(new PrintStream(new ByteArrayOutputStream()));
        } catch (Throwable e) {
            oldErr = null;
        }

        try {
            GenModel genModel = getGenModel(genModelFile);

            Generator generator = new Generator() {
                @Override
                public JControlModel getJControlModel() {
                    return new JControlModel();
                }
            };

            generator.setInput(genModel);
            generator.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, monitor);
        } finally {
            if (oldErr != null) {
                System.setErr(oldErr);
            }
        }
    }

    private GenModel getGenModel(File file) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getURIConverter().getURIMap().put(URI.createURI("platform:/resource/"), URI.createFileURI(outputDirectory.getAbsolutePath() + File.separator));
        Resource res = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);


        GenModel genModel = (GenModel) res.getContents().get(0);
        genModel.setModelDirectory(".");
        genModel.setCanGenerate(true);
        genModel.reconcile();

        return genModel;
    }


    private class Monitor extends BasicMonitor {
        Logger logger;

        public Monitor(Logger logger) {
            this.logger = logger;
        }

        private void log(String msg) {
            logger.info(msg);
        }

        @Override
        public void beginTask(String name, int totalWork) {
            if (name != null && name.length() > 0) {
                log(">>> " + name);
            }
        }

        @Override
        public void setTaskName(String name) {
            if (name != null && name.length() > 0) {
                log("<>> " + name);
            }
        }

        @Override
        public void subTask(String name) {
            if (name != null && name.length() > 0) {
                log(">>  "+ name);
            }
        }

        @Override
        public void setBlocked(Diagnostic reason) {
            super.setBlocked(reason);
            log("#>  " + reason.getMessage());
        }

        @Override
        public void clearBlocked() {
            log("=>  " + getBlockedReason().getMessage());
            super.clearBlocked();

        }
    }
}


