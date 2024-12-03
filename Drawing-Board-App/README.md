# Drawing Board

# 2D Graphics, MVC, Multiple Views

## Overview
This project is a JavaFX visual editor for building state-machine diagrams. It demonstrates concepts such as 2D graphics, immediate-mode interaction, the Model-View-Controller (MVC) architecture, multiple synchronized views, and view panning. The editor allows users to interactively create state nodes and transition links, edit them, and pan through the workspace.

A visual editor in Java, JavaFX that demonstrates immediate-mode graphics, interaction with graphics, more complex Model-View-Controller (MVC) architectures, and multiple synchronized views. The
system is an interactive editor for building state-machine diagrams. 

The editor allows users to select a tool from a tool palette. Different tools allow the user to create state nodes, connect
states with transition links, move and edit items in the workspace, and pan the view. A property panel at the right of the
screen allows the user to edit details for a state node or a transition link. Pressing the Delete key deletes the selected
item (if there is one) and any dependent items.

### Features
- **Part 1:** Basic system with state node creation and linking
- **Part 2:** Full transition links with event, context, and side effect representation
- **Part 3:** Editing and deletion of state nodes and transition links
- **Part 4:** View synchronization, viewport panning, and window resizing

## How to Run the Application
1. Ensure that you have JavaFX installed.
2. Open the project in IntelliJ IDEA or any other IDE with JavaFX support.
3. Build and run the project. The main class is `EditorApp`.

### Key Interactions:
- **Pointer Tool:** Create and move state nodes.
- **Link Tool:** Create transition links between nodes.
- **Pan Tool:** Drag to move the viewport.
- **Property Panel:** Edit state node or transition properties.
- **Delete Key:** Deletes the selected node or transition.

## Project Structure
- **Application Class:**
  - `EditorApp`: Main application class.
- **Model Classes:**
  - `SMModel`: Manages state nodes and transition links.
  - `SMStateNode`, `SMTransitionLink`: Represent state nodes and transition links.
- **View Classes:**
  - `MainUI`, `ToolPalette`, `DiagramView`: Define different views.
- **Controller Class:**
  - `AppController`: Handles user input and controls interaction.

## Functionality Status
- **Part 1:** Fully implemented (state nodes, links, and MVC structure).
- **Part 2:** Transition links with additional details (event, context, side effects) are fully working.
- **Part 3:** Node and transition editing, along with deletion, is implemented.
- **Part 4:** Panning, resizing, and view synchronization is working as expected.

## Known Issues
- None identified at the moment. If any interaction fails, please ensure the JavaFX version is correctly installed.

## Dependencies
- JavaFX 17+
- No external libraries are required other than JavaFX.

## How to Compile and Run
1. Install JavaFX (version 17 or above).
2. Import the project into your IDE (IntelliJ IDEA recommended).
3. Compile and run `EditorApp.java`.

Data Structures used - HashMap, ArrayList.

Just download the code and setup javaFx path and run.


## Results Similar to Following

![image](https://github.com/user-attachments/assets/33b2b8f1-8ef0-43a4-b1d2-0b51d4dd19d9)
