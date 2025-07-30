import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileOrganizer {

    public static void main(String[] args) {
        System.out.println("File Organizer is starting...");

        String downloadPath = System.getProperty("user.home") + "\\Downloads";
        File downloadFolder = new File(downloadPath);
        File[] files = downloadFolder.listFiles();

        if (files == null) {
            System.out.println("Download folder not found.");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex == -1) continue;

                String extension = fileName.substring(dotIndex + 1).toLowerCase();
                String category = getCategory(extension);
                Path categoryDir = Paths.get(downloadPath, category);

                try {
                    Files.createDirectories(categoryDir); // Create folder if it doesn't exist
                    Path targetPath = categoryDir.resolve(fileName);
                    Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved: " + fileName + " → " + category + "/");
                } catch (IOException e) {
                    System.out.println("Failed to move: " + fileName);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("File organization complete.");
    }

    public static String getCategory(String ext) {
        switch (ext) {
            case "jpg": case "jpeg": case "png": case "gif": return "Images";
            case "pdf": case "docx": case "txt": return "Documents";
            case "mp4": case "mkv": case "mov": return "Videos";
            case "zip": case "rar": case "7z": return "Zipped";
            case "exe": case "msi": return "Installers";
            default: return "Others";
        }
    }
}
