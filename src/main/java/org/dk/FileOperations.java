/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class providing common helper methods for file operations.
 * <p>
 * Note: This class is not intended to be instantiated.
 * All methods are static and can be called directly on the class.
 * <p>
 * This class is part of the org.dk package, which provides utility methods for various file operations.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see org.dk.FileOperations
 * @since 1.0
 * <p>Created at : 2025-08-23</p>
 */
public class FileOperations {

    private FileOperations() {
        // Private constructor to prevent instantiation
    }

    /**
     * Interface for progress callback during file/directory copy or move.
     */
    public interface ProgressListener {
        /**
         * Called with updated progress information.
         *
         * @param bytesCopied number of bytes copied so far
         * @param totalBytes  total bytes to copy
         */
        void onProgress(long bytesCopied, long totalBytes);
    }

    /**
     * Checks if the file at the given path exists and is a file.
     *
     * @param filePath the file path to check
     * @return true if file exists and is a regular file
     */
    public static boolean isFileExists(String filePath) {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * Checks if the given file path does not exist or is not a file.
     *
     * @param filePath the file path to check
     * @return true if file does not exist or is not a file
     */
    public static boolean isFileNotExists(String filePath) {
        return !isFileExists(filePath);
    }

    /**
     * Checks if the directory at the given path exists and is a directory.
     *
     * @param dirPath the directory path to check
     * @return true if directory exists and is a directory
     */
    public static boolean isDirectoryExists(String dirPath) {
        if (CommonUtils.isEmpty(dirPath)) {
            throw new IllegalArgumentException("Directory path cannot be null or empty");
        }
        File dir = new File(dirPath);
        return dir.exists() && dir.isDirectory();
    }

    /**
     * Checks if the given directory path does not exist or is not a directory.
     *
     * @param dirPath the directory path to check
     * @return true if directory does not exist or is not a directory
     */
    public static boolean isDirectoryNotExists(String dirPath) {
        return !isDirectoryExists(dirPath);
    }

    /**
     * Checks if the file at the given path exists and is readable.
     *
     * @param filePath the file path to check
     * @return true if file exists and is readable
     */
    public static boolean canReadFile(String filePath) {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        return file.exists() && file.canRead();
    }

    /**
     * Checks if the directory at the given path exists, is a directory, and is readable.
     *
     * @param dirPath the directory path to check
     * @return true if directory exists, is directory, and readable
     */
    public static boolean canReadDirectory(String dirPath) {
        if (CommonUtils.isEmpty(dirPath)) {
            throw new IllegalArgumentException("Directory path cannot be null or empty");
        }
        File dir = new File(dirPath);
        return dir.exists() && dir.isDirectory() && dir.canRead();
    }

    /**
     * Checks if the file at the given path exists and is writable.
     *
     * @param filePath the file path to check
     * @return true if file exists and is writable
     */
    public static boolean canWriteFile(String filePath) {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        return file.exists() && file.canWrite();
    }

    /**
     * Formats bytes count into human-readable string up to petabytes.
     *
     * @param bytes number of bytes
     * @return formatted string with unit (e.g., "1.5 MB")
     * @throws IllegalArgumentException if bytes is negative
     */
    public static String formatBytes(long bytes) {
        if (bytes < 0) {
            throw new IllegalArgumentException("Bytes must be non-negative");
        }

        final String[] units = {"B", "KB", "MB", "GB", "TB", "PB"};
        double size = bytes;
        int unitIndex = 0;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        if (unitIndex == 0) {
            return String.format("%d %s", (int) size, units[unitIndex]);
        } else if (unitIndex == 1) {
            return String.format("%.1f %s", Math.max(size, 1.0), units[unitIndex]);
        } else {
            return String.format("%.2f %s", size, units[unitIndex]);
        }
    }

    /**
     * Asynchronously creates a directory at the specified path.
     *
     * @param dirPath directory path
     * @return a {@code CompletableFuture<File>} representing the created directory
     */
    public static CompletableFuture<File> createDirectoryAsync(String dirPath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return createDirectory(dirPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Creates a directory at the specified path.
     *
     * @param dirPath directory path
     * @return the directory File object
     * @throws IOException if creation fails or path is invalid
     */
    public static File createDirectory(String dirPath) throws IOException {
        if (CommonUtils.isEmpty(dirPath)) {
            throw new IllegalArgumentException("Directory path cannot be null or empty");
        }
        File dir = new File(dirPath);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IOException("Path exists but is not a directory: " + dirPath);
            }
            return dir;
        }

        if (!dir.mkdirs()) {
            throw new IOException("Failed to create directory: " + dirPath);
        }
        return dir;
    }

    /**
     * Asynchronously creates a file at the specified path.
     *
     * @param filePath file path
     * @return a {@code CompletableFuture<File>} representing the created file
     */
    public static CompletableFuture<File> createFileAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Creates a file at the specified path, including parent directories.
     *
     * @param filePath file path
     * @return the created file
     * @throws IOException if creation fails or path invalid
     */
    public static File createFile(String filePath) throws IOException {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.isFile()) {
                throw new IOException("Path exists but is not a file: " + filePath);
            }
            return file;
        }
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Failed to create parent directory: " + parentDir.getAbsolutePath());
        }
        if (!file.createNewFile()) {
            throw new IOException("Failed to create file: " + filePath);
        }
        return file;
    }

    /**
     * Asynchronously reads entire file content into a StringBuilder.
     *
     * @param filePath file path to read
     * @return a {@code CompletableFuture<StringBuilder>} with file content
     */
    public static CompletableFuture<StringBuilder> readStringFromFileAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return readStringFromFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Reads the entire content of a file as StringBuilder.
     *
     * @param filePath file path to read
     * @return file content as StringBuilder
     * @throws IOException if file cannot be read
     */
    public static StringBuilder readStringFromFile(String filePath) throws IOException {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (isFileNotExists(filePath)) {
            throw new IOException("File does not exist: " + filePath);
        }
        if (!canReadFile(filePath)) {
            throw new IOException("Cannot read file: " + filePath);
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content;
    }

    /**
     * Asynchronously reads lines of a file as List of strings.
     *
     * @param filePath file path to read
     * @return a {@code CompletableFuture<List<String>>} of file lines
     */
    public static CompletableFuture<List<String>> readLinesFromFileAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return readLinesFromFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Reads lines of a file as List of strings.
     *
     * @param filePath file path to read
     * @return {@code List<String>} lines from file
     * @throws IOException if file cannot be read
     */
    public static List<String> readLinesFromFile(String filePath) throws IOException {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (isFileNotExists(filePath)) {
            throw new IOException("File does not exist: " + filePath);
        }
        if (!canReadFile(filePath)) {
            throw new IOException("Cannot read file: " + filePath);
        }
        List<String> lines = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Asynchronously writes a string content to a file.
     *
     * @param filePath file path to write
     * @param content  content to write
     * @return a {@code CompletableFuture<File>} representing written file
     */
    public static CompletableFuture<File> writeStringToFileAsync(String filePath, String content) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return writeStringToFile(filePath, content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Writes a string content to a file.
     *
     * @param filePath file path to write
     * @param content  content to write
     * @return the file written
     * @throws IOException if unable to write
     */
    public static File writeStringToFile(String filePath, String content) throws IOException {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (CommonUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        File dir = new File(filePath).getParentFile();
        if (dir != null && !dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
        }
        File file = new File(filePath);
        if (file.exists() && !file.canWrite()) {
            throw new IOException("Cannot write to file: " + filePath);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
        return file;
    }

    /**
     * Asynchronously deletes a file.
     *
     * @param filePath file path to delete
     * @return {@code CompletableFuture<Void>}
     */
    public static CompletableFuture<Void> deleteFileAsync(String filePath) {
        return CompletableFuture.runAsync(() -> {
            try {
                deleteFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Deletes a file.
     *
     * @param filePath file path to delete
     * @throws IOException if file does not exist or deletion fails
     */
    public static void deleteFile(String filePath) throws IOException {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + filePath);
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete file: " + filePath);
        }
    }

    /**
     * Asynchronously copies a file with progress updates.
     *
     * @param sourcePath      file to copy
     * @param destinationPath target path
     * @param listener        progress listener
     * @return {@code CompletableFuture<Void>}
     */
    public static CompletableFuture<Void> copyFileAsync(String sourcePath, String destinationPath, ProgressListener listener) {
        return CompletableFuture.runAsync(() -> {
            try {
                copyFileWithProgress(sourcePath, destinationPath, listener);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Copies a file reporting progress.
     *
     * @param sourcePath      file to copy
     * @param destinationPath target path
     * @param listener        progress listener
     * @throws IOException on IO error
     */
    public static void copyFileWithProgress(String sourcePath, String destinationPath, ProgressListener listener) throws IOException {
        File source = new File(sourcePath);
        long totalSize = source.length();
        long currentCopied = 0;

        try (InputStream in = new BufferedInputStream(new FileInputStream(source));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(destinationPath))) {

            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                currentCopied += read;
                if (listener != null) {
                    listener.onProgress(currentCopied, totalSize);
                }
            }
        }
    }

    /**
     * Calculates the total size of a directory recursively.
     *
     * @param directory directory File object
     * @return total size in bytes
     */
    public static long calculateDirectorySize(File directory) {
        if (directory == null || !directory.exists()) return 0L;
        if (directory.isFile()) return directory.length();

        long size = 0L;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                size += calculateDirectorySize(f);
            }
        }
        return size;
    }

    /**
     * Asynchronously moves a file with progress reporting.
     * Internally copies file then deletes the original.
     *
     * @param sourcePath      source file
     * @param destinationPath destination file
     * @param listener        progress listener
     * @return {@code CompletableFuture<Void>}
     */
    public static CompletableFuture<Void> moveFileAsync(String sourcePath, String destinationPath, ProgressListener listener) {
        return CompletableFuture.runAsync(() -> {
            try {
                moveFileWithProgress(sourcePath, destinationPath, listener);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Moves a file by copying with progress and deleting source.
     *
     * @param sourcePath source file
     * @param destPath   destination file
     * @param listener   progress listener
     * @throws IOException on IO error
     */
    public static void moveFileWithProgress(String sourcePath, String destPath, ProgressListener listener) throws IOException {
        copyFileWithProgress(sourcePath, destPath, listener);
        Files.delete(Paths.get(sourcePath));
    }

    /**
     * Recursively deletes the directory or file.
     *
     * @param dir directory or file to delete
     * @throws IOException on failure to delete
     */
    public static void deleteDirectoryRecursively(File dir) throws IOException {
        if (dir == null || !dir.exists()) return;

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteDirectoryRecursively(f);
                }
            }
        }
        if (!dir.delete()) {
            throw new IOException("Failed to delete: " + dir.getAbsolutePath());
        }
    }

    /**
     * Asynchronously copies multiple files with progress reporting.
     *
     * @param sourceFilePaths    list of source file paths
     * @param destinationDirPath destination directory path
     * @param listener           progress listener
     * @return {@code CompletableFuture<Void>}
     */
    public static CompletableFuture<Void> copyFilesAsync(List<String> sourceFilePaths, String destinationDirPath, ProgressListener listener) {
        return CompletableFuture.runAsync(() -> {
            try {
                copyFiles(sourceFilePaths, destinationDirPath, listener);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Copies multiple files with progress reporting.
     *
     * @param sourceFilePaths    list of source file paths
     * @param destinationDirPath destination directory path
     * @param listener           progress listener
     * @throws IOException on IO error
     */
    public static void copyFiles(List<String> sourceFilePaths, String destinationDirPath, ProgressListener listener) throws IOException {
        if (CommonUtils.isEmpty(destinationDirPath)) {
            throw new IllegalArgumentException("Destination directory path cannot be null or empty");
        }
        File destDir = createDirectory(destinationDirPath);

        long totalSize = sourceFilePaths.stream().mapToLong(path -> new File(path).length()).sum();
        AtomicLong copiedBytes = new AtomicLong(0);

        for (String sourceFilePath : sourceFilePaths) {
            if (CommonUtils.isEmpty(sourceFilePath)) {
                throw new IllegalArgumentException("Source file path cannot be null or empty");
            }
            String destPath = new File(destDir, new File(sourceFilePath).getName()).getAbsolutePath();
            copyFileWithProgress(sourceFilePath, destPath, (copied, fileSize) -> {
                long current = copiedBytes.addAndGet(copied);
                if (listener != null) listener.onProgress(current, totalSize);
            });
        }
    }

    /**
     * Asynchronously moves multiple files with progress reporting.
     *
     * @param sourceFilePaths    list of source file paths
     * @param destinationDirPath destination directory path
     * @param listener           progress listener
     * @return {@code CompletableFuture<Void>}
     */
    public static CompletableFuture<Void> moveFilesAsync(List<String> sourceFilePaths, String destinationDirPath, ProgressListener listener) {
        return CompletableFuture.runAsync(() -> {
            try {
                moveFiles(sourceFilePaths, destinationDirPath, listener);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Moves multiple files with progress reporting.
     *
     * @param sourceFilePaths    list of source file paths
     * @param destinationDirPath destination directory path
     * @param listener           progress listener
     * @throws IOException on IO error
     */
    public static void moveFiles(List<String> sourceFilePaths, String destinationDirPath, ProgressListener listener) throws IOException {
        if (CommonUtils.isEmpty(destinationDirPath)) {
            throw new IllegalArgumentException("Destination directory path cannot be null or empty");
        }
        File destDir = createDirectory(destinationDirPath);

        for (String sourceFilePath : sourceFilePaths) {
            if (CommonUtils.isEmpty(sourceFilePath)) {
                throw new IllegalArgumentException("Source file path cannot be null or empty");
            }
            String destPath = new File(destDir, new File(sourceFilePath).getName()).getAbsolutePath();
            moveFileWithProgress(sourceFilePath, destPath, listener);
        }
    }
}
