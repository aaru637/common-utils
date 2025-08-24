package org.dk;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileOperationsTest {
    private static final String TEST_DIR = "test_dir";
    private static final String TEST_FILE = TEST_DIR + "/test_file.txt";

    @BeforeEach
    void setup() throws IOException {
        // Ensure clean test directory and file for each test
        File dir = new File(TEST_DIR);
        if (dir.exists()) {
            deleteRecursively(dir);
        }
        dir.mkdirs();
        Files.write(new File(TEST_FILE).toPath(), "Line1\nLine2\nLine3".getBytes());
    }

    @AfterEach
    void cleanup() throws IOException {
        File dir = new File(TEST_DIR);
        if (dir.exists()) {
            deleteRecursively(dir);
        }
    }

    private void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                deleteRecursively(f);
            }
        }
        if (!file.delete() && file.exists()) {
            throw new IOException("Failed to delete " + file.getAbsolutePath());
        }
    }

    @Test
    void testIsFileExists_and_IsFileNotExists() {
        assertTrue(FileOperations.isFileExists(TEST_FILE));
        assertFalse(FileOperations.isFileExists(TEST_DIR));
        assertFalse(FileOperations.isFileExists("nonexistent.file"));

        assertFalse(FileOperations.isFileNotExists(TEST_FILE));
        assertTrue(FileOperations.isFileNotExists("nonexistent.file"));

        assertThrows(IllegalArgumentException.class, () -> FileOperations.isFileExists(null));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.isFileExists(""));
    }

    @Test
    void testIsDirectoryExists_and_IsDirectoryNotExists() {
        assertTrue(FileOperations.isDirectoryExists(TEST_DIR));
        assertFalse(FileOperations.isDirectoryExists(TEST_FILE));
        assertFalse(FileOperations.isDirectoryExists("nonexistentDir"));

        assertFalse(FileOperations.isDirectoryNotExists(TEST_DIR));
        assertTrue(FileOperations.isDirectoryNotExists("nonexistentDir"));

        assertThrows(IllegalArgumentException.class, () -> FileOperations.isDirectoryExists(null));
    }

    @Test
    void testCanReadFileAndDirectory() {
        assertTrue(FileOperations.canReadFile(TEST_FILE));
        assertTrue(FileOperations.canReadDirectory(TEST_DIR));

        assertThrows(IllegalArgumentException.class, () -> FileOperations.canReadFile(null));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.canReadDirectory(""));
    }

    @Test
    void testCanWriteFile() {
        assertTrue(FileOperations.canWriteFile(TEST_FILE));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.canWriteFile(null));
    }

    @Test
    void testFormatBytes() {
        assertEquals("0 B", FileOperations.formatBytes(0));
        assertEquals("500 B", FileOperations.formatBytes(500));
        assertEquals("1.0 KB", FileOperations.formatBytes(1024));
        assertEquals("1.50 MB", FileOperations.formatBytes(1572864));
        assertEquals("1.00 GB", FileOperations.formatBytes(1073741824L));
        assertEquals("1.00 TB", FileOperations.formatBytes(1099511627776L));
        assertEquals("1.00 PB", FileOperations.formatBytes(1125899906842624L));

        assertThrows(IllegalArgumentException.class, () -> FileOperations.formatBytes(-1));
    }

    @Test
    void testCreateDirectory() throws IOException {
        String newDir = TEST_DIR + "/newdir";
        File dir = FileOperations.createDirectory(newDir);
        assertTrue(dir.exists() && dir.isDirectory());

        // Already exists directory returns same
        File same = FileOperations.createDirectory(newDir);
        assertEquals(dir.getAbsolutePath(), same.getAbsolutePath());

        // Invalid inputs
        assertThrows(IllegalArgumentException.class, () -> FileOperations.createDirectory(null));
        assertThrows(IOException.class, () -> {
            // Create file with name newDir to cause conflict
            String path = TEST_DIR + "/fileAsDir";
            Files.write(new File(path).toPath(), "content".getBytes());
            FileOperations.createDirectory(path);
        });
    }

    @Test
    void testCreateFile() throws IOException {
        String fileToCreate = TEST_DIR + "/newfile.txt";
        File file = FileOperations.createFile(fileToCreate);
        assertTrue(file.exists() && file.isFile());

        // Already exists file returns same
        File same = FileOperations.createFile(fileToCreate);
        assertEquals(file.getAbsolutePath(), same.getAbsolutePath());

        // Null/Empty checks
        assertThrows(IllegalArgumentException.class, () -> FileOperations.createFile(null));
        // Create directory with same name to cause IOException
        String dirPath = TEST_DIR + "/someDir";
        new File(dirPath).mkdirs();
        assertThrows(IOException.class, () -> FileOperations.createFile(dirPath));
    }

    @Test
    void testReadStringFromFile() throws IOException {
        StringBuilder content = FileOperations.readStringFromFile(TEST_FILE);
        assertTrue(content.toString().contains("Line1"));

        assertThrows(IOException.class, () -> FileOperations.readStringFromFile("no_file"));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.readStringFromFile(null));
    }

    @Test
    void testReadLinesFromFile() throws IOException {
        List<String> lines = FileOperations.readLinesFromFile(TEST_FILE);
        assertEquals(3, lines.size());
        assertEquals("Line1", lines.getFirst());

        assertThrows(IOException.class, () -> FileOperations.readLinesFromFile("no_file"));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.readLinesFromFile(""));
    }

    @Test
    void testWriteStringToFile() throws IOException {
        String fileToWrite = TEST_DIR + "/write_test.txt";
        String content = "Hello Test";
        File f = FileOperations.writeStringToFile(fileToWrite, content);
        assertTrue(f.exists());
        String read = new String(Files.readAllBytes(f.toPath()));
        assertEquals(content, read);

        // Exception cases
        assertThrows(IllegalArgumentException.class, () -> FileOperations.writeStringToFile(null, "content"));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.writeStringToFile(fileToWrite, null));
    }

    @Test
    void testDeleteFile() throws IOException {
        File file = new File(TEST_FILE);
        assertTrue(file.exists());
        FileOperations.deleteFile(TEST_FILE);
        assertFalse(file.exists());

        assertThrows(IOException.class, () -> FileOperations.deleteFile(TEST_FILE));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.deleteFile(null));
    }

    @Test
    void testCopyFileWithProgress() throws IOException {
        String destFile = TEST_DIR + "/copy_test.txt";
        AtomicLong lastProgress = new AtomicLong(-1);
        FileOperations.copyFileWithProgress(TEST_FILE, destFile, (copied, total) -> lastProgress.set(copied));
        File copiedFile = new File(destFile);
        assertTrue(copiedFile.exists());
        assertEquals(copiedFile.length(), new File(TEST_FILE).length());
        assertTrue(lastProgress.get() > 0);
    }

    @Test
    void testCalculateDirectorySize() {
        long size = FileOperations.calculateDirectorySize(new File(TEST_DIR));
        assertTrue(size > 0);
        assertEquals(0L, FileOperations.calculateDirectorySize(new File("nonexistent_dir")));
        assertEquals(new File(TEST_FILE).length(), FileOperations.calculateDirectorySize(new File(TEST_FILE)));
    }

    @Test
    void testMoveFileWithProgress() throws IOException {
        String moveDest = TEST_DIR + "/moved.txt";
        FileOperations.moveFileWithProgress(TEST_FILE, moveDest, (copied, total) -> {
        });
        assertFalse(new File(TEST_FILE).exists());
        assertTrue(new File(moveDest).exists());
    }

    @Test
    void testDeleteDirectoryRecursively() throws IOException {
        File dir = new File(TEST_DIR + "/delDir");
        dir.mkdirs();
        File file = new File(dir, "file.txt");
        file.createNewFile();

        assertTrue(dir.exists());
        FileOperations.deleteDirectoryRecursively(dir);
        assertFalse(dir.exists());

        // Deleting null or non-existent directory should silently do nothing
        FileOperations.deleteDirectoryRecursively(null);
        FileOperations.deleteDirectoryRecursively(new File("non_existent_dir"));
    }

    @Test
    void testCopyFiles() throws IOException {
        String destDir = TEST_DIR + "/copyFiles";
        List<String> filesToCopy = Collections.singletonList(TEST_FILE);
        FileOperations.copyFiles(filesToCopy, destDir, (copied, total) -> {
        });
        assertTrue(new File(destDir).exists());
        assertTrue(new File(destDir, "test_file.txt").exists());

        assertThrows(IllegalArgumentException.class, () -> FileOperations.copyFiles(filesToCopy, "", null));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.copyFiles(Collections.singletonList(""), destDir, null));
    }

    @Test
    void testMoveFiles() throws IOException {
        String destDir = TEST_DIR + "/moveFiles";
        String moveFile = TEST_DIR + "/move_file.txt";
        Files.write(new File(moveFile).toPath(), "content".getBytes());

        List<String> filesToMove = Collections.singletonList(moveFile);
        FileOperations.moveFiles(filesToMove, destDir, (copied, total) -> {
        });
        assertFalse(new File(moveFile).exists());
        assertTrue(new File(destDir, "move_file.txt").exists());

        assertThrows(IllegalArgumentException.class, () -> FileOperations.moveFiles(filesToMove, "", null));
        assertThrows(IllegalArgumentException.class, () -> FileOperations.moveFiles(Collections.singletonList(""), destDir, null));
    }

    @Test
    void testAsyncMethods() throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<File> dirFuture = FileOperations.createDirectoryAsync(TEST_DIR + "/asyncDir");
        assertTrue(dirFuture.get().exists());

        CompletableFuture<File> fileFuture = FileOperations.createFileAsync(TEST_DIR + "/asyncFile.txt");
        assertTrue(fileFuture.get().exists());

        CompletableFuture<StringBuilder> readFuture = FileOperations.readStringFromFileAsync(TEST_FILE);
        assertFalse(readFuture.get().isEmpty());

        CompletableFuture<List<String>> linesFuture = FileOperations.readLinesFromFileAsync(TEST_FILE);
        assertEquals(3, linesFuture.get().size());

        CompletableFuture<File> writeFuture = FileOperations.writeStringToFileAsync(TEST_DIR + "/asyncWritten.txt", "test content");
        assertTrue(writeFuture.get().exists());

        CompletableFuture<Void> deleteFuture = FileOperations.deleteFileAsync(TEST_FILE);
        deleteFuture.get();
        assertFalse(new File(TEST_FILE).exists());

        CompletableFuture<Void> copyFuture;
        // Since deleted above, recreate file:
        Files.write(new File(TEST_FILE).toPath(), "data".getBytes());
        copyFuture = FileOperations.copyFileAsync(TEST_FILE, TEST_DIR + "/copyAsync.txt", null);
        copyFuture.get();
        assertTrue(new File(TEST_DIR + "/copyAsync.txt").exists());

        CompletableFuture<Void> moveFuture = FileOperations.moveFileAsync(TEST_FILE, TEST_DIR + "/moveAsync.txt", null);
        moveFuture.get();
        assertFalse(new File(TEST_FILE).exists());
        assertTrue(new File(TEST_DIR + "/moveAsync.txt").exists());
    }
}
