package com.sanyavertolet.statics

import okio.*

private val clientJsFileContent = """
    |document.addEventListener('DOMContentLoaded', (event) => {
    |    console.log('The DOM is fully loaded and parsed');
    |    const messageElement = document.getElementById('message');
    |    if (messageElement) {
    |        messageElement.textContent = 'Hello, Kotlin!';
    |    }
    |});
""".trimMargin()

private val indexHtmlFileContent = """
    |<!DOCTYPE html>
    |<html lang="en">
    |    <head>
    |        <meta charset="UTF-8">
    |        <title>Simple Static Page</title>
    |    </head>
    |    <body>
    |        <h1>Hello, Kotlin!</h1>
    |    </body>
    |</html>
""".trimMargin()


private val stylesCssFileContent = """
    |body {
    |    font-family: Arial, sans-serif;
    |    background-color: #f0f0f0;
    |    margin: 0;
    |    padding: 20px;
    |}
""".trimMargin()

private val filesForTesting = listOf(
    "index.html" to indexHtmlFileContent,
    "styles.css" to stylesCssFileContent,
    "client.js" to clientJsFileContent,
)

fun createFilesForTesting(filesDirectory: Path) {
    filesForTesting.forEach { (fileName, content) ->
        createFileForTesting(filesDirectory /fileName, content)
    }
}

fun deleteFilesForTesting(filesDirectory: Path) {
    filesForTesting.forEach { (fileName, _) ->
        FileSystem.SYSTEM.delete(filesDirectory / fileName)
    }
}

private fun createFileForTesting(filePath: Path, fileContent: String) {
    val directory = filePath.parent
    if (directory != null && !FileSystem.SYSTEM.exists(directory)) {
        FileSystem.SYSTEM.createDirectories(directory)
    }

    FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
        sink.writeUtf8(fileContent)
    }
}
