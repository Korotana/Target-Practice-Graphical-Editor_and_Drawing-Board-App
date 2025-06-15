# DEMO VIDEO
https://youtu.be/IOkpjKBXyF8

# Target Practice Graphical Editor

![image](https://github.com/user-attachments/assets/d4bfc67f-2fa3-488c-8e88-68637e3a2f59)

![image](https://github.com/user-attachments/assets/f5b6cc83-aaf6-46a7-9416-00e732f50628)

## Overview
In this project I have built a graphical editor in JavaFX that lets the user create targets for people to practice 2D targeting (e.g.,
to help them improve their performance in arcade-style video games). The editor allows the user to create, resize, and position
targets for the practice session, and then run a session where they click on the targets one at a time target creation, deletion, 
resizing, selection, and multiple selection; covers undo/redo;  covers clipboards and cut/copy/paste operations. Additionally, 
it includes a target training mode where users can test their clicking accuracy on targets, with performance statistics displayed in 
a final report.

## Features Implemented

### Target Creation, Selection, and Manipulation 
    - Create Targets: Shift-clicking on the background creates a new target.
    - Move Targets: Clicking and dragging moves targets.
    - Resize Targets: Shift-clicking and dragging allows resizing targets.
    - Single and Multiple Selection: Users can select targets individually or using rubber-band-lasso for multiple selections. 
    - Control-click is used to add/remove selections.
    - Delete Targets: Pressing Delete removes selected targets.

### Undo/Redo
    Create, delete, move, and resize actions can be undone (Control-Z) or redone (Control-R).
    Supports undo/redo functionality with multiple targets.

### Cut/Copy/Paste 
    - Cut (Control-X): Removes selected items and stores them in the clipboard.
    - Copy (Control-C): Copies selected items to the clipboard.
    - Paste (Control-V): Pastes items from the clipboard into the model, with support for multiple pastes.

### Targeting Trainer
    - Test Mode: Switch to a test view using Control-T, where users can click through targets.
    - Performance Report: A chart of performance data is shown, plotting Movement Time (MT) vs. Index of Difficulty (ID).
    - Mode Switching: Control-E switches back to the editor view.

### Folders
    Main.java: Entry point for the application.
    Controller.java: Manages interactions and user events.
    InteractionModel.java: Stores application state and implements undo/redo, clipboard, and test mode logic.
    View.java: Handles rendering the targets and user interface.
    TargetCommand.java: Interface for command pattern implementation of undo/redo.
    TargetClipboard.java: Stores targets for cut/copy/paste operations.
    TargetTrainerView.java: Manages the test mode view and handles performance tracking.

Ensure JavaFX is properly configured in your IDE.
Import the project into IntelliJ IDEA or another IDE that supports JavaFX.
Run the application by executing Main.java.


