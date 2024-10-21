# FaraXtractor

FaraXtractor is a simple file management tool that helps you move files from multiple subdirectories into a single destination folder. Originally designed for consolidating Faradars course video files, FaraXtractor can be used for any task where files are scattered across multiple folders and need to be organized into one place for easy access.

## Features

- **Drag and Drop Support**: Easily drop directories into the source or destination fields.
- **Directory Browsing**: Choose both source and destination directories through a simple file chooser.
- **Recursive File Moving**: Moves files from multiple subdirectories into a single destination directory, organizing files into one place.
- **Intuitive UI**: A user-friendly, lightweight Swing-based UI for selecting directories and performing file operations.

## Why FaraXtractor?

When working with directories that contain files spread across subfolders (like video collections, documents, or images), it can be time-consuming to manually move them into a single folder. FaraXtractor automates this process, saving you time and ensuring all your files are in one place.

### Personal Use Case

FaraXtractor was originally created to manage video files from **Faradars courses**. Each course often contains many videos spread across different subdirectories. FaraXtractor helps consolidate all these videos into a single folder, allowing seamless playback.

## How It Works

1. **Select the Source Directory**: This is the root directory containing subdirectories with the files you want to move.
2. **Select the Destination Directory**: The location where you want all the files to be consolidated.
3. **Click Move Files**: FaraXtractor will recursively move all files from subdirectories into the destination directory.

> **Note**: FaraXtractor only moves files and does not preserve the folder structure. All files will be placed in the destination folder.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/JavaBaz/FaraXtractor.git
   ```
2. Navigate into the directory:
   ```bash
    cd FaraXtractor
    ```
3. Build the project with Maven:
    ```bash
   mvn clean package
   ```
4. Run the application:
    ```bash
   java -jar target/FaraXtractor.jar
   ```

####  Requirements

    Java 11 or higher is required to run this application.
    Maven is required to build the project.

### Usage

-  Launch the FaraXtractor app.
-  In the UI:
   Source Directory: Use the "Browse" button or drag-and-drop to select the source directory containing your Faradars course subdirectories.
   Destination Directory: Use the "Browse" button or drag-and-drop to select the directory where you want all the videos to be moved.
-   Click the "Move Files" button to start the file extraction process.


### Contributing

If you find a bug or have a feature request, feel free to open an issue or submit a pull request. Contributions are always welcome!

