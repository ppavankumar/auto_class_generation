import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class CreateBaseClasses {
    private static final String REPLACE_PACKAGE = "<REPLACE_PACKAGE>";
    private static final String REPLACE_MODEL = "<REPLACE_MODEL>";
    private static final String JAVA_FILE_EXT = ".java";

    public static void main(String[] args) throws IOException {
        String modelClassName = "User";
        String packageName = "org.users";
        String modelPackageName = packageName + ".models";
        String controllerPackageName = packageName + ".controller";
        String servicePackageName = packageName + ".service";
        String serviceImplPackageName = packageName + ".service.impl";
        List<String> controllerAndServiceClasses = List.of(modelClassName + "Controller", modelClassName + "Service", modelClassName + "ServiceImpl");

        String modelsContent = modelsContent(modelClassName, modelPackageName);
        String controllerContent = controllerContent(modelClassName, modelPackageName);
        String serviceContent = serviceInterfaceContent(modelClassName, modelPackageName);
        String serviceImplContent = serviceImplClassContent(modelClassName, modelPackageName);

        Path modelPath = Files.createDirectories(Path.of(modelPackageName.replace('.', File.separatorChar)));
        Path controllerPath = Files.createDirectories(Path.of(controllerPackageName.replace('.', File.separatorChar)));
        Path servicePath = Files.createDirectories(Path.of(servicePackageName.replace('.', File.separatorChar)));
        Path serviceImplPath = Files.createDirectories(Path.of(serviceImplPackageName.replace('.', File.separatorChar)));

        Path modelClassFilePath = Files.createFile(Path.of(modelPath.toString() + File.separator + modelClassName + JAVA_FILE_EXT));
        Path controllerClassFilePath = Files.createFile(Path.of(controllerPath.toString() + File.separator + controllerAndServiceClasses.get(0) + JAVA_FILE_EXT));
        Path serviceClassFilePath = Files.createFile(Path.of(servicePath.toString() + File.separator + controllerAndServiceClasses.get(1) + JAVA_FILE_EXT));
        Path serviceImplClassFilePath = Files.createFile(Path.of(serviceImplPath.toString() + File.separator + controllerAndServiceClasses.get(2) + JAVA_FILE_EXT));

        Files.write(modelClassFilePath, modelsContent.getBytes(), StandardOpenOption.APPEND);
        Files.write(controllerClassFilePath, controllerContent.getBytes(), StandardOpenOption.APPEND);
        Files.write(serviceClassFilePath, serviceContent.getBytes(), StandardOpenOption.APPEND);
        Files.write(serviceImplClassFilePath, serviceImplContent.getBytes(), StandardOpenOption.APPEND);

//        System.out.println(controllerContent);
//        System.out.println(serviceContent);
//        System.out.println(serviceImplContent);
    }

    public static String modelsContent(String modelClassName, String modelPackageName) {
        String modelsFileContent = readFileContent("ModelClassContent.txt");
        modelsFileContent = modelsFileContent.replace(REPLACE_MODEL, modelClassName);
        modelsFileContent = modelsFileContent.replace(REPLACE_PACKAGE, modelPackageName);
        return modelsFileContent;
    }

    public static String controllerContent(String modelClassName, String modelPackageName) {
        String controllerFileContent = readFileContent("ControllerClassContent.txt");
        controllerFileContent = controllerFileContent.replace(REPLACE_MODEL, modelClassName);
        controllerFileContent = controllerFileContent.replace(REPLACE_PACKAGE, modelPackageName);
        return controllerFileContent;
    }

    public static String serviceInterfaceContent(String modelClassName, String modelPackageName) {
        String serviceInterfaceContent = readFileContent("ServiceInterfaceContent.txt");
        serviceInterfaceContent = serviceInterfaceContent.replace(REPLACE_MODEL, modelClassName);
        serviceInterfaceContent = serviceInterfaceContent.replace(REPLACE_PACKAGE, modelPackageName);
        return serviceInterfaceContent;
    }

    public static String serviceImplClassContent(String modelClassName, String modelPackageName) {
        String serviceImplClassContent = readFileContent("ServiceImplClassContentContent.txt");
        serviceImplClassContent = serviceImplClassContent.replace(REPLACE_MODEL, modelClassName);
        serviceImplClassContent = serviceImplClassContent.replace(REPLACE_PACKAGE, modelPackageName);
        return serviceImplClassContent;
    }

    private static String readFileContent(String fileName) {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(line -> builder.append(line).append("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }

//        File file = new File(fileName);
//
//        try {
//            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//                br.lines().forEach(builder::append);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            try (InputStream inputStream = CreateBaseClasses.class.getResourceAsStream(fileName);
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
////                int data = reader.read();
////                while (data != -1) {
////                    builder.append((char) data);
////                    data = reader.read();
////                }
//                while (reader.ready()) {
//                    builder.append(reader.readLine());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return builder.toString();
    }
}
